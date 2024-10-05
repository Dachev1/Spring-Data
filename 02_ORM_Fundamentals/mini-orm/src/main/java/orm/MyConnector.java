package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnector {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private final Connection connection;

    public MyConnector(String username, String password, String dbName) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        connection = DriverManager.getConnection(CONNECTION_STRING + dbName, props);
    }

    public Connection getConnection() {
        return connection;
    }
}
