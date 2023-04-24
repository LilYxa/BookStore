package Menu.components;

import DataBase.*;
import Menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UpdateRecordMenu implements Menu {
    private ConnectionToDB db;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;
    private OrderRepository orderRepository;
    private BookStoreManager DAO;
    private Scanner in = new Scanner(System.in);

    public UpdateRecordMenu(ConnectionToDB db) {
        this.db = db;
        this.bookRepository = new BookRepository(db.getConnection());
        this.authorRepository = new AuthorRepository(db.getConnection());
        this.categoryRepository = new CategoryRepository(db.getConnection());
        this.orderRepository = new OrderRepository(db.getConnection());
        this.DAO = new BookStoreManager(db.getConnection());
    }

    @Override
    public void displayMenu() {
        System.out.println("1) Обновить информацию о книге");
        System.out.println("2) Обновить информацию об авторе");
        System.out.println("3) Обновить информацию о категории");
        System.out.println("4) Обновить информацию о заказе");
        System.out.println("0) Вернуться назад");
    }

    @Override
    public void handleInput(int input) {
        switch (input) {
            case 1: {
                System.out.print("Введите id книги, которую хотите обновить: ");
                int book_id = in.nextInt();
                List<String> fieldsToUpdate = Arrays.asList("title", "author_id", "category_id", "price");
                UpdateTable("books", fieldsToUpdate, book_id);
                break;
            }
            case 2: {
                System.out.print("Введите id автора, информацию о котором хотите обновить: ");
                int author_id = in.nextInt();
                List<String> fieldsToUpdate = Arrays.asList("name", "country", "birth_year");
                UpdateTable("authors", fieldsToUpdate, author_id);
                break;
            }
            case 3: {
                System.out.print("Введите id категории, информацию о которой хотите обновить: ");
                int category_id = in.nextInt();
                List<String> fieldsToUpdate = Arrays.asList("name");
                UpdateTable("categories", fieldsToUpdate, category_id);
                break;
            }
            case 4:{
                System.out.print("Введите id заказа, информацию о котором хотите обновить: ");
                int order_id = in.nextInt();
                List<String> fieldsToUpdate = Arrays.asList("customer_name", "customer_email");
                UpdateTable("orders", fieldsToUpdate, order_id);
                break;
            }
        }
    }

    public void UpdateTable(String tableName, List<String> fieldsToUpdate, int id) {
        // Получаем список полей таблицы, которые можно обновлять
//        List<String> fieldsToUpdate = Arrays.asList("field1", "field2", "field3");

        //Список выбранных полей
        List<String> selectedFields = new ArrayList<>();

        Scanner in = new Scanner(System.in);

        //Запрос номера полей для обновления
        System.out.println("Выберите поля для обновления:");
        for (int i = 0; i < fieldsToUpdate.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, fieldsToUpdate.get(i));
        }

        String input;
        do {
            System.out.println("Введите номер поля (для завершения введите 0):");
            input = in.nextLine();

            try {
                int fieldIndex = Integer.parseInt(input) - 1;

                if (fieldIndex >= 0 && fieldIndex < fieldsToUpdate.size()) {
                    String selectedField = fieldsToUpdate.get(fieldIndex);
                    if (!selectedFields.contains(selectedField))
                        selectedFields.add(selectedField);
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        } while (!input.equals("0"));

        //Запрос новых значений для выбранных полей
        for (String field : selectedFields) {
            System.out.printf("Введите новое значение для поля %s: ", field);
            Object newValue = null;
            if (in.hasNextInt())
                newValue = in.nextInt();
            else if (in.hasNextFloat())
                newValue = in.nextFloat();
            else
                newValue = in.nextLine();
            DAO.updateData(tableName, field, newValue, id);
        }
    }
}
