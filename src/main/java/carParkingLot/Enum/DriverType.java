package carParkingLot.Enum;

public enum DriverType {
    NORMAL(1), HANDICAP(1);
    int capacity = 0;

    DriverType(int capacity) {
        this.capacity = capacity;
    }
}
