import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) throws SQLException {
        // Make connection to the DB
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String user = scanner.nextLine();
        user = user.isEmpty() ? "root" : user;
        System.out.println();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        password = password.isEmpty() ? "root" : password;

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://127.0.0.1:3306/soft_uni", properties);

        PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM employees WHERE salary > ?");

        System.out.print("Enter min salary: ");
        double minSalary = Double.parseDouble(scanner.nextLine());
        preparedStatement.setDouble(1, minSalary);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("employee_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Double salary = resultSet.getDouble("salary");

            System.out.printf("%d -> %s %s %.2f%n", id, firstName, lastName, salary);
        }
    }
}