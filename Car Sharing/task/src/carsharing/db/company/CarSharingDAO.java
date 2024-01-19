package carsharing.db.company;

import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.util.List;

public interface CarSharingDAO {
    void add(Company company);
    void addCar(Company company, Car car);

    void addCustomer(Customer customer);

    List<Company> findAll();

    List<Customer> findAllCustomers();

    List<Car> findAllCars();
    List<Car> findAllCarsByCompany(Company company);
    List<Car> findAllCarsByCustomer(Customer customer);
    Company findById(int id);

    Car findCarById(Integer rentedCarID);

    Company findCompanyByCarID(Integer rentedCarID);

    void rentCar(Car car, Customer customer);

    Customer returnCar(Customer currentCustomer);

    Car updateCarRentalState(Car car);
}
