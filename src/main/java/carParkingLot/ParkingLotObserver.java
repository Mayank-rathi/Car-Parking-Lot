package carParkingLot;

public interface ParkingLotObserver {
    void setParkingCapacityFull();

    boolean isParkingLotFull();

    void setParkingAvailable();

}
