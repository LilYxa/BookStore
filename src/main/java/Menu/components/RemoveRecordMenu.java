package Menu.components;

import DataBase.*;
import Menu.Menu;

import java.util.Scanner;

public class RemoveRecordMenu implements Menu {

    private ConnectionToDB db;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;

    private OrderRepository orderRepository;

    private Scanner in = new Scanner(System.in);

    public RemoveRecordMenu(ConnectionToDB db) {
        this.db = db;
        this.bookRepository = new BookRepository(db.getConnection());
        this.authorRepository = new AuthorRepository(db.getConnection());
        this.categoryRepository = new CategoryRepository(db.getConnection());
        this.orderRepository = new OrderRepository(db.getConnection());
    }
    @Override
    public void displayMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1) Удалить книгу");
        System.out.println("2) Удалить автора");
        System.out.println("3) Удалить категорию");
        System.out.println("4) Удалить заказ");
        System.out.println("0) Вернуться назад");
    }

    @Override
    public void handleInput(int input) {
        switch (input) {
            case 1:
                System.out.print("Введите идентификатор книги: ");
                int bookID = in.nextInt();
                bookRepository.deleteBook(bookID);
                break;
            case 2:
                System.out.print("Введите идентификатор автора: ");
                int authorID = in.nextInt();
                authorRepository.deleteAuthor(authorID);
                break;
            case 3:
                System.out.print("Введите идентификатор категории: ");
                int categoryID = in.nextInt();
                categoryRepository.deleteCategory(categoryID);
                break;
            case 4:
                System.out.print("Введите идентификатор заказа: ");
                int orderID = in.nextInt();
                orderRepository.deleteOrder(orderID);
                break;
        }
    }
}
