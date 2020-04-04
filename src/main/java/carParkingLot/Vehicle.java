package carParkingLot;

public class Vehicle {
    public String carColor;
    public String plateNumber;
    public String carManufacturer;
    public String attendantName;
    public String location;

    public Vehicle(String plateNumber, String carColor , String carManufacturer, String attendantName, String location) {
        this.carColor = carColor;
        this.plateNumber = plateNumber;
        this.carManufacturer = carManufacturer;
        this.attendantName = attendantName;
        this.location = location;
    }

   /* public String getColor() {
        return carColor;
    }
    public String getNumberPlate() {
        return plateNumber;
    }
    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getModelName() {
        return carManufacturer;
    }
*/

    @Override
    public String toString() {
        return "Vehicle{" +
                "carColor='" + carColor + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", carManufacturer='" + carManufacturer + '\'' +
                ", attendantName='" + attendantName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
