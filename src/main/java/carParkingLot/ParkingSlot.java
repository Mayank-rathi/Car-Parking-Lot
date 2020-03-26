package carParkingLot;

import java.util.ArrayList;
import java.util.List;

public class ParkingSlot implements ParkingLotObserver {
    private int availableSlotNumber;
    private int actualCapacity;
    private Object vehicle;
    private boolean slotIsFull;
    List slotNumberList;
    int slotCount;

    public ParkingSlot(int availableSlotNumber) {
        this.availableSlotNumber = availableSlotNumber;
        slotNumberList = new ArrayList();
        this.actualCapacity = availableSlotNumber;
    }


    public void setVehicleParkingSlot(Object vehicle, int slotNumber) {
        this.vehicle = vehicle;
        slotNumberList.add(slotNumber);
    }

    public boolean isSlotAvailable() {
        if (availableSlotNumber != slotCount)
            throw new ParkingLotException("Slot Is Full", ParkingLotException.ExceptionType.SLOT_FULL);
        return true;
    }

    @Override
    public void setCapaCity() {
        return;
    }

    @Override
    public boolean isSpaceAvailable() {
        return false;
    }

}

