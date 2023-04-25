package DataBase;

import Models.Author;
import Models.Book;
import Models.Category;
import Models.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookStoreManager {
    private Connection connection;

    public BookStoreManager(Connection connection) {
        this.connection = connection;
    }

    public int insertData(String table, String[] fields, Object[] values) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ").append(table).append("(");
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                sqlBuilder.append(", ");
            }
            sqlBuilder.append(fields[i]);
        }
        sqlBuilder.append(") VALUES (");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sqlBuilder.append(", ");
            }
            sqlBuilder.append("?");
        }
        sqlBuilder.append(")");

        String sql = sqlBuilder.toString();

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Ошибка выполнения!");
            }

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public Object getData(String table, String field, Object value, Object entity) {
        String sql = "SELECT * FROM " + table + " WHERE " + field + " = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, value);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                if (entity instanceof Book) {
                    return new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("author_id"),
                        resultSet.getInt("category_id"),
                        resultSet.getFloat("price")
                );
                }
                else if (entity instanceof Author) {
                    return new Author(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("country"),
                    resultSet.getInt("birth_year")
                );
                }
                else if (entity instanceof Category) {
                    return new Category(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    );
                }
                else {
                    return new Order(
                            resultSet.getInt("id"),
                            resultSet.getString("customer_name"),
                            resultSet.getString("customer_email"),
                            resultSet.getInt("book_id"),
                            resultSet.getObject("order_date", LocalDateTime.class)
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
        return null;
    }

    public void deleteData(String table, String field, Object value) {
        String sql = "DELETE FROM " + table + " WHERE " + field + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, value);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("Успешное удаление данных!");
            else
                System.err.println("Ошибка при удалении!");
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }

    public void updateData(String table, List<String> fields, ArrayList<Object> values, int id) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE " + table + " SET ");
        for (int i = 0; i < fields.size(); i++) {
            sqlBuilder.append(fields.get(i)).append(" = ?");
            if (i != fields.size() - 1)
                sqlBuilder.append(", ");
        }
        sqlBuilder.append(" WHERE id = ?");
        String sql = sqlBuilder.toString();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }
            preparedStatement.setInt(values.size() + 1, id);
            preparedStatement.executeUpdate();
            System.out.println("Данные успешно обновлены!");
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }
}
