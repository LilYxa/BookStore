package Menu.components;

import Menu.Menu;

public class RemoveRecordMenu implements Menu {
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
    public void handleInput(int Input) {

    }
}
