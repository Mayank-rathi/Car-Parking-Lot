package carParkingLot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLots {

    private int actualCapacity;
    public List<Object> vehicles;
    private List<ParkingLotObserver> observerList;
    ParkingLotInformer lotInformer;
    private ParkingSlot parkingSlot;

    public ParkingLots(int capacity) {
        setLotCapacity(capacity);
        this.observerList = new ArrayList<>();
        lotInformer=new ParkingLotInformer();
    }

    public String getWelcomeMessage() {
        return "Welcome";
    }

    public boolean parkTheVehicle(Object vehicle, DriverType driverType) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
        if (vehicles.size() == actualCapacity && !vehicles.contains(null)) {
            lotInformer.notifyParkingFull();
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        }
        getAutoParkingLocation(vehicle, driverType);
        return true;
    }

    public Boolean unParking(Object vehicle) throws ParkingLotException {
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.set(this.vehicles.indexOf(vehicle), null);
            return true;
        }
        throw new ParkingLotException("No Vehicle Found....", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public void getAutoParkingLocation(Object vehicle, DriverType driverType, int... slots) {
        if (slots.length == 0) {
            int autoParkingLocation = (int) parkingAttender(driverType).get(0);
            this.vehicles.set(autoParkingLocation, vehicle);
            return;
        }
        this.vehicles.set(slots[0], vehicle);
    }

    public ArrayList getEmptyParkingSlot() {
        ArrayList<Integer> emptyParkingSlots = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) == null)
                .forEach(slot -> emptyParkingSlots.add(slot));
        return emptyParkingSlots;

    }

    public boolean isVehicleParked(Object vehicle) {
        return this.vehicles.contains(vehicle);
    }

    public int findVehicle(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return this.vehicles.indexOf(vehicle);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public ArrayList parkingAttender(DriverType driverType) {
        ArrayList emptyParkingSlotList = getEmptyParkingSlot();
        if (DriverType.NORMAL.equals(driverType))
            Collections.sort(emptyParkingSlotList, Collections.reverseOrder());
        else if (DriverType.HANDICAP.equals(driverType))
            Collections.sort(emptyParkingSlotList);
        return emptyParkingSlotList;
    }

    public void setLotCapacity(int capacity) {
        this.actualCapacity = capacity;
        initializeParkingLot();
    }

    public int initializeParkingLot() {
        this.vehicles = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).forEach(slots -> vehicles.add(null));
        return vehicles.size();
    }

    public void registredParkingAttendant(ParkingLotObserver attendant) {
        observerList.add(attendant);
    }
    public boolean getVehicleParkingTime(Object vehicle) {
        if (isVehicleParked(vehicle))
            return parkingSlot.timeCheck(vehicle);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }
}
