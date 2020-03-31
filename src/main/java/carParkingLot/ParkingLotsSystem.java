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

public class ParkingLotsSystem {
    List<ParkingLot> parkingLotList;
    ParkingLotInformer parkingLotInformer;
    boolean addToLot;

    public ParkingLotsSystem() {
        parkingLotInformer = new ParkingLotInformer();
        this.parkingLotList = new ArrayList<>();
    }

    public void addToLot(ParkingLot parkingLot) {
        addToLot = this.parkingLotList.add(parkingLot);
    }

    public boolean parkVehicle(Object vehicle, DriverType type) {
        ParkingLot lot = getParkingLotIfAvailableSpace();
        boolean parkedVehicle = lot.parkTheVehicle(vehicle, type, VehicleType.SMALL_VEHICLE);
        return parkedVehicle;
    }

    public ParkingLot getParkingLotIfAvailableSpace() {
        return parkingLotList.stream().sorted(Comparator.comparing(parkingLotList -> parkingLotList.getEmptyParkingSlot().size(),
                Comparator.reverseOrder())).collect(Collectors.toList()).get(0);
    }

    public void registerParkingLots(ParkingLotObserver observer) {
        parkingLotInformer.registerParkingLots(observer);
    }

    public boolean unParkVehicle(Object vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : this.parkingLotList) {
            return parkingLot.unParking(vehicle);
        }
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }
}
