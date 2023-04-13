package Menu.components;

import DataBase.BookStoreManager;
import DataBase.ConnectionToDB;
import Menu.Menu;

public class AddRecordMenu implements Menu {
    private ConnectionToDB db;
    private final BookStoreManager bs_manager;

    public AddRecordMenu(ConnectionToDB db) {
        this.db = db;
        bs_manager = new BookStoreManager(db.getConnection());
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
        }
    }
}
