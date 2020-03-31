package carParkingLot;

import carParkingLot.Enum.DriverType;
import carParkingLot.Enum.VehicleType;
import carParkingLot.Exceptions.ParkingLotException;
import carParkingLot.InformerAndObserver.ParkingLotInformer;
import carParkingLot.InformerAndObserver.ParkingLotObserver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {

    private int actualCapacity;
    public List<Object> vehicles;
    private List<ParkingLotObserver> observerList;
    ParkingLotInformer lotInformer;
    private ParkingSlot parkingSlot;
    public int autoParkingLocation;

    public ParkingLot(int capacity) {
        setLotCapacity(capacity);
        this.observerList = new ArrayList<>();
        lotInformer=new ParkingLotInformer();
    }

    public String getWelcomeMessage() {
        return "Welcome";
    }

    public boolean parkTheVehicle(Object vehicle, DriverType driverType, VehicleType smallVehicle) throws ParkingLotException {
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
        if (isVehicleParked(vehicle)) {
            this.vehicles.set(this.vehicles.indexOf(parkingSlot), null);
            lotInformer.notifyParkingAvailable();
            return true;
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public void getAutoParkingLocation(Object vehicle, DriverType driverType) {
        autoParkingLocation = (int) parkingAttender(driverType);
        this.vehicles.set(autoParkingLocation, parkingSlot);
    }

    public ArrayList<Integer> getEmptyParkingSlot() {
        ArrayList<Integer> emptyParkingSlots = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) == null).forEach(slot -> emptyParkingSlots.add(slot));
        return emptyParkingSlots;
    }

    public boolean isVehicleParked(Object vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        return this.vehicles.contains(parkingSlot);
    }

    public int findVehicle(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return this.vehicles.indexOf(vehicle);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public Integer parkingAttender(DriverType driverType) {
            if(DriverType.HANDICAP.equals(driverType))
                return getEmptyParkingSlot().stream().sorted().collect(Collectors.toList()).get(0);
        return getEmptyParkingSlot().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
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
}
