import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");

        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://127.0.0.1:3306/diablo", properties);


        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.println();

        PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT " +
                        "CONCAT(u.first_name, ' ', u.last_name) AS full_name," +
                        "COUNT(*) AS count " +
                        "FROM users AS u " +
                        "JOIN users_games AS ug ON u.id = ug.user_id " +
                        "WHERE user_name = ? " +
                        "HAVING count > 0;");

        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            System.out.printf("User: %s%n", username);
            System.out.printf("%s has played %d games",
                    resultSet.getString("full_name"),
                    resultSet.getInt("count"));
        } else {
            System.out.println("No such user exists");
        }
    }
}
