package carParkingLot.Handler;

import carParkingLot.InformerAndObserver.ParkingLotObserver;

public class AirPortSecurityStaff implements ParkingLotObserver {
    private boolean isFullCapacity;
    private boolean isSpaceAvailable;

    public void setParkingCapacityFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isFullCapacity() {
        return this.isFullCapacity;
    }

    @Override
    public void isLotSpaceAvailable() {
        isSpaceAvailable = true;
    }

    public boolean isSpaceAvailable() {
        return this.isSpaceAvailable;
    }
}
