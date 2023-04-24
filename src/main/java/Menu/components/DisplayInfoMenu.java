package Menu.components;

import DataBase.*;
import Menu.Menu;
import Models.Author;
import Models.Book;
import Models.Category;
import Models.Order;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class DisplayInfoMenu implements Menu {

    private ConnectionToDB db;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;

    private OrderRepository orderRepository;

    private Scanner in = new Scanner(System.in);

    public DisplayInfoMenu(ConnectionToDB db) {
        this.db = db;
        this.bookRepository = new BookRepository(db.getConnection());
        this.authorRepository = new AuthorRepository(db.getConnection());
        this.categoryRepository = new CategoryRepository(db.getConnection());
        this.orderRepository = new OrderRepository(db.getConnection());
    }
        @Override
        public void displayMenu() {
            System.out.println("Выберите действие:");
            System.out.println("1) Вывести книгу");
            System.out.println("2) Вывести все книги");
            System.out.println("3) Вывести автора");
            System.out.println("4) Вывести всех авторов");
            System.out.println("5) Вывести категорию");
            System.out.println("6) Вывести все категории");
            System.out.println("7) Вывести заказ");
            System.out.println("8) Отчёт по заказам");
            System.out.println("0) Вернуться назад");
        }

        @Override
        public void handleInput(int input) {
            switch (input) {
                case 1:
                {
                    System.out.print("Введите название книги: ");
                    String title = in.nextLine();
                    Book book = bookRepository.getBook(title);
                    if (book == null)
                        System.out.println("Такой книги не существует!");
                    else {

                        String[][] data = {{"ID", "Название", "ID автора", "ID категории", "Цена"},
                                {String.valueOf(book.getId()), book.getTitle(), String.valueOf(book.getAuthor_id()),
                                        String.valueOf(book.getCategory_id()), String.valueOf(book.getPrice())}};

                        printTable(data);
                    }
                    break;
                }
                case 2:
                {
                    List<Book> books = bookRepository.getAllBooks();
                    //массив data размером на один больше, чем количество книг в списке, чтобы уместить в него шапку
                    String[][] data = new String[books.size() + 1][5];
                    data[0] = new String[]{"ID", "Название", "Имя автора", "Название категории", "Цена"};

                    for (int i = 0; i < books.size(); i++) {
                        Book book = books.get(i);
                        data[i + 1] = new String[]{String.valueOf(book.getId()), book.getTitle(), book.getAuthor_name(),
                                book.getCategory_name(), String.valueOf(book.getPrice())};
                    }

                    printTable(data);
                    break;
                }
                case 3:
                {
                    System.out.print("Введите имя автора: ");
                    String authorName = in.nextLine();
//                    Author author = authorRepository.getAuthorByName(authorName);
                    Author author = authorRepository.getAuthor(authorName);
                    if (author == null)
                        System.err.println("Такого автора нет в базе данных!");
                    else {
                        String[][] data = {
                                {"ID", "ФИО автора", "Страна автора", "Год рождения"},
                                {String.valueOf(author.getId()), author.getName(), author.getCountry(), String.valueOf(author.getBirthYear())}
                        };
                        printTable(data);
                    }
                    break;
                }
                case 4:
                {
                    List<Author> authors = authorRepository.getAllAuthors();
                    String[][] data = new String[authors.size() + 1][4];
                    data[0] = new String[]{"ID", "ФИО автора", "Страна автора", "Год рождения"};

                    for (int i = 0; i < authors.size(); i++) {
                        Author author = authors.get(i);
                        data[i + 1] = new String[]{String.valueOf(author.getId()), author.getName(), author.getCountry(), String.valueOf(author.getBirthYear())};
                    }
                    printTable(data);
                    break;
                }
                case 5:
                {
                    System.out.print("Введите название категории: ");
                    String categoryName = in.nextLine();
                    Category category = categoryRepository.getCategory(categoryName);
                    if (category == null)
                        System.err.println("Такой категории в базе нет!");
                    else {
                        String[][] data = {
                                {"ID", "Название категории"},
                                {String.valueOf(category.getId()), category.getName()}
                        };
                        printTable(data);
                    }
                    break;
                }
                case 6:
                {
                    List<Category> categories = categoryRepository.getAllCategories();
                    String[][] data = new String[categories.size() + 1][2];
                    data[0] = new String[]{"ID", "Название категории"};

                    for (int i = 0; i < categories.size(); i++) {
                        Category category = categories.get(i);
                        data[i + 1] = new String[]{String.valueOf(category.getId()), category.getName()};
                    }
                    printTable(data);
                    break;
                }
                case 7:
                {
                    System.out.print("Введите номер заказа: ");
                    int id = in.nextInt();
                    Order order = orderRepository.getOrder(id);
//                    Book book = bookRepository.getBook(id);

                    if (order == null)
                        System.err.println("Заказа с таким номером не существует!");
                    else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                        String formattedDate = order.getOrderDate().format(formatter);
                        String[][] data = {
                                {"ID", "Имя покупателя", "E-mail покупателя", "ID книги", "Дата заказа"},
                                {String.valueOf(order.getId()), order.getCustomerName(), order.getCustomerEmail(),
                                        String.valueOf(order.getBookId()), formattedDate}
                        };
                        printTable(data);
                    }
                    break;
                }
                case 8:
                {
                    orderRepository.printReport();
                }
            }
        }

    private static void printTable(String[][] data) {
        // Найти максимальную длину в каждом столбце
        int[] maxColumnLengths = new int[data[0].length];
        for (int i = 0; i < data[0].length; i++) {
            int max = 0;
            for (int j = 0; j < data.length; j++) {
                if (data[j][i].length() > max) {
                    max = data[j][i].length();
                }
            }
            maxColumnLengths[i] = max;
        }

        // Вычислить ширину всей таблицы
        int tableWidth = 1; // ширина левой границы
        for (int length : maxColumnLengths) {
            tableWidth += length + 2; // ширина ячейки + 2 символа (правая и левая границы)
        }
        tableWidth += data[0].length - 1; // добавить ширину разделителей между ячейками

        // Рисуем верхнюю границу таблицы
        for (int i = 0; i < tableWidth; i++) {
            System.out.print("-");
        }
        System.out.println();

        // Выводим данные в таблицу
        for (int i = 0; i < data.length; i++) {
            System.out.print("|"); // левая граница строки
            for (int j = 0; j < data[i].length; j++) {
                String cellValue = data[i][j];
                int padding = maxColumnLengths[j] - cellValue.length();
                int paddingLeft = padding / 2;
                int paddingRight = padding - paddingLeft;
                System.out.print(" ");
                for (int k = 0; k < paddingLeft; k++) {
                    System.out.print(" ");
                }
                System.out.print(cellValue);
                for (int k = 0; k < paddingRight; k++) {
                    System.out.print(" ");
                }
                System.out.print(" |"); // правая граница ячейки
            }
            System.out.println(); // переход на новую строку

            // Рисуем разделительную границу между строками
            for (int j = 0; j < tableWidth; j++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }

}
