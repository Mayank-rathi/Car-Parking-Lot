package carParkingLot;

import carParkingLot.Enum.DriverType;

public class Vehicle {

    public DriverType driverType;
    public String carColor;
    public String plateNumber;
    public String carManufacturer;
    public String attendantName;
    private String location;

    public Vehicle(DriverType driverType, String carColor, String plateNumber, String carManufacturer, String attendantName, String location) {
        this.driverType = driverType;
        this.carColor = carColor;
        this.plateNumber = plateNumber;
        this.carManufacturer = carManufacturer;
        this.attendantName = attendantName;
        this.location = location;
    }
    public String getColor() {
        return carColor;
    }
    @Override
    public String toString() {
        return "Vehicle{" +
                "driverType=" + driverType +
                ", carColor='" + carColor + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", carManufacturer='" + carManufacturer + '\'' +
                ", attendantName='" + attendantName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

}
