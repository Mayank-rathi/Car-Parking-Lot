package carParkingLot;

import carParkingLot.Enum.DriverType;
import carParkingLot.Enum.VehicleColors;
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
    public List<ParkingSlot> vehicles;
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

    public boolean parkTheVehicle(Object vehicle, DriverType driverType, VehicleColors colors) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
        if (vehicles.size() == actualCapacity && !vehicles.contains(null)) {
            lotInformer.notifyParkingFull();
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        }
        getAutoParkingLocation(vehicle, driverType,colors);
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

    public void getAutoParkingLocation(Object vehicle, DriverType driverType, VehicleColors colors) {
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
        if (isVehicleParked(vehicle))
            return this.vehicles.indexOf(parkingSlot);
        throw new ParkingLotException("NO VEHICLE.", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public int findByColor(Object color){
        if (this.vehicles.contains(color))
            return this.vehicles.indexOf(color);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public Integer parkingAttender(DriverType driverType) {
            if(DriverType.HANDICAP.equals(driverType))
                return getEmptyParkingSlot().stream().sorted().collect(Collectors.toList()).get(0);
        if(DriverType.NORMAL.equals(driverType))
            return getEmptyParkingSlot().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
        if (DriverType.LARGE_VEHICLE.equals(driverType))
            return getEmptyParkingSlot().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
            return getEmptyParkingSlot().stream().sorted().collect(Collectors.toList()).get(0);
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

    public List<Integer> getVehicleByColor(VehicleColors carColor) {
        try {
            List<Integer> whiteColorSlot = vehicles.stream().filter(slot -> slot.getVehicle() != null)
                    .filter(slot -> slot.getVehicle().toString().equals(carColor))
                    .map(parkingSlot -> parkingSlot.getVehicleSlotNumber()).collect(Collectors.toList());
            return whiteColorSlot;
        } catch (NullPointerException e) {
            throw new ParkingLotException("No Vehicle Found", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
        }
    }

}
