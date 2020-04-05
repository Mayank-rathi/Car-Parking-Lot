package carParkingLot.ParkingConcept;

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
    public List<ParkingSlot> parkingSlots;
    private List<ParkingLotObserver> observerList;
    ParkingLotInformer lotInformer;
    private ParkingSlot parkingSlot;
    public int autoParkingLocation;
    private int autoVehicleCount;
    public int place30min = 30;

    public ParkingLot(int capacity) {
        setLotCapacity(capacity);
        this.observerList = new ArrayList<>();
        lotInformer = new ParkingLotInformer();
    }

    public int getParkVehicleCount() {
        return autoVehicleCount;
    }

    public String getWelcomeMessage() {
        return "Welcome";
    }

    public boolean parking(Vehicle vehicle, DriverType driverType) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
        if (parkingSlots.size() == actualCapacity && !parkingSlots.contains(null)) {
            lotInformer.notifyParkingFull();
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        }
        getAutoParkingLocation(vehicle, driverType);
        return true;
    }

    public Boolean unParking(Vehicle vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle)) {
            this.parkingSlots.set(this.parkingSlots.indexOf(parkingSlot), null);
            lotInformer.notifyParkingAvailable();
            return true;
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public void getAutoParkingLocation(Vehicle vehicle, DriverType driverType) {
        autoParkingLocation = (int) parkingAttender(driverType);
        this.parkingSlots.set(autoParkingLocation, parkingSlot);
        autoVehicleCount++;
        parkingSlot.setVehicleAndTimeDate(vehicle);
        parkingSlot.setSlot(autoVehicleCount);
    }

    public ArrayList<Integer> getEmptyParkingSlot() {
        ArrayList<Integer> emptyParkingSlots = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(slot -> parkingSlots.get(slot) == null).forEach(slot -> emptyParkingSlots.add(slot));
        return emptyParkingSlots;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        return this.parkingSlots.contains(parkingSlot);
    }

    public int findVehicle(Vehicle vehicle) {
        if (isVehicleParked(vehicle))
            return this.parkingSlots.indexOf(parkingSlot);
        throw new ParkingLotException("NO VEHICLE.", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }


    public Integer parkingAttender(DriverType driverType) {
            if(DriverType.HANDICAP.equals(driverType))
                return getEmptyParkingSlot().stream().sorted().collect(Collectors.toList()).get(0);
        if (DriverType.LARGE_VEHICLE.equals(driverType))
            return largeVehiclePlaceAtParkingLot();
        return getEmptyParkingSlot().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
    }

    public int largeVehiclePlaceAtParkingLot() {
        for (int checkPlaceToPark = 0; checkPlaceToPark < parkingSlots.size(); checkPlaceToPark++)
            if (parkingSlots.get(checkPlaceToPark) == null && parkingSlots.get(checkPlaceToPark + 1) == null && parkingSlots.get(checkPlaceToPark + 2) == null)
                return checkPlaceToPark + 2;
        return getEmptyParkingSlot().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
    }

    public void setLotCapacity(int capacity) {
        this.actualCapacity = capacity;
        initializeParkingLot();
    }

    public int initializeParkingLot() {
        this.parkingSlots = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).forEach(slots -> parkingSlots.add(null));
        return parkingSlots.size();
    }

    public ArrayList<Integer> findCarByColour(String colour) {
        try {
            ArrayList<Integer> carColour = new ArrayList<>();
            for (int getCarColour = 0; getCarColour < this.parkingSlots.size(); getCarColour++)
                if ((this.parkingSlots.get(getCarColour) != null))
                    if (this.parkingSlots.get(getCarColour).vehicle.getColor().equals(colour))
                        carColour.add(getCarColour);
            return carColour;
        } catch (ParkingLotException e) {
            throw new ParkingLotException("This colour Vehicle not Found", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
        }
    }
    public List<String> findCarByColourAndCarBrand(String carsColour, String carsType) {
        try {
            List<String> carColour = new ArrayList<>();
            carColour = this.parkingSlots.stream()
                    .filter(parkingSlot -> parkingSlot != null)
                    .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equals(carsColour))
                    .filter(parkingSlot -> parkingSlot.getVehicle().carManufacturer.equals(carsType))
                    .map(parkingSlot -> parkingSlot.getVehicle().getNumberPlate())
                    .collect(Collectors.toList());
            System.out.println("vehicles data " + carColour);
            return carColour;
        } catch (ParkingLotException e) {
            throw new ParkingLotException("No Such Vehicle Available", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
        }
    }
    public List<String> findCarByCarBrand(String carsType) {
        try {
            List<String> carCompany = new ArrayList<>();
            carCompany = this.parkingSlots.stream()
                    .filter(parkingSlot -> parkingSlot != null)
                    .filter(parkingSlot -> parkingSlot.getVehicle().carManufacturer.equals(carsType))
                    .map(parkingSlot -> parkingSlot.getVehicle().getNumberPlate())
                    .collect(Collectors.toList());
            return carCompany;
        } catch (ParkingLotException e) {
            throw new ParkingLotException("No Vehicle Available", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
        }
    }

    public ArrayList<String> getVehicleWhichIsParkedFrom30Min() {
        ArrayList<String> ListOf30Min = new ArrayList<>();
        ListOf30Min = (ArrayList<String>) this.parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot != null)
                .filter(parkingSlot -> parkingSlot.getParkingTime().getMinute() - LocalDateTime.now().getMinute() <= place30min)
                .map(parkingSlot -> parkingSlot.getVehicle().getNumberPlate())
                .collect(Collectors.toList());
        return ListOf30Min;
    }
}
