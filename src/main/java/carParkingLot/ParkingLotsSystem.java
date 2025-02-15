package carParkingLot;

import carParkingLot.Enum.DriverType;
import carParkingLot.Enum.VehicleType;
import carParkingLot.Exceptions.ParkingLotException;
import carParkingLot.InformerAndObserver.ParkingLotInformer;
import carParkingLot.InformerAndObserver.ParkingLotObserver;
import carParkingLot.ParkingConcept.ParkingLot;
import carParkingLot.ParkingConcept.ParkingSlot;
import carParkingLot.ParkingConcept.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotsSystem {
    List<ParkingLot> parkingLotList;
    private List<ParkingSlot> list;
    ParkingLotInformer parkingLotInformer;
    boolean addToLot;
    private int vehicleCount;

    public ParkingLotsSystem() {
        parkingLotInformer = new ParkingLotInformer();
        this.parkingLotList = new ArrayList<>();
    }

    public void addToLot(ParkingLot parkingLot) {
        addToLot = this.parkingLotList.add(parkingLot);
    }

    public boolean parkVehicle(Vehicle vehicle, DriverType type, VehicleType vehicleType) {
        ParkingLot lot = getParkingLotIfAvailableSpace();
        boolean parkedVehicle = lot.park(vehicle, type, vehicleType);
        return parkedVehicle;
    }

    public ParkingLot getParkingLotIfAvailableSpace() {
        return parkingLotList.stream()
                .sorted(Comparator.comparing(parkingLotList -> parkingLotList.getEmptyParkingSlotForVehicle().size(),
                        Comparator.reverseOrder()))
                .collect(Collectors.toList()).get(0);
    }

    public int findVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList)
            return parkingLot.findVehicle(vehicle);
        throw new ParkingLotException("No Vehicle place Found", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }


    public void registerParkingLots(ParkingLotObserver observer) {
        parkingLotInformer.registerParkingLots(observer);
    }

    public boolean unParkVehicle(Vehicle vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : this.parkingLotList) {
            return parkingLot.unPark(vehicle);
        }
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

    public boolean CheckVehicleParked(Vehicle vehicle) {
        for (ParkingLot parkingLots : this.parkingLotList) {
            if (parkingLots.CheckVehicleParked(vehicle))
                return true;
        }
        throw new ParkingLotException("Vehicle Not Available", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }
}
