package school.hei.federationagricoleapi.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class Datasource {
    @Bean
    public Connection getConnection() {
        try {
            String url = System.getenv("DB_URL");
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");

            return DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
