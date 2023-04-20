package DataBase;

import Models.Order;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class OrderRepository {
    private Connection connection;

    private Manager DAO;

    public OrderRepository(Connection connection) {
        this.connection = connection;
        DAO = new Manager(connection);
    }

    public void addOrder(Order order) {
        String[] fields = {"customer_name", "customer_email", "book_id", "order_date"};
        Object[] values = {order.getCustomerName(), order.getCustomerEmail(), order.getBookId(), order.getOrderDate()};
        int id = DAO.insertData("orders", fields, values);
        order.setId(id);
    }

    public Order getOrder(int id) {
        Order order = new Order();
        return (Order) DAO.getData("orders", "id", id, order);
    }

    public void printReport() {
        String sql = "SELECT b.title, a.name, COUNT(o.id) AS order_count " +
                    "FROM orders o JOIN books b ON b.id = o.book_id " +
                    "JOIN authors a on a.id = b.author_id " +
                    "GROUP BY b.id, b.title, a.name " +
                    "ORDER BY order_count DESC";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String bookTitle = resultSet.getString("title");
                int orderCount = resultSet.getInt("order_count");
                String author_name = resultSet.getString("name");
                System.out.printf("Автор: %s\nКнига: %s\nКоличество заказов: %d\n\n", author_name, bookTitle, orderCount);

                String sql_inner = "SELECT o.id, o.customer_name, o.customer_email, o.order_date from orders o " +
                                    "JOIN books b on b.id = o.book_id " +
                                    "WHERE b.title = ? " +
                                    "ORDER BY order_date ASC";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql_inner)) {
                    preparedStatement.setString(1, bookTitle);
                    ResultSet innerResSet = preparedStatement.executeQuery();
                    while (innerResSet.next()) {
                        int orderId = innerResSet.getInt("id");
                        String customerName = innerResSet.getString("customer_name");
                        String customerEmail = innerResSet.getString("customer_email");
                        LocalDateTime orderDate = innerResSet.getObject("order_date", LocalDateTime.class);
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                        String formattedDate = orderDate.format(dateTimeFormatter);

                        System.out.printf("\tЗаказ №%d от %s:\n\tКлиент: %s (%s)\n\n",
                                            orderId, formattedDate, customerName, customerEmail);
                    }
                }
                System.out.println("---------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }


}
