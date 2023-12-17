import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class App {
    public static void main(String[] args) {

        //fill our database
        DBFiller dbFiller = new DBFiller();
        dbFiller.createCarsTable();
        dbFiller.fillCarsTable();

        /*Database database = new Database();
        ArrayList<Car> carList = database.getCarListByModel("Selenium");
        for(Car car : carList){
            System.out.println(car.getId() + " " + car.getCarNumber() + " " + car.getColor());
        }

        Car car = new Car(UUID.randomUUID(), 123456, 1.8, "blue metallic", "mazda 3");
        database.insertCar(car);
        System.out.println(database.getCarByNumber(123456));*/

        //database.deleteCarByCarNumber(123456);
        //int affectedRows = database.updateCarColor(123456, "white");
        //System.out.println(database.getCarByNumber(123456));
    }
}
