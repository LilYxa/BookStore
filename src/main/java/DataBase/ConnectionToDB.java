package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionToDB {
    private static Dotenv dotenv = Dotenv.load();
    private static final String url = dotenv.get("URL");
    private static final String jdbc_driver = "org.postgresql.Driver";
    private static final String user = dotenv.get("USER");
    private static final String password = dotenv.get("PASSWORD");
    private Connection connection = null;

    public ConnectionToDB() {
        try {
            Class.forName(jdbc_driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
            return;
        }
        System.out.println("Подключение к базе данных прошло успешно!");
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии базы данных!");
            e.printStackTrace();
        }
        System.out.println("Подключение успешно закрыто");
    }

}
