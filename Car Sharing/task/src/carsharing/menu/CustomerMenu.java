package carsharing.menu;

import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.util.Scanner;

public class CustomerMenu implements ConsoleMenu {
    private static CustomerMenu INSTANCE;

    private final MenuManager menuManager;

    public CustomerMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public static CustomerMenu getInstance(MenuManager menuManager) {
        if (INSTANCE == null) {
            INSTANCE = new CustomerMenu(menuManager);
        }

        return INSTANCE;
    }

    @Override
    public void printMenu() {
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
    }

    @Override
    public void run(Scanner scanner) {

        switch (menuManager.getInput()) {
            case "1" -> rentCar();
            case "2" -> returnCar();
            case "3" -> printRentedCar();
            case "0" -> menuManager.switchMenu(MainMenu.getInstance(menuManager));
        }
    }

    private void returnCar(){
        Integer rentedCarID = menuManager.getCurrentCustomer().rentedCarId();

        if (rentedCarID == null || rentedCarID <= 0) {
            System.out.println("You didn't rent a car!");
            return;
        }
        Customer updatedCustomer = menuManager.getCustomerService().returnCar(menuManager.getCurrentCustomer());
        menuManager.setCurrentCustomer(updatedCustomer);
        System.out.println("You've returned a rented car!");

    }

    private void rentCar() {
        Integer integer = menuManager.getCurrentCustomer().rentedCarId();

        if(integer == null){
            menuManager.switchMenu(CompanyListMenu.getInstance(menuManager));
            return;
        }

        if (menuManager.getCompanyService().getCompanies().isEmpty()){
            System.out.println("Currently there are no companies to rent a car from.");
            return;
        }

        if (integer > 0) {
            System.out.println("You've already rented a car!");
            return;
        }

        menuManager.switchMenu(CompanyListMenu.getInstance(menuManager));
    }



    private void printRentedCar() {
        Car rentedCar = getRentedCar();

        if(rentedCar == null){
            return;
        }

        Company companyOfRentedCar = menuManager.getCompanyService().getCompanyByID(rentedCar.companyId());

        System.out.println();
        System.out.println("Your rented car:");
        System.out.println(rentedCar.name());
        System.out.println("Company:");
        System.out.println(companyOfRentedCar.name());
        System.out.println();
    }

    private Car getRentedCar() {
        Integer rentedCarID = menuManager.getCurrentCustomer().rentedCarId();

        if (rentedCarID == null || rentedCarID <= 0) {
            System.out.println("You didn't rent a car!");
            return null;
        }

        return menuManager.getCustomerService().getRentedCar(rentedCarID);
    }
}
