package carParkingLot.Handler;

import carParkingLot.InformerAndObserver.ParkingLotObserver;

public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;
    private boolean isLotSpaceAvailable;
    private boolean isSpaceAvailable;

    @Override
    public void setParkingCapacityFull()     {
        isFullCapacity = true;
    }

    @Override
    public boolean isFullCapacity() {
        return this.isFullCapacity;
    }

    @Override
    public void isLotSpaceAvailable() {
        isLotSpaceAvailable = false;
    }
    public boolean isSpaceAvailable() {
        return this.isSpaceAvailable;
    }
}
