import java.sql.*;
import java.util.*;

public class Main {

    private static final DBTools DB_TOOLS = new DBTools("root", "root", "minions_db");
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        exercise_08();
    }

    private static void exercise_08() throws SQLException {
        int minionId = Integer.parseInt(SCANNER.nextLine());

        CallableStatement callableStatement = DB_TOOLS.getConnection().prepareCall("CALL usp_get_older(?)");
        callableStatement.setInt(1, minionId);
        callableStatement.execute();
    }

    private static void exercise_07() throws SQLException {
        int[] minionIds = Arrays.stream(SCANNER.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int minionId : minionIds) {
            PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("UPDATE minions SET age = age + 1, name = LOWER(name) WHERE id = ?");
            preparedStatement.setInt(1, minionId);
            int i = preparedStatement.executeUpdate();

            preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT name, age FROM minions");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
            }
        }
    }

    private static void exercise_06() throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT name FROM minions");
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();

        Deque<String> deque = new ArrayDeque<>();

        while (resultSet.next()) {
            deque.add(resultSet.getString("name"));
        }

        while (!deque.isEmpty()) {
            System.out.println(deque.removeFirst());
            System.out.println(deque.removeLast());
        }
    }

    private static void exercise_05() throws SQLException {
        int villainId = Integer.parseInt(SCANNER.nextLine());

        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("No such villain was found");
            return;
        }

        String villainName = resultSet.getString("name");

        preparedStatement = DB_TOOLS.getConnection().prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
        preparedStatement.setInt(1, villainId);
        int deletedCount = preparedStatement.executeUpdate();

        preparedStatement = DB_TOOLS.getConnection().prepareStatement("DELETE FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);
        preparedStatement.executeUpdate();

        System.out.printf("%s was deleted%n", villainName);
        System.out.printf("%d minions released%n", deletedCount);
    }

    private static void exercise_04() throws SQLException {
        String countryName = SCANNER.nextLine();

        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ?");
        preparedStatement.setString(1, countryName);
        int updateCount = preparedStatement.executeUpdate();

        if (updateCount == 0) {
            System.out.println("No town names were affected.");
            return;
        }

        List<String> townsList = new ArrayList<>();
        preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT name FROM towns WHERE country = ?");
        preparedStatement.setString(1, countryName);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            townsList.add(resultSet.getString("name"));
        }

        System.out.printf("%d town names were affected.%n[%s]", updateCount, String.join(", ", townsList));
    }

    private static void exercise_03() throws SQLException {
        System.out.println("Minion: ");
        String[] minionTokens = SCANNER.nextLine().split("\\s+");
        String minionName = minionTokens[0];
        int minionAge = Integer.parseInt(minionTokens[1]);
        String minionTown = minionTokens[2];

        System.out.println("Villain: ");
        String villainName = SCANNER.nextLine().split("\\s+")[0];

        int townId = getTownId(minionTown);
        if (townId == 0) {
            townId = addTown(minionTown);
            System.out.printf("Town %s was added to the database.%n", minionTown);
        }

        int villainId = getVillainId(villainName);
        if (villainId == 0) {
            villainId = addVillain(villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        int minionId = addMinion(minionName, minionAge, townId);

        addMinionToHisLeader(minionId, villainId);
        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);
    }

    public static void exercise_02() throws SQLException {
        System.out.print("Enter villain id: ");
        int villainId = Integer.parseInt(SCANNER.next());

        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement
                ("SELECT " +
                        "v.name AS villain_name," +
                        "m.name AS minion_name, " +
                        "m.age AS minion_age " +
                        "FROM villains AS v " +
                        "JOIN minions_villains AS mv ON v.id = mv.villain_id " +
                        "JOIN minions AS m ON mv.minion_id = m.id " +
                        "WHERE v.id = ?;"
                );

        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            System.out.printf("Villain: %s%n", resultSet.getString("villain_name"));
        } else {
            System.out.printf("No villain with ID %d exists in the database.%n", villainId);
            return;
        }

        int row = 1;
        do {
            System.out.printf("%d. %s %d%n",
                    row++, resultSet.getString("minion_name"), resultSet.getInt("minion_age"));
        } while (resultSet.next());
    }

    public static void exercise_01() throws SQLException {
        // Make the query
        ResultSet resultSet = DB_TOOLS.getConnection().createStatement().executeQuery
                ("SELECT " +
                        "v.name," +
                        "COUNT(*) AS count " +
                        "FROM villains AS v " +
                        "JOIN minions_villains AS mv ON v.id = mv.villain_id " +
                        "GROUP BY v.name " +
                        "HAVING count > 15 " +
                        "ORDER BY count DESC;");

        // Output
        while (resultSet.next()) {
            System.out.printf("%s %d%n",
                    resultSet.getString("name"),
                    resultSet.getInt("count"));
        }
    }

    private static void addMinionToHisLeader(int minionId, int villainId) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("INSERT INTO minions_villains VALUES (?, ?)");
        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);
        preparedStatement.executeUpdate();
    }

    private static int addMinion(String minionName, int minionAge, int townId) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("INSERT INTO minions (name, age, town_id) VALUES (?, ?, ?)");
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setInt(3, townId);
        preparedStatement.executeUpdate();

        preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT id FROM minions WHERE name = ?");
        preparedStatement.setString(1, minionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    private static int addVillain(String villainName) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("INSERT INTO villains(name, evilness_factor) VALUE (?, 'evil')");
        preparedStatement.setString(1, villainName);
        preparedStatement.executeUpdate();

        preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT id FROM villains WHERE name = ?");
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    private static int getVillainId(String villainName) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT * FROM villains WHERE name = ?");
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }

        return 0;
    }

    private static int addTown(String minionTown) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("INSERT INTO towns(name) VALUE (?)");
        preparedStatement.setString(1, minionTown);
        preparedStatement.executeUpdate();

        preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT id FROM towns WHERE name = ?");
        preparedStatement.setString(1, minionTown);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    private static int getTownId(String minionTown) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT id FROM towns WHERE name = ?");
        preparedStatement.setString(1, minionTown);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }


}
