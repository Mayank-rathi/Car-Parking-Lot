package carParkingLot;

public class AirPortSecurityStaff implements ParkingAttendant {
    private boolean isFullCapacity;

    public void lotCapacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isSpaceAvailable() {
        return false;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
