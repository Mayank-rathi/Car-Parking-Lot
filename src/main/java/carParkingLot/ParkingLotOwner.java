package carParkingLot;

public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;

    @Override
    public void setParkingCapacityFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isParkingLotFull() {
        return this.isFullCapacity;
    }

    @Override
    public void setParkingAvailable() {
        isFullCapacity = true;
    }


}
