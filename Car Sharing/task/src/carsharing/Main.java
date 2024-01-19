package carsharing;

import carsharing.db.company.CarSharingDAO;
import carsharing.db.company.CarSharingDAOImpl;
import carsharing.menu.MenuManager;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String databaseName;

        if (args.length > 1 && Objects.equals(args[0], "-databaseFileName")) {
            databaseName = args[1];
        } else {
            databaseName = "DatabaseCarSharingNew4";
        }

        CarSharingDAO carSharingDAO = new CarSharingDAOImpl(databaseName);
        new MenuManager(carSharingDAO);

    }
}