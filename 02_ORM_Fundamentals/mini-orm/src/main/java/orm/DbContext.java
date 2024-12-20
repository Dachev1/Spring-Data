package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbContext<E> {
    void doCreate(Class<E> entityClass) throws SQLException;

    void doAlter(Class<E> entityClass) throws SQLException;

    boolean persist(E entity) throws IllegalAccessException, SQLException;

    boolean persist(E entity, long id) throws IllegalAccessException, SQLException;

    Iterable<E> find(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    Iterable<E> find(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    E findFirst(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    boolean delete(E entity) throws IllegalAccessException, SQLException;
}
