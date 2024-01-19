package carsharing.menu;

import carsharing.db.company.CarSharingDAO;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;
import carsharing.services.CompanyService;
import carsharing.services.CustomerService;

import java.util.Scanner;

public class MenuManager {

    private CompanyService companyService;
    private CustomerService customerService;
    private String input;
    private ConsoleMenu currentMenu;
    private Company currentCompany;

    private Car currentCar;

    private Customer currentCustomer;
    private boolean isRunning;

    private Scanner scanner;

    public MenuManager(CarSharingDAO carSharingDAO) {
        this.companyService = new CompanyService(carSharingDAO);
        this.customerService = new CustomerService(carSharingDAO);
        this.currentMenu = MainMenu.getInstance(this);

        isRunning = true;
        menuLoop();
    }

    public void switchMenu(ConsoleMenu menu) {
        this.currentMenu = menu;
    }

    private void menuLoop() {
        try (Scanner scanner = new Scanner(System.in)) {
            this.scanner = scanner;
            do {
                printMenu(currentMenu);

                input = scanner.nextLine();

                currentMenu.run(scanner);

            } while (isRunning);
        }
    }

    public void rentCar() {
        int carIndex = this.scanner.nextInt();

        if(carIndex == 0){
            switchMenu(CustomerMenu.getInstance(this));
            return;
        }

        Car car = companyService.findAllCarsByCompany(getCurrentCompany()).get(carIndex - 1);
        customerService.rentCar(car, getCurrentCustomer());

        System.out.println("You rented '" + car.name() + "'");

        Customer updatedCustomer = new Customer(getCurrentCustomer().id(), getCurrentCustomer().name(), car.id());
        setCurrentCustomer(updatedCustomer);

        switchMenu(CustomerMenu.getInstance(this));
    }

    private void printMenu(ConsoleMenu menu){
        menu.printMenu();
    }


    //#region Getter/Setter
    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public ConsoleMenu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(ConsoleMenu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public Company getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(Company currentCompany) {
        this.currentCompany = currentCompany;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Car getCurrentCar() {
        return currentCar;
    }

    public void setCurrentCar(Car currentCar) {
        this.currentCar = currentCar;
    }

    //endregion Getter Setter
}
