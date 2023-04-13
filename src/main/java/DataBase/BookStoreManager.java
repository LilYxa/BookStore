package DataBase;

import java.sql.*;
import java.util.Scanner;

public class BookStoreManager {
    private Connection connection;

    public BookStoreManager(Connection connection) {
        this.connection = connection;
    }

    //Добавление книг в базу данных
    public void addBook() {
        Scanner in = new Scanner(System.in);

        System.out.print("Введите название книги: ");
        String title = in.nextLine();
        System.out.print("Введите ФИО автора: ");
        String author_name = in.nextLine();
        System.out.print("Введите категорию: ");
        String category_name = in.nextLine();
        System.out.print("Введите цену: ");
        float price = in.nextFloat();
        in.nextLine();

        //Проверка на существование автора
        int author_id = getID(connection, "authors", author_name);
        if (author_id == -1) {
            System.out.println("Такого автора нет. Его необходимо добавить.");
            System.out.print("Введите страну автора: ");
            String country = in.nextLine();
            System.out.print("Введите год его рождения: ");
            int birthYear = in.nextInt();

            author_id = addAuthor(connection, author_name, country, birthYear);
        }

        //Проверка на существование категории
        int category_id = getID(connection, "categories", category_name);
        if (category_id == -1)
            category_id = addCategory(category_name);

        String sql = "INSERT INTO books (title, author_id, category_id, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setInt(2, author_id);
            statement.setInt(3, category_id);
            statement.setFloat(4, price);
            statement.executeUpdate();
            System.out.println("Книга успешно добавлена!");
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
    }

    //Метод, который возвращает id из таблиц
    public int getID(Connection connection, String table_name, String name) {
        String sql = "SELECT id FROM " + table_name + " WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("id");
            else
                return -1;
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
            return -1;
        }
    }

    public int addAuthor(Connection connection, String author_name, String country, int birth_year) {
        String sql = "INSERT INTO authors (name, country, birth_year) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author_name);
            statement.setString(2, country);
            statement.setInt(3, birth_year);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKey = statement.getGeneratedKeys();
                if (generatedKey.next())
                    return generatedKey.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
            return -1;
        }
    }

    public int addCategory(String title) {
        String sql = "INSERT INTO categories (name) values (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, title);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKey = statement.getGeneratedKeys();
                if (generatedKey.next())
                    return generatedKey.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса!");
            e.printStackTrace();
            return -1;
        }
    }

}
