package carParkingLot.Exceptions;

public class ParkingLotException extends RuntimeException {
    public ExceptionType type;

    public enum ExceptionType {
        PARKING_LOT_FULL, VEHICLE_IS_ALREADY_PARK,NO_VEHICLE_FOUND;
    }

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}