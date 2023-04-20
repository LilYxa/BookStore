package DataBase;

import Models.Author;
import Models.Book;
import Models.Category;

import java.sql.*;
import java.util.Scanner;

public class BookStoreManager {
    private Connection connection;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;

    public BookStoreManager(Connection connection) {
        this.connection = connection;
        this.bookRepository = new BookRepository(connection);
        this.authorRepository = new AuthorRepository(connection);
        this.categoryRepository = new CategoryRepository(connection);
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
//        Author author = authorRepository.getAuthorByName(author_name);
        Author author = authorRepository.getAuthor(author_name);
        if (author == null) {
            System.out.println("Такого автора нет. Его необходимо добавить.");
            System.out.print("Введите страну автора: ");
            String country = in.nextLine();
            System.out.print("Введите год его рождения: ");
            int birthYear = in.nextInt();

            author = new Author(author_name, country, birthYear);
            authorRepository.addAuthor(author);
            System.out.println("Автор был успешно добавлен!");
        }

        //Проверка на существование категории
//        Category category = categoryRepository.getCategoryByName(category_name);
        Category category = categoryRepository.getCategory(category_name);
        if (category == null) {
            category =  new Category(category_name);
            categoryRepository.addCategory(category);
        }

        Book book = new Book(title, author.getId(), category.getId(), price);
        bookRepository.createBook(book);
        System.out.println("Книга успешно добавлена!");
    }

}
