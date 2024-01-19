package carsharing.services;

import carsharing.db.company.CarSharingDAO;
import carsharing.entity.Car;
import carsharing.entity.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerService {
    CarSharingDAO carSharingDAO;

    public CustomerService(CarSharingDAO carSharingDAO) {
        this.carSharingDAO = carSharingDAO;
    }

    public void printCustomers() {

        List<Customer> allCustomers = carSharingDAO.findAllCustomers();

        if (allCustomers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        }

        allCustomers
                .forEach(customer -> System.out.println(customer.id() + ". " + customer.name()));


        System.out.println("0. Back");
    }

    public void createCustomer(Scanner scanner) {
        System.out.println("Enter the customer name:");
        String customerName = scanner.nextLine();

        int id = carSharingDAO.findAllCustomers().size() + 1;
        carSharingDAO.addCustomer(new Customer(id, customerName, null));

        System.out.println("Customer was created!");
    }

    public List<Customer> getCustomers() {
        return carSharingDAO.findAllCustomers();
    }

    public Car getRentedCar(Integer rentedCarID) {
        return carSharingDAO.findCarById(rentedCarID);
    }

    public void rentCar(Car car, Customer customer) {
        carSharingDAO.rentCar(car, customer);
        carSharingDAO.updateCarRentalState(car);
    }


    public Customer returnCar(Customer currentCustomer) {
        if(currentCustomer == null){
            System.out.println("No Customer is currently active");
            return null;
        }

        return carSharingDAO.returnCar(currentCustomer);
    }
}
