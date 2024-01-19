package carsharing.services;

import carsharing.db.company.CarSharingDAO;
import carsharing.entity.Car;
import carsharing.entity.Company;

import java.util.List;
import java.util.Scanner;

public class CompanyService {
    CarSharingDAO carSharingDAO;

    public CompanyService(CarSharingDAO carSharingDAO) {
        this.carSharingDAO = carSharingDAO;
    }


    public void printCompanies() {

        carSharingDAO.findAll()
                .forEach(company -> System.out.println(company.id() + ". " + company.name()));

        System.out.println("0. Back");
    }

    public List<Car> findAllCarsByCompany(Company company) {
        return carSharingDAO.findAllCarsByCompany(company);
    }

    public List<Car> getAvailableCompanyCars(Company company) {
        List<Car> allCarsByCompany = findAllCarsByCompany(company);


        return allCarsByCompany.stream()
                .filter(car -> !car.isRented())
                .toList();
    }

    public void printCompanyCars(Company company){
        List<Car> allCarsByCompany = findAllCarsByCompany(company);

        for (int i = 0; i < allCarsByCompany.size(); i++) {
            System.out.println((i + 1) + ". " + allCarsByCompany.get(i).name());
        }

        System.out.println();
    }

    public void printCompanyCars(List<Car> rentedCars) {

        for (int i = 0; i < rentedCars.size(); i++) {
            System.out.println((i + 1) + ". " + rentedCars.get(i).name());
        }

    }


    public List<Company> getCompanies() {
        return carSharingDAO.findAll();
    }

    public void createNewCompany(Scanner scanner) {
        System.out.println("Enter the company name: ");
        String companyName = scanner.nextLine();

        int id = carSharingDAO.findAll().size() + 1;
        carSharingDAO.add(new Company(id, companyName));

        System.out.println("The company was created!");
    }

    public void addCarToCompany(Scanner scanner, Company company) {
        System.out.println("Enter the car name: ");
        String carName = scanner.nextLine();

        int id = carSharingDAO.findAllCars().size();

        Car newCar = new Car(id + 1, carName, company.id(), false);
        carSharingDAO.addCar(company, newCar);

        System.out.println("Car was added!");
    }

    public Company getCompanyByID(Integer rentedCarID) {
        return carSharingDAO.findCompanyByCarID(rentedCarID);
    }
}
