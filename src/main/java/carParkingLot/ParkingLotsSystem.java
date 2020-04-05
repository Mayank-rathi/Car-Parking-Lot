package carParkingLot;

import carParkingLot.Enum.DriverType;
import carParkingLot.Exceptions.ParkingLotException;
import carParkingLot.InformerAndObserver.ParkingLotInformer;
import carParkingLot.InformerAndObserver.ParkingLotObserver;

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

    public boolean parkVehicle(Vehicle vehicle, DriverType type) {
        ParkingLot lot = getParkingLotIfAvailableSpace();
        boolean parkedVehicle = lot.parkTheVehicle(vehicle, type);
        return parkedVehicle;
    }

    public ParkingLot getParkingLotIfAvailableSpace() {
        return parkingLotList.stream().sorted(Comparator.comparing(parkingLotList -> parkingLotList.getEmptyParkingSlot().size(),
                Comparator.reverseOrder())).collect(Collectors.toList()).get(0);
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
            return parkingLot.unParking(vehicle);
        }
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }
    public boolean CheckVehicleParked(Vehicle vehicle) {
        for (ParkingLot parkingLots : this.parkingLotList) {
            if (parkingLots.isVehicleParked(vehicle))
                return true;
        }
        throw new ParkingLotException("Vehicle Not Available", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }

}
