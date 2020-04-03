package carParkingLot;

import java.time.LocalDateTime;

public class ParkingSlot {
    private Vehicle vehicle;
    public LocalDateTime parkingTime;
    private int slot;

    public ParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public void setSlot(int slotNumber) {
        this.slot = slotNumber;
    }
    public int getSlot() {
        return slot;
    }
    public int getSlotNumber() {
        return slot;
    }
    public int getVehicleSlotNumber() {
        return slot;
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

    public void setVehicleAndTime(Vehicle vehicle) {
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

