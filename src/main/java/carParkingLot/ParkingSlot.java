package carParkingLot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void setParkingCapacityFull() {
        return;
    }

    @Override
    public boolean isParkingLotFull() {
        return false;
    }

    @Override
    public void setParkingAvailable() {
    return ;
    }
    public static boolean timeCheck(Object vehicle) {
        if (vehicle != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String time = formatter.format(date);
            return true;
        }
        throw new ParkingLotException("No Such Vehicle Found", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }
}

