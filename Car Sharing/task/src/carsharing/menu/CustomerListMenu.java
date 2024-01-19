package carsharing.menu;

import carsharing.entity.Customer;

import java.util.Scanner;

public class CustomerListMenu implements ConsoleMenu {

    private static CustomerListMenu INSTANCE;
    private final MenuManager menuManager;


    public CustomerListMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
    }


    public static CustomerListMenu getInstance(MenuManager menuManager) {
        if (INSTANCE == null) {
            INSTANCE = new CustomerListMenu(menuManager);
        }

        return INSTANCE;
    }

    @Override
    public void printMenu() {
        if (!menuManager.getCustomerService().getCustomers().isEmpty()) {
            System.out.println("Choose a customer:");
        }
        menuManager.getCustomerService().printCustomers();
    }

    @Override
    public void run(Scanner scanner) {
        if (menuManager.getCustomerService().getCustomers().isEmpty()) return;
        selectCustomer(scanner);
    }

    private void selectCustomer(Scanner scanner) {
        if (menuManager.getInput().equals("0")) {
            menuManager.switchMenu(MainMenu.getInstance(menuManager));
            return;
        }

        try {
            int index = Integer.parseInt(menuManager.getInput());

            if (index <= menuManager.getCustomerService().getCustomers().size()) {
                Customer currentCustomer = menuManager.getCustomerService().getCustomers().get(index - 1);
                menuManager.setCurrentCustomer(currentCustomer);

                menuManager.switchMenu(CustomerMenu.getInstance(menuManager));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
