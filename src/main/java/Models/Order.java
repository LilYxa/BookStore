package Models;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String customerName;
    private String customerEmail;
    private LocalDateTime orderDate;
    private Book book;
    private int bookId;

    public Order(String customerName, String customerEmail, int bookId, LocalDateTime orderDate) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.bookId = bookId;
        this.orderDate = LocalDateTime.now();
    }

    public Order(int id, String customerName, String customerEmail, int bookId, LocalDateTime orderDate) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.bookId = bookId;
        this.orderDate = LocalDateTime.now();
    }

    public Order(String customerName, String customerEmail, int bookId) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.bookId = bookId;
        this.orderDate = LocalDateTime.now();
    }

    public Order() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Book getBook() {
        return book;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String toString() {
        return id + "\t" + customerName + "\t" + customerEmail + "\t" + book.getId() + "\t" + orderDate + "\n";
    }
}
