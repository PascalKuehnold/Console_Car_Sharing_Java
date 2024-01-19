package carsharing.menu;

import java.util.Scanner;

public class MainMenu implements ConsoleMenu {

    private static MainMenu INSTANCE;

    private final MenuManager menuManager;

    public MainMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public static MainMenu getInstance(MenuManager menuManager) {
        if (INSTANCE == null) {
            INSTANCE = new MainMenu(menuManager);
        }

        return INSTANCE;
    }

    @Override
    public void run(Scanner scanner) {
        switch (menuManager.getInput()) {
            case "0" -> menuManager.setRunning(false);
            case "1" -> menuManager.switchMenu(ManagerMenu.getInstance(menuManager));
            case "2" -> {
                if (menuManager.getCustomerService().getCustomers().isEmpty()) {
                    System.out.println("The customer list is empty!");
                    break;
                }
                menuManager.switchMenu(CustomerListMenu.getInstance(menuManager));
            }
            case "3" -> menuManager.getCustomerService().createCustomer(scanner);
        }
    }

    @Override
    public void printMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
    }
}
