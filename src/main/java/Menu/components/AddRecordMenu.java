package Menu.components;

import DataBase.*;
import Menu.Menu;
import Models.Author;
import Models.Book;
import Models.Category;
import Models.Order;

import java.util.Scanner;

public class AddRecordMenu implements Menu {
    private ConnectionToDB db;
    private final BookStoreManager bs_manager;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;
    private OrderRepository orderRepository;

    private Scanner in = new Scanner(System.in);

    public AddRecordMenu(ConnectionToDB db) {
        this.db = db;
        bs_manager = new BookStoreManager(db.getConnection());
        this.bookRepository = new BookRepository(db.getConnection());
        this.authorRepository = new AuthorRepository(db.getConnection());
        this.categoryRepository = new CategoryRepository(db.getConnection());
        this.orderRepository = new OrderRepository(db.getConnection());
    }
    @Override
    public void displayMenu() {
        System.out.println("1) Добавить книгу");
        System.out.println("2) Добавить автора");
        System.out.println("3) Добавить категорию");
        System.out.println("4) Добавить заказ");
        System.out.println("0) Вернуться назад");
    }

    @Override
    public void handleInput(int input) {
        switch (input) {
            case 1:
                bs_manager.addBook();
                break;
            case 2:
                System.out.print("Введите ФИО автора: ");
                String author_name = in.nextLine();
                System.out.print("Введите страну автора: ");
                String country = in.nextLine();
                System.out.print("Введите год рождения: ");
                int birth_year = in.nextInt();

                Author author = new Author(author_name, country, birth_year);
                authorRepository.addAuthor(author);
                break;
            case 3:
                System.out.print("Введите название категории: ");
                String category_title = in.nextLine();
                Category category = new Category(category_title);
                categoryRepository.addCategory(category);
                break;
            case 4:
                System.out.print("Введите имя покупателя: ");
                String customer_name = in.nextLine();
                System.out.print("Введите e-mail покупателя: ");
                String customer_email = in.nextLine();
                System.out.print("Введите название книги: ");
                String book_title = in.nextLine();

                Book book = bookRepository.getBook(book_title);
                if (book == null) {
                    System.err.println("Такой книги нет в наличии.");
                    break;
                }
                else {
                    Order order = new Order(customer_name, customer_email, book.getId());
                    orderRepository.addOrder(order);
                    break;
                }
        }
    }
}
