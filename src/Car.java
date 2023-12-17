import java.util.UUID;

public class Car {
    private UUID id;
    private int carNumber;
    private double motorVolume;
    private String color;
    private String model;

    public Car() {
    }

    public Car(UUID id, int carNumber, double motorVolume, String color, String model) {
        this.id = UUID.randomUUID();
        this.carNumber = carNumber;
        this.motorVolume = motorVolume;
        this.color = color;
        this.model = model;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public double getMotorVolume() {
        return motorVolume;
    }

    public void setMotorVolume(double motorVolume) {
        this.motorVolume = motorVolume;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carNumber=" + carNumber +
                ", motorVolume=" + motorVolume +
                ", color='" + color + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
