package carsharing.menu;

import carsharing.entity.Car;
import carsharing.entity.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompanyMenu implements ConsoleMenu {

    private static CompanyMenu INSTANCE;

    private final MenuManager menuManager;

    private static List<Car> availableCompanyCars;

    public CompanyMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
        availableCompanyCars = new ArrayList<>();
    }


    public static CompanyMenu getInstance(MenuManager menuManager) {
        if (INSTANCE == null) {
            INSTANCE = new CompanyMenu(menuManager);
        }

        return INSTANCE;
    }

    @Override
    public void run(Scanner scanner) {
        Company currentCompany = menuManager.getCurrentCompany();

        switch (menuManager.getInput()) {
            case "1" -> {
                printCars(currentCompany);
            }
            case "2" -> menuManager.getCompanyService().addCarToCompany(scanner, currentCompany);
            case "0" -> menuManager.switchMenu(ManagerMenu.getInstance(menuManager));
        }
    }

    private void printCars(Company currentCompany) {
        availableCompanyCars = menuManager
                .getCompanyService()
                .getAvailableCompanyCars(menuManager.getCurrentCompany());


        if (availableCompanyCars == null || availableCompanyCars.isEmpty()) {
            System.out.println("The car list is empty!");
            return;
        }


        System.out.println("Car List:");
        menuManager.getCompanyService().printCompanyCars(currentCompany);

    }

    @Override
    public void printMenu() {
        Company currentCompany = menuManager.getCurrentCompany();
        availableCompanyCars = menuManager
                .getCompanyService()
                .getAvailableCompanyCars(menuManager.getCurrentCompany());


        if (menuManager.getCurrentCustomer() != null) {

            if (!availableCompanyCars.isEmpty()) {
                System.out.println("Choose a car:");

                menuManager.getCompanyService().printCompanyCars(availableCompanyCars);

                System.out.println("0. Back");


                menuManager.rentCar();
                return;
            }

            System.out.println("The car list is empty!");
            menuManager.switchMenu(MainMenu.getInstance(menuManager));

            return;
        }

        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }


}
