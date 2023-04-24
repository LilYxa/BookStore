import DataBase.ConnectionToDB;
import DataBase.CreateTables;
import Menu.Menu;
import Menu.MainMenu;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectionToDB db = new ConnectionToDB();
        Connection connection = db.getConnection();

        Menu mainMenu = new MainMenu(db);
        int choice;

        //Создание таблиц
        CreateTables createTables = new CreateTables(connection);
        createTables.CreateBooksTable();
        createTables.CreateAuthorsTable();
        createTables.CreateCategoriesTable();
        createTables.CreateOrdersTable();

        Scanner in = new Scanner(System.in);

        while (true) {
            mainMenu.displayMenu();
            choice = in.nextInt();
            mainMenu.handleInput(choice);
        }
    }
}
