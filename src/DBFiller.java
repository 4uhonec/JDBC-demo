import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;


public class DBFiller {
    private final SecureRandom random = new SecureRandom();

    public void createCarsTable(){
        String query = "CREATE TABLE IF NOT EXISTS cars (" +
                "id UUID PRIMARY KEY," +
                "car_number INT," +
                "motor_volume DOUBLE PRECISION," +
                "color VARCHAR(50)," +
                "model VARCHAR(50)"
                + ");";
        try(Connection connection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
            Statement statement = connection.createStatement()){
                statement.executeUpdate(query);
                System.out.println("Table 'cars' created successfully");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void fillCarsTable(){
        ArrayList<Car> carList = createCarList();
        String query = "INSERT INTO cars (id, car_number, motor_volume, color, model) VALUES (?,?,?,?,?);";
        //try-with-resources, Connection and PreparedStatement objects will auto close when try block ends
        try(Connection connection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                int recordsCount = 0;

                for(Car car: carList){
                    int index = 1;
                    //filling first value in preparedStatement
                    preparedStatement.setObject(index++, car.getId(), Types.OTHER);
                    //filling second value and so on...
                    preparedStatement.setInt(index++, car.getCarNumber());
                    preparedStatement.setDouble(index++, car.getMotorVolume());
                    preparedStatement.setString(index++, car.getColor());
                    preparedStatement.setString(index, car.getModel());
                    //preparedStatement.executeUpdate(); //executing 1 query many times

                    preparedStatement.addBatch();  //adding query for future batch execution
                    recordsCount++;

                    // execute every 100 rows or less
                    if (recordsCount % 100 == 0 || recordsCount == carList.size()) {
                        preparedStatement.executeBatch();
                    }
                }


                preparedStatement.executeBatch();  //executing amount of queries in batch
            System.out.println("Records added to database");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Connection failed");
        }
    }

    private ArrayList<Car> createCarList(){
        ArrayList<Car> carList = new ArrayList<>();

        //filling our car list to insert into car table
        for(int i = 0; i<50; i++){
            Car car = new Car(UUID.randomUUID(),
                    random.nextInt(9000000) + 1000000,
                                Constants.motorVolumeArr[random.nextInt(Constants.motorVolumeArr.length)],
                                Constants.colorArr[random.nextInt(Constants.colorArr.length)],
                                Constants.modelArr[random.nextInt(Constants.modelArr.length)]);
            carList.add(car);
        }

        return carList;
    }
}
