package carsharing.menu;

import carsharing.Main;
import carsharing.entity.Company;

import java.util.Scanner;

public class CompanyListMenu implements ConsoleMenu {

    private static CompanyListMenu INSTANCE;
    private final MenuManager menuManager;

    public CompanyListMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
    }


    public static CompanyListMenu getInstance(MenuManager menuManager) {

        if (INSTANCE == null) {
            INSTANCE = new CompanyListMenu(menuManager);
        }

        return INSTANCE;
    }

    @Override
    public void printMenu() {
        if (!menuManager.getCompanyService().getCompanies().isEmpty()) {
            System.out.println("Choose a company:");
        }
        menuManager.getCompanyService().printCompanies();
    }

    @Override
    public void run(Scanner scanner) {
        if (menuManager.getCompanyService().getCompanies().isEmpty()) return;
        selectCompany();
    }

    private void selectCompany() {
        if (menuManager.getInput().equals("0")) {
            menuManager.switchMenu(ManagerMenu.getInstance(menuManager));
            return;
        }

        try {
            int index = Integer.parseInt(menuManager.getInput());

            if (index <= menuManager.getCompanyService().getCompanies().size()) {
                Company currentCompany = menuManager.getCompanyService().getCompanies().get(index - 1);

                menuManager.setCurrentCompany(currentCompany);
                menuManager.switchMenu(CompanyMenu.getInstance(menuManager));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
