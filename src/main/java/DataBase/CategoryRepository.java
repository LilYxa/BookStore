package DataBase;

import Models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private Connection connection;

    private BookStoreManager DAO;

    public CategoryRepository(Connection connection) {
        this.connection = connection;
        this.DAO = new BookStoreManager(connection);
    }

//    public void addCategory(Category category) {
//        String sql = "INSERT INTO categories(name) VALUES(?)";
//        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            statement.setString(1, category.getName());
//
//            int rowsInserted = statement.executeUpdate();
//            if (rowsInserted == 0)
//                throw new SQLException("Ошибка выполнения!");
//
//            ResultSet resultSet = statement.getGeneratedKeys();
//            if (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                category.setId(id);
//            } else
//                throw new SQLException("Ошибка при добавлении категории!");
//        } catch (SQLException e) {
//            System.err.println("Ошибка при выполнении запроса!");
//            e.printStackTrace();
//        }
//    }

    public void addCategory(Category category) {
        String[] fields = {"name"};
        Object[] values = {category.getName()};
        int id = DAO.insertData("categories", fields, values);
        category.setId(id);
    }

//    public Category getCategory(int id) {
//        String sql = "SELECT * FROM categories WHERE id = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return new Category(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name")
//                );
//            }
//        } catch (SQLException e) {
//            System.err.println("Ошибка при выполнении запроса!");
//            e.printStackTrace();
//        }
//        return null;
//    }

    public Category getCategory(Object identifier) {
        Category category = new Category();
        String field = (identifier instanceof String) ? "name" : "id";
        return (Category) DAO.getData("categories", field, identifier, category);
    }

//    public Category getCategoryByName(String name) {
//        String sql = "SELECT * FROM categories WHERE name = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, name);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return new Category(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name")
//                );
//            }
//        } catch (SQLException e) {
//            System.err.println("Ошибка при выполнении запроса!");
//            e.printStackTrace();
//        }
//        return null;
//    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                categories.add(new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
        return categories;
    }

    public void updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("Категория успешно обновлена!");
            else
                System.out.println("Ошибка при обновлении категории!");
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
        }
    }

//    public void deleteCategory(int id) {
//        String sql = "DELETE FROM categories WHERE id = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, id);
//            int rowsDeleted = statement.executeUpdate();
//            if (rowsDeleted > 0)
//                System.out.println("Категория успешно удалена!");
//            else
//                System.out.println("Ошибка при удалении категории!");
//        } catch (SQLException e) {
//            System.err.println("Ошибка при выполнении запроса!");
//            e.printStackTrace();
//        }
//    }

    public void deleteCategory(Object identifier) {
        String field = (identifier instanceof String) ? "name" : "id";
        DAO.deleteData("categories", field, identifier);
    }
}
