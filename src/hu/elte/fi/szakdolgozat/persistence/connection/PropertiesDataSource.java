package hu.elte.fi.szakdolgozat.persistence.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PropertiesDataSource {

    private final String url;

    private final String user;

    private final String password;

    public PropertiesDataSource(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("The given properties file should not be null!");
        }
        url = properties.getProperty("db.url");
        user = properties.getProperty("db.user");
        password = properties.getProperty("db.password");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
