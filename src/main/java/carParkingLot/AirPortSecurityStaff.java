package carParkingLot;

public class AirPortSecurityStaff implements ParkingLotObserver {
    private boolean isFullCapacity;

    public void setCapaCity() {
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
