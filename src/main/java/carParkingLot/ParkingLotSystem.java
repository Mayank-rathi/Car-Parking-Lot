package carParkingLot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingLotSystem {
    private int actualCapacity;
    private List vehicles;
    List CheckSlot;
    private List<ParkingAttendant> attendant;


    public String getWelcomeMessage() {
        return "Welcome";
    }

    public ParkingLotSystem(int capacity) {
        vehicles = new ArrayList();
        attendant = new ArrayList();
        CheckSlot = new ArrayList();
        this.actualCapacity = capacity;
    }

    public void registredParkingLotObserver(ParkingAttendant observer) {
        this.attendant.add(observer);
    }

    public Boolean parkTheVehicle(Object vehicle) {
        if (vehicle != null) {
            CheckSlot.add(vehicle);
            if (this.vehicles.size() == this.actualCapacity) {
                for (ParkingAttendant Attendant : attendant) {
                    Attendant.lotCapacityIsFull();
                }
                throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            }
            if (isVehicleParked(vehicle))
                throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
            this.vehicles.add(vehicle);
            return true;
        }
        return true;
    }


    public Boolean UnParking(Object vehicle) throws ParkingLotException {
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            return true;
        }
        return false;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            return true;
        }
        return false;
    }

    public boolean findVehicle(Object vehicle) {
        if (CheckSlot.contains(vehicle))
            return true;
        throw new ParkingLotException("No Such Vehicle In SLot", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public boolean timeCheck(Object vehicle) {
        if (vehicle != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String time = formatter.format(date);
            return true;
        }
        throw new ParkingLotException("No Such Vehicle Found", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

}
