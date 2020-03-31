package carParkingLot.InformerAndObserver;

public interface ParkingLotObserver {
    void setParkingCapacityFull();

    boolean isFullCapacity();

    void isLotSpaceAvailable();

}
