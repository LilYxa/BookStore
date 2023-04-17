package DataBase;

import Models.Author;

import java.awt.geom.RectangularShape;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {
    private Connection connection;

    public AuthorRepository(Connection connection) {
        this.connection = connection;
    }

    public void addAuthor(Author author) {
        String sql = "INSERT INTO authors(name, country, birth_year) VALUES(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getCountry());
            statement.setInt(3, author.getBirthYear());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0)
                throw new SQLException("Ошибка выполнения!");

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                author.setId(id);
            } else
                throw new SQLException("Ошибка при добавлении автора!");
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
    }

    public Author getAuthor(int id) {
        String sql = "SELECT * FROM authors WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Author(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("country"),
                    resultSet.getInt("birth_year")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
        return null;
    }

    public Author getAuthorByName(String name) {
        String sql = "SELECT * FROM authors WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Author(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("country"),
                        resultSet.getInt("birth_year")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
        return null;
    }

    public List<Author> getAllAuthors() {
        String sql = "SELECT * FROM authors";
        List<Author> authors = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                authors.add(new Author(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("country"),
                        resultSet.getInt("birth_year")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
        return authors;
    }

    public void updateAuthor(Author author) {
        String sql = "UPDATE authors SET name = ?, country = ?, birth_year = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getCountry());
            statement.setInt(3, author.getBirthYear());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("Автор успешно обновлен!");
            else
                System.out.println("Ошибка при обновлении автора!");
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
    }

    public void deleteAuthor(int id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("Автор успешно удален!");
            else
                System.out.println("Ошибка при удалении автора!");
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
    }
}
