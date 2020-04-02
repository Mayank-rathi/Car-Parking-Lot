package carParkingLot.Enum;

public enum DriverType {
    NORMAL(1), HANDICAP(1), LARGE_VEHICLE(3), SMALL_VEHICLE(2);
    int capacity = 0;

    DriverType(int capacity) {
        this.capacity = capacity;
    }
}
