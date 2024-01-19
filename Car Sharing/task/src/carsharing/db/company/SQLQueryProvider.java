package carsharing.db.company;

public class SQLQueryProvider {
    public static final String DROP_DB = """
            DROP TABLE COMPANY;
            DROP TABLE CAR;
            DROP TABLE CUSTOMER;""";

    public static final String CREATE_DB = "CREATE TABLE IF NOT EXISTS COMPANY  " +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " name VARCHAR(255) NOT NULL UNIQUE, " +
            " PRIMARY KEY ( id ))";

    public static final String CREATE_TABLE_CAR = "CREATE TABLE IF NOT EXISTS CAR  " +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " name VARCHAR(255) NOT NULL UNIQUE, " +
            " company_id INT NOT NULL, " +
            " is_rented BOOL NOT NULL, " +
            " PRIMARY KEY ( id ), " +
            " CONSTRAINT fk_company_id FOREIGN KEY (company_id) REFERENCES COMPANY(id) " +
            " )";

    public static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE IF NOT EXISTS CUSTOMER  " +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " name VARCHAR(255) NOT NULL UNIQUE, " +
            " rented_car_id INT, " +
            " PRIMARY KEY ( id ), " +
            " CONSTRAINT fk_rented_car_id FOREIGN KEY (rented_car_id) REFERENCES CAR(id) " +
            " )";

    public static final String CREATE_CUSTOMER = "INSERT INTO CUSTOMER VALUES (%d, '%s', %d)";
    public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER";


    public static final String ADD_CAR = "INSERT INTO CAR VALUES (%d, '%s', %d, %b)";

    public static final String SELECT_ALL = "SELECT * FROM COMPANY";
    public static final String SELECT_ALL_CARS = "SELECT * FROM CAR";

    public static final String SELECT_CAR_BY_ID = "SELECT * FROM CAR WHERE id = %d";
    public static final String SELECT = "SELECT * FROM COMPANY WHERE id = %d";

    public static final String SELECT_ALL_CARS_BY_COMPANY = "SELECT * FROM CAR WHERE company_id = %d";
    public static final String INSERT_DATA = "INSERT INTO COMPANY VALUES (%d, '%s')";

    public static final String UPDATE_CUSTOMER = "UPDATE CUSTOMER SET rented_car_id = %d WHERE id = %d";
    public static final String REMOVE_CAR_FROM_CUSTOMER = "UPDATE CUSTOMER SET rented_car_id = NULL WHERE id = %d";

    public static final String UPDATE_CAR_RENTAL_STATE = "UPDATE CAR SET is_rented = %b WHERE id = %d";
}
