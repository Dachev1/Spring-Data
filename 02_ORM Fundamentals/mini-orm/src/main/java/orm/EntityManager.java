package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityManager<E> implements DbContext<E> {
    private Connection conn;

    public EntityManager(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void doCreate(Class<E> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        String fieldsWithData = getAllFieldsAndDataTypes(entityClass);

        String query = String.format("CREATE TABLE %s (%s);", tableName, fieldsWithData);

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    @Override
    public void doAlter(Class<E> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        List<String> existingColumns = getAllExistingColumns(tableName);
        List<String> fieldsToAdd = new ArrayList<>();

        Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !existingColumns.contains(field.getName()))
                .forEach(field -> {
                    // Get the SQL data type for the field
                    String fieldType = getFieldType(field);
                    // Add the field and its type to the fieldsToAdd list
                    fieldsToAdd.add(field.getName() + " " + fieldType);
                });

        // If there are new fields to add, construct the ALTER TABLE query
        if (!fieldsToAdd.isEmpty()) {
            String query = String.format("ALTER TABLE %s ADD %s;", tableName, String.join(", ", fieldsToAdd));
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        Field idColumn = getIdColumn(entity);
        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);

        if (idValue == null || (long) idValue == 0) {
            return doInsert(entity);
        }

        return doUpdate(entity, (long) idValue);
    }

    @Override
    public boolean persist(E entity, long id) throws IllegalAccessException, SQLException {
        return doUpdate(entity, id);
    }

    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return find(table, null); // Pass null for 'where' condition if not specified
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(table);
        String query = String.format("SELECT * FROM %s %s", tableName, where == null ? "" : " WHERE " + where);

        // Execute query and return list of entities
        return executeQueryAndFillEntities(query, table);
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return findFirst(table, null); // Use findFirst with null where clause
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(table); // Reuse getTableName
        String query = String.format("SELECT * FROM %s %s LIMIT 1", tableName, where == null ? "" : " WHERE " + where);

        // Execute query and return the first entity
        List<E> entities = executeQueryAndFillEntities(query, table);
        return entities.isEmpty() ? null : entities.get(0);
    }

    @Override
    public boolean delete(E entity) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);
        Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Entity has no Id column"));

        idField.setAccessible(true);
        long idValue = (long) idField.get(entity);  // Get the ID directly from the entity

        String query = String.format("DELETE FROM %s WHERE id = %d", tableName, idValue);
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 0) {
            System.out.println("No rows deleted. The entity may not exist.");
            return false;
        } else {
            System.out.printf("Deleted %d rows\n", rowsAffected);
            return true;
        }
    }

    private List<E> executeQueryAndFillEntities(String query, Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<E> entities = new ArrayList<>();

        while (resultSet.next()) {
            E entity = table.getDeclaredConstructor().newInstance();
            fillEntity(table, resultSet, entity);
            entities.add(entity);
        }

        return entities;
    }

    private void fillEntity(Class<E> table, ResultSet resultSet, E entity) throws SQLException {
        Field[] declaredFields = Arrays.stream(table.getDeclaredFields())
                .toArray(Field[]::new);

        Arrays.stream(declaredFields)
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        fillField(field, resultSet, entity);
                    } catch (SQLException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void fillField(Field field, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        field.setAccessible(true);

        if (field.getType() == int.class || field.getType() == long.class) {
            field.set(entity, resultSet.getInt(field.getName()));
        } else if (field.getType() == LocalDate.class) {
            field.set(entity, LocalDate.parse(resultSet.getString(field.getName())));
        } else {
            field.set(entity, resultSet.getString(field.getName()));
        }
    }

    private Field getIdColumn(E entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                return declaredField;
            }
        }

        throw new RuntimeException("Entity has no Id column");
    }

    private boolean doInsert(E entity) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);

        List<String> columnsWithoutId = getColumnsWithoutId(entity);
        List<String> valuesWithoutId = getValuesWithoutId(entity);

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                String.join(",", columnsWithoutId),
                String.join(",", valuesWithoutId));

        PreparedStatement preparedStatement = conn.prepareStatement(query);

        return preparedStatement.executeUpdate() == 1;
    }

    private boolean doUpdate(E entity, Long idValue) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);
        String primaryKey = getIdColumn(entity).getName();

        // Get the columns and values for the entity, excluding the primary key
        List<String> columnsWithoutId = getColumnsWithoutId(entity);
        List<String> valuesWithoutId = getValuesWithoutId(entity);

        // Create a list for column-value pairs
        List<String> columnsAndValues = new ArrayList<>();
        for (int i = 0; i < columnsWithoutId.size(); i++) {
            columnsAndValues.add(String.format("%s=%s", columnsWithoutId.get(i), valuesWithoutId.get(i)));
        }

        // Construct the SQL UPDATE query
        String query = String.format(
                "UPDATE %s SET %s WHERE %s = '%d'",
                tableName,
                String.join(",", columnsAndValues),
                primaryKey,
                idValue
        );

        // Execute the prepared statement
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        return preparedStatement.executeUpdate() == 1;
    }

    private List<String> getColumnsWithoutId(E entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        List<String> list = new ArrayList<>();

        Arrays.stream(declaredFields)
                .filter(field -> !field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(Column.class))
                .forEach(f -> list.add(f.getName()));
        return list;
    }

    private List<String> getValuesWithoutId(E entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        List<String> list = new ArrayList<>();

        Arrays.stream(declaredFields)
                .filter(field -> !field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(Column.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        Object fieldValue = field.get(entity);
                        list.add("'" + fieldValue.toString() + "'");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        return list;
    }

    private String getTableName(E entity) {
        Entity annotation = entity.getClass().getAnnotation(Entity.class);

        if (annotation == null) {
            throw new UnsupportedOperationException("Entity name does not exist");
        }

        return annotation.name();
    }

    private String getTableName(Class<E> table) {
        return table.getAnnotation(Entity.class).name();
    }

    private String getAllFieldsAndDataTypes(Class<E> entityClass) {
        List<String> fieldsWithData = new ArrayList<>();

        Arrays.stream(entityClass.getDeclaredFields())
                .forEach(field -> {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(field.getName());
                            stringBuilder.append(" ");
                            stringBuilder.append(getFieldType(field));
                            if (field.isAnnotationPresent(Id.class)) {
                                stringBuilder.append(" AUTO_INCREMENT PRIMARY KEY");
                            }
                            fieldsWithData.add(stringBuilder.toString());
                        }
                );

        return String.join(", ", fieldsWithData);
    }

    private String getFieldType(Field field) {
        switch (field.getType().getSimpleName()) {
            case "int" -> {
                return "INT";
            }
            case "long" -> {
                return "BIGINT";
            }
            case "String" -> {
                return "VARCHAR(255)";
            }
            case "LocalDate" -> {
                return "DATE";
            }
        }

        return null;
    }

    private List<String> getAllExistingColumns(String tableName) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'mini_orm_db' AND TABLE_NAME = ?;");
        preparedStatement.setString(1, tableName);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> existingColumns = new ArrayList<>();
        while (resultSet.next()) {
            existingColumns.add(resultSet.getString(1));
        }

        return existingColumns;
    }
}
