package carParkingLot;

public class ParkingLotOwner implements ParkingAttendant {
    private boolean isFullCapacity;

    @Override
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
