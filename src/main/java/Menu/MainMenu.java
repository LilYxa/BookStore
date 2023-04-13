package Menu;

import DataBase.ConnectionToDB;
import Menu.components.AddRecordMenu;
import Menu.components.DisplayInfoMenu;
import Menu.components.RemoveRecordMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu implements Menu {
    private List<Menu> subMenus;
    private Scanner in = new Scanner(System.in);
    private ConnectionToDB db;
    public MainMenu(ConnectionToDB db) {
        this.db = db;
        subMenus = new ArrayList<>();
        subMenus.add(new AddRecordMenu(db));
        subMenus.add(new RemoveRecordMenu());
        subMenus.add(new DisplayInfoMenu());
    }

    @Override
    public void displayMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1) Добавить запись");
        System.out.println("2) Удалить запись");
        System.out.println("3) Вывод информации");
        System.out.println("0) Выйти из приложения");
    }

    @Override
    public void handleInput(int input) {
        switch(input) {
            case 1:
                subMenus.get(0).displayMenu();
                int subMenuInput = in.nextInt();
                subMenus.get(0).handleInput(subMenuInput);
                break;
            case 2:
                subMenus.get(1).displayMenu();
                subMenuInput = in.nextInt();
                subMenus.get(1).handleInput(subMenuInput);
                break;
            case 3:
                subMenus.get(2).displayMenu();
                subMenuInput = in.nextInt();
                subMenus.get(2).handleInput(subMenuInput);
                break;
            case 0:
                db.closeConnection();
                System.exit(0);
            default:
                System.out.println("Некорректное действие!");
        }
    }
}
