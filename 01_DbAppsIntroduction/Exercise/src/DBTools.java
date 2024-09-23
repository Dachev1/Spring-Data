import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBTools {
    private final String PATH = "jdbc:mysql://127.0.0.1:3306/";
    private Connection connection;

    public DBTools(String username, String password, String dbName) {
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        try {
            connection = DriverManager.getConnection(PATH + dbName, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
