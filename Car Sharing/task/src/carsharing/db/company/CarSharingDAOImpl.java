package carsharing.db.company;

import carsharing.db.DBClient;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class CarSharingDAOImpl implements CarSharingDAO {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String databasePrefix = "jdbc:h2:./src/carsharing/db/";

    private final DBClient dbClient;



    public CarSharingDAOImpl(String databaseName) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(databasePrefix + databaseName);

        dbClient = new DBClient(dataSource);
        //dbClient.run(SQLQueryProvider.DROP_DB);
        dbClient.run(SQLQueryProvider.CREATE_DB);
        dbClient.run(SQLQueryProvider.CREATE_TABLE_CAR);
        dbClient.run(SQLQueryProvider.CREATE_TABLE_CUSTOMER);
    }

    @Override
    public void add(Company company) {
        dbClient.run(String.format(
                SQLQueryProvider.INSERT_DATA, company.id(), company.name()));
        //System.out.println("Company: " + company.name() + " added");
    }

    @Override
    public void addCar(Company company, Car car) {
        dbClient.run(String.format(SQLQueryProvider.ADD_CAR, car.id(), car.name(), company.id(), car.isRented()));
    }

    @Override
    public void addCustomer(Customer customer) {
        dbClient.run(String.format(SQLQueryProvider.CREATE_CUSTOMER, customer.id(), customer.name(), customer.rentedCarId()));
    }

    @Override
    public void rentCar(Car car, Customer customer) {
        dbClient.run(String.format(SQLQueryProvider.UPDATE_CUSTOMER, car.id(), customer.id()));
    }

    @Override
    public List<Customer> findAllCustomers() {
        return dbClient.selectCustomersForList(SQLQueryProvider.SELECT_ALL_CUSTOMERS);
    }

    @Override
    public List<Car> findAllCarsByCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<Company> findAll() {
        return dbClient.selectForList(SQLQueryProvider.SELECT_ALL);
    }

    @Override
    public List<Car> findAllCarsByCompany(Company company) {
        return dbClient.selectCarsForListByCompanyID(String.format(SQLQueryProvider.SELECT_ALL_CARS_BY_COMPANY, company.id()), company.id());
    }

    @Override
    public List<Car> findAllCars() {
        return dbClient.selectCarsForListByCompanyID(SQLQueryProvider.SELECT_ALL_CARS, null);
    }

    @Override
    public Car findCarById(Integer rentedCarID) {
        return dbClient.selectCar(String.format(SQLQueryProvider.SELECT_CAR_BY_ID, rentedCarID));
    }

    @Override
    public Customer returnCar(Customer currentCustomer) {
        dbClient.run(String.format(SQLQueryProvider.REMOVE_CAR_FROM_CUSTOMER, currentCustomer.id()));
        dbClient.run(String.format(SQLQueryProvider.UPDATE_CAR_RENTAL_STATE, false, currentCustomer.rentedCarId()));


        return new Customer(currentCustomer.id(), currentCustomer.name(), null);
    }

    @Override
    public Car updateCarRentalState(Car car) {
        dbClient.run(String.format(SQLQueryProvider.UPDATE_CAR_RENTAL_STATE, !car.isRented(), car.id()));

        return new Car(car.id(), car.name(), car.companyId(), !car.isRented());
    }

    @Override
    public Company findCompanyByCarID(Integer companyId) {
        return dbClient.select(String.format(SQLQueryProvider.SELECT, companyId));
    }

    @Override
    public Company findById(int id) {
        Company company = dbClient.select(String.format(SQLQueryProvider.SELECT, id));

        if (company != null) {
            System.out.println("Company: Id " + id + ", found");
            return company;
        } else {
            System.out.println("Company: Id " + id + ", not found");
            return null;
        }
    }
}
