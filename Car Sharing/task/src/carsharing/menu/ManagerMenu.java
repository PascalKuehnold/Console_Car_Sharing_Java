package carsharing.menu;

import java.util.Scanner;

public class ManagerMenu implements ConsoleMenu{

    private static ManagerMenu INSTANCE;

    private final MenuManager menuManager;

    public ManagerMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public static ManagerMenu getInstance(MenuManager menuManager){
        menuManager.setCurrentCustomer(null);

        if(INSTANCE == null){
            INSTANCE = new ManagerMenu(menuManager);
        }

        return INSTANCE;
    }

    @Override
    public void run(Scanner scanner) {
        switch (menuManager.getInput()) {
            case "1" -> {
                if (menuManager.getCompanyService().getCompanies().isEmpty()){
                    System.out.println("The company list is empty!");
                    break;
                }
                menuManager.switchMenu(CompanyListMenu.getInstance(menuManager));
            }
            case "2" -> menuManager.getCompanyService().createNewCompany(scanner);
            case "0" -> menuManager.switchMenu(MainMenu.getInstance(menuManager));
        }
    }

    @Override
    public void printMenu() {
        System.out.println();
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }
}
