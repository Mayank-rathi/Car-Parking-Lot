package carParkingLot;

public class AirPortSecurityStaff implements ParkingLotObserver {
    private boolean isFullCapacity;

    public void setParkingCapacityFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isParkingLotFull() {
        return this.isFullCapacity;
    }

    @Override
    public void setParkingAvailable() {
        isFullCapacity=false;
    }
}
