package carParkingLot;

public class ParkingLotOwner implements ParkingAttendant {
    private boolean isFullCapacity;
    private int parkingTime;
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
    @Override
    public void setParkingTime(int parkingTime) {
        this.parkingTime = parkingTime;
    }

    @Override
    public int getParkingTime() {
        return parkingTime;
    }
}
