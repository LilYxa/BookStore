package Menu.components;

import Menu.Menu;

public class DisplayInfoMenu implements Menu {
        @Override
        public void displayMenu() {
            System.out.println("Выберите действие:");
            System.out.println("1) Вывести список");
            System.out.println("2) Вывести автора");
            System.out.println("3) Вывести категорию");
            System.out.println("4) Вывести заказ");
            System.out.println("0) Вернуться назад");
        }

        @Override
        public void handleInput(int Input) {

        }
}
