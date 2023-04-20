package DataBase;

import Models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private Connection connection;
    private Manager DAO;

    public BookRepository(Connection connection) {
        this.connection = connection;
        DAO = new Manager(connection);
    }

    //Создание книги
//    public void createBook(Book book) {
//        String sql = "INSERT INTO books (title, author_id, category_id, price) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, book.getTitle());
//            statement.setInt(2, book.getAuthor_id());
//            statement.setInt(3, book.getCategory_id());
//            statement.setFloat(4, book.getPrice());
//            statement.executeUpdate();
//            System.out.println("Книга успешно создана!");
//        } catch (SQLException e) {
//            System.err.println("Ошибка при выполнении запроса!");
//            e.printStackTrace();
//        }
//    }

    public void createBook(Book book) {
        String[] fields = {"title", "author_id", "category_id", "price"};
        Object[] values = {book.getTitle(), book.getAuthor_id(), book.getCategory_id(), book.getPrice()};
        DAO.insertData("books", fields, values);
    }

    //Получение книги по id/названию
//    public Book getBook(Object identifier) {
//        String sql = "SELECT * from books WHERE ";
//        String column = "";
//        if (identifier instanceof Integer) {
//            column = "id";
//            sql += column + " = ?";
//        } else if (identifier instanceof String) {
//            column = "title";
//            sql += column + " = ?";
//        } else
//            throw new IllegalArgumentException("Неверный тип параметра " + identifier.getClass());
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            if (identifier instanceof Integer) {
//                statement.setInt(1, (int) identifier);
//            } else {
//                statement.setString(1, (String) identifier);
//            }
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return new Book(
//                        resultSet.getInt("id"),
//                        resultSet.getString("title"),
//                        resultSet.getInt("author_id"),
//                        resultSet.getInt("category_id"),
//                        resultSet.getFloat("price")
//                );
//            }
//        } catch (SQLException e) {
//            System.err.println("Ошибка при выполнении запроса!");
//            e.printStackTrace();
//        }
//        return null;
//    }

    public Book getBook(Object identifier) {
        Book book = new Book();
        String field = (identifier instanceof String) ? "title" : "id";
        return (Book) DAO.getData("books", field, identifier, book);
    }

    //Получение всех книг
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("author_id"),
                        resultSet.getInt("category_id"),
                        resultSet.getFloat("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
        return books;
    }

    //Обновление информации о книге
    public void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author_id = ?, category_id = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthor_id());
            statement.setInt(3, book.getCategory_id());
            statement.setFloat(4, book.getPrice());
            statement.setInt(5, book.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("Книга успешно обновлена!");
            else
                System.out.println("Ошибка при обновлении книги!");
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
    }

    //Удаление книги
    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("Книга успешно удалена!");
            else
                System.out.println("Ошибка при удалении книги!");
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
    }

}
