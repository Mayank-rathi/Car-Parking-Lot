package carParkingLot;

public class ParkingLotException extends RuntimeException {
    public ExceptionType type;

    public enum ExceptionType {
        PARKING_LOT_FULL, VEHICLE_IS_ALREADY_PARK, SLOT_FULL,NO_VEHICLE_FOUND, DATA_NOT_FOUND;
    }

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}