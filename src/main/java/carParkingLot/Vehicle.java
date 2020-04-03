package carParkingLot;

import carParkingLot.Enum.DriverType;
import carParkingLot.Enum.VehicleColors;

public class Vehicle {

    public DriverType driverType;
    public VehicleColors colors;
    public String carColor;
    public String plateNumber;
    public String carManufacturer;
    public String attendantName;
    private String location;

    public Vehicle(String plateNumber,DriverType driverType, String carColor , String carManufacturer, String attendantName, String location) {
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
