package carParkingLot;

import java.time.LocalDateTime;

public class ParkingSlot {
    public Vehicle vehicle;
    public LocalDateTime parkingTime;
    public int VehicleSlotNumber;

    public ParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setSlot(int slotNumber) {
        this.VehicleSlotNumber = slotNumber;
    }

    public int getVehicleSlotNumber() {
        return VehicleSlotNumber;
    }

    public void setVehicleParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getParkingTime() {
        return parkingTime;
    }

    public void setVehicleAndTimeDate(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.parkingTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ParkingSlot that = (ParkingSlot) obj;
        return vehicle.equals(that.vehicle);
    }
}

