package carParkingLot.Enum;

public enum  VehicleType {
    LARGE(3),SMALL(2);

    int capacity = 0;

    VehicleType(int capacity) {
        this.capacity = capacity;
    }
}
