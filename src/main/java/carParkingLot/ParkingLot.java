package carParkingLot;

import carParkingLot.Enum.DriverType;
import carParkingLot.Exceptions.ParkingLotException;
import carParkingLot.InformerAndObserver.ParkingLotInformer;
import carParkingLot.InformerAndObserver.ParkingLotObserver;

import java.time.LocalDateTime;
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
    private int vehicleCount;


    public ParkingLot(int capacity) {
        setLotCapacity(capacity);
        this.observerList = new ArrayList<>();
        lotInformer = new ParkingLotInformer();
    }

    public int getParkVehicleCount() {
        return vehicleCount;
    }

    public String getWelcomeMessage() {
        return "Welcome";
    }

    public boolean parkTheVehicle(Vehicle vehicle, DriverType driverType) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
        if (vehicles.size() == actualCapacity && !vehicles.contains(null)) {
            lotInformer.notifyParkingFull();
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        }
        getAutoParkingLocation(vehicle, driverType);
        return true;
    }


    public Boolean unParking(Vehicle vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle)) {
            this.vehicles.set(this.vehicles.indexOf(parkingSlot), null);
            lotInformer.notifyParkingAvailable();
            return true;
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public void getAutoParkingLocation(Vehicle vehicle, DriverType driverType) {
        autoParkingLocation = (int) parkingAttender(driverType);
        this.vehicles.set(autoParkingLocation, parkingSlot);
        vehicleCount++;
        parkingSlot.setVehicleAndTime(vehicle);
        parkingSlot.setSlot(vehicleCount);
    }

    public ArrayList<Integer> getEmptyParkingSlot() {
        ArrayList<Integer> emptyParkingSlots = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) == null).forEach(slot -> emptyParkingSlots.add(slot));
        return emptyParkingSlots;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        return this.vehicles.contains(parkingSlot);
    }

    public int findVehicle(Vehicle vehicle) {
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

    public List<String> getVehicleWhichIsParkedFrom30Min() {
        try {
            List<String> MinParkVehicleList = new ArrayList<>();
            MinParkVehicleList = this.vehicles.stream()
                    .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                    .filter(parkingSlot -> parkingSlot.getParkingTime().getMinute() - LocalDateTime.now().getMinute() <= 30)
                    .map(parkingSlot -> ((parkingSlot.getVehicleSlotNumber())) + " " + (parkingSlot.getVehicle().getModelName()) + " " + (parkingSlot.getVehicle().getNumberPlate()))
                    .collect(Collectors.toList());
            return MinParkVehicleList;
        } catch (NullPointerException e) {
            throw new ParkingLotException("No Vehicle Available", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
        }

    }
}
