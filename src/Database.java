import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class Database {


    //getting connection to database method
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
    }

    public boolean insertCar(Car car){
        boolean inserted = false;
        String query = "INSERT INTO cars (id, car_number, motor_volume, color, model) VALUES (?,?,?,?,?);";

        try(Connection connection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            int index = 1;
            //filling first value in preparedStatement (id)
            preparedStatement.setObject(index++, car.getId(), Types.OTHER);
            //filling second value and so on...
            preparedStatement.setInt(index++, car.getCarNumber());
            preparedStatement.setDouble(index++, car.getMotorVolume());
            preparedStatement.setString(index++, car.getColor());
            preparedStatement.setString(index, car.getModel());
            preparedStatement.executeUpdate();

            System.out.println("Record added to database");
            inserted = true;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Connection failed");
        }

        return inserted;
    }

    public Car getCarByNumber(int carNumber){
        Car car = null;
        String query = "SELECT * FROM cars WHERE car_number = ? ;";

        try(Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, carNumber);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                UUID id = resultSet.getObject(1, java.util.UUID.class);
                double motorVolume = resultSet.getDouble(3);
                String color = resultSet.getString(4);
                String carModel = resultSet.getString(5);
                car = new Car(id, carNumber, motorVolume, color, carModel);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return car;
    }

    public int deleteCarByCarNumber(int carNumber){
        String query = "DELETE FROM cars WHERE car_number = ?;";
        int affectedRows = 0;

        try(Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, carNumber);
            affectedRows = preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return affectedRows;
    }

    public int updateCarColor(int carNumber, String newColor){
        String query = "UPDATE cars SET color = ? WHERE car_number = ?;";
        int affectedRows = 0;

        try (Connection connection = connect();//another way of getting connection - through connect() method
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newColor);
            preparedStatement.setInt(2, carNumber);

            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return affectedRows;
    }

    public int getCarCount(){
        String query = "SELECT COUNT(*) FROM cars;";
        int count = 0;
        try(Connection connection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
            //todo Statement use example
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)){
            resultSet.next();
            count = resultSet.getInt(1);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return count;
    }


    public ArrayList<Car> getCarListByModel(String model){
        ArrayList<Car> carList = new ArrayList<>();
        String query = "SELECT * FROM cars WHERE model = ?;";

        try(Connection connection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
                //replacing first and only "?" parameter with our model variable in query
                preparedStatement.setString(1, model);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    //iterate through result set's records
                    while(resultSet.next()){
                        //we can get data from the record row by column's name...
                        UUID id = resultSet.getObject("id", java.util.UUID.class);
                        //...or by row's number
                        int carNumber = resultSet.getInt(2);
                        double motorVolume = resultSet.getDouble(3);
                        String color = resultSet.getString(4);
                        String carModel = resultSet.getString(5);
                        carList.add(new Car(id, carNumber, motorVolume, color, carModel));
                    }
                }catch (SQLException e){
                    //another way to print exception message
                    System.out.println(e.getMessage());
                }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return carList;
    }
}
