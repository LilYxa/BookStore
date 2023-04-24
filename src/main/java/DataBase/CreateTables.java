package DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {
    private Connection connection;

    public CreateTables(Connection connection) {
        this.connection = connection;
    }

    public void CreateAuthorsTable() {
        String sql_createAuthors = "CREATE TABLE IF NOT EXISTS authors " +
                "(id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "country VARCHAR(255), " +
                "birth_year INTEGER NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql_createAuthors);
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }

    public void CreateCategoriesTable() {
        String sql_createCategories = "CREATE TABLE IF NOT EXISTS categories " +
                "(id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql_createCategories);
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }

    public void CreateBooksTable() {
        String sql_createBooks = "CREATE TABLE IF NOT EXISTS books " +
                                "(id SERIAL PRIMARY KEY, " +
                                "title VARCHAR(255), " +
                                "author_id INTEGER NOT NULL, " +
                                "category_id INTEGER NOT NULL, " +
                                "price NUMERIC(10, 2) CHECK ( price > 0 ), " +
                                "FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE, " +
                                "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE)";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql_createBooks);
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }
    public void CreateOrdersTable() {
        String sql_createOrders = "CREATE TABLE IF NOT EXISTS orders " +
                "(id SERIAL PRIMARY KEY, " +
                "customer_name VARCHAR(255) NOT NULL, " +
                "customer_email VARCHAR(255) NOT NULL, " +
                "book_id INTEGER REFERENCES books(id) ON DELETE CASCADE, " +
                "order_date TIMESTAMP DEFAULT NOW())";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql_createOrders);
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }
}
