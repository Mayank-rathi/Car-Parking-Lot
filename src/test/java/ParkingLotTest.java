import carParkingLot.Enum.DriverType;
import carParkingLot.Enum.VehicleType;
import carParkingLot.Exceptions.ParkingLotException;
import carParkingLot.Handler.AirPortSecurityStaff;
import carParkingLot.Handler.ParkingLotOwner;
import carParkingLot.ParkingLot;
import carParkingLot.ParkingLotsSystem;
import carParkingLot.ParkingSlot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;


public class ParkingLotTest {
    ParkingLot parkingLot;
    ParkingSlot parkingSlot;
    ParkingLotsSystem parkingLotsSystem;
    ParkingLotOwner owner;
    AirPortSecurityStaff airportSecurity;
    Object vehicle, vehicle2, vehicle3;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
        parkingSlot = new ParkingSlot(2);
        owner = new ParkingLotOwner();
        parkingLotsSystem = new ParkingLotsSystem();
        airportSecurity = new AirPortSecurityStaff();
        vehicle = new Object();
        vehicle2 = new Object();
        vehicle3 = new Object();
    }

    @Test
    public void giveWelcomeMessage_ForParking_Lot() {
        String welcomeCheck = parkingLot.getWelcomeMessage();
        Assert.assertEquals(welcomeCheck, "Welcome");
    }

    //Check For Park And Unpark Vehicle
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldReturnTrue() {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle);
        LocalDateTime expectedParkingTime = LocalDateTime.now();
        parkingSlot.setVehicleAndTime(vehicle);
        LocalDateTime vehicleParkingTime = parkingSlot.getParkingTime();
        Assert.assertEquals(expectedParkingTime, vehicleParkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_ShouldReturnFalse() {
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        boolean isUnparked = parkingLot.unParking(vehicle);
        Assert.assertTrue(isUnparked);
    }

    //Check For Parking Lot Owner
    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        parkingLot.setLotCapacity(3);
        Object vehicle2 = new Object();
        Object vehicle = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = owner.isFullCapacity();
            Assert.assertTrue(isCapacityFull);
        }
    }


    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {
        Object vehicle2 = new Object();
        Object vehicle = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.unParking(vehicle);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            boolean isParkedVehicle = parkingLot.isVehicleParked(vehicle3);
            Assert.assertTrue(isParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //Check For AirPort Security Staff
    @Test
    public void givenParkingLot_WhenLotIsFull_ShouldInfromAirPortSecurityStaff() {
        AirPortSecurityStaff airPortSecurity = new AirPortSecurityStaff();
        parkingLotsSystem.registerParkingLots(airPortSecurity);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            boolean isCapacityFull = airPortSecurity.isFullCapacity();
            Assert.assertTrue(isCapacityFull);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLotFull_ShouldInfromAirPortSecurityStaffs() {
        parkingLotsSystem.registerParkingLots(airportSecurity);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = airportSecurity.isFullCapacity();
            Assert.assertTrue(isCapacityFull);
        }
    }


    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnTrue() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotsSystem.registerParkingLots(owner);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.unParking(vehicle2);
            boolean spaceAvailable = owner.isFullCapacity();
            Assert.assertTrue(spaceAvailable);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }

    }

    //UC6
    @Test
    public void givenParkingLot_ForParkingAttendant_WhenParkingAttendantToParkACar_ShouldOwnerDecideWitchSlotParkTheCar() {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle);
        try {
            parkingLot.setLotCapacity(3);
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingSlot.setVehicleParkingSlot(vehicle);
            int locationParkedVehicle = parkingLot.autoParkingLocation;
            Assert.assertEquals(2, locationParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    //Parking attendant to park cars
    @Test
    public void givenParkingLot_AsParkingAttendant_WhenSlotIsAvailable_ShouldReturnTrue() {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle);
        try {
            parkingLot.setLotCapacity(3);
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingSlot.setVehicleParkingSlot(vehicle);
            int locationParkedVehicle = parkingLot.autoParkingLocation;
            Assert.assertEquals(2, locationParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    //Find my car
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleAtThatSlot() {
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            int findVehicle = parkingLot.findVehicle(vehicle);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkAndTryToFindTheVehicle_ShouldThrowException() {
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            int findVehicle = parkingLot.findVehicle(new Object());
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //Check for time
    @Test
    public void givenParkingLot_WhenDriverIsParkVehicalAtSomeTime_ShouldReturnTime() {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle);
        LocalDateTime expectedParkingTime = LocalDateTime.now();
        parkingSlot.setVehicleAndTime(vehicle);
        LocalDateTime vehicleParkingTime = parkingSlot.getParkingTime();
        Assert.assertEquals(expectedParkingTime, vehicleParkingTime);
    }


    @Test
    public void givenParkingLot_WhenVehicleIsNotParkedInParkingLot_ShouldThrowException() {
        Object vehicle2 = new Object();
        try {
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.findVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLotCheckCapacity_WhenLotIsFull_ShouldReturnTrue() {
        parkingLot.setLotCapacity(3);
        parkingLot.initializeParkingLot();
        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLot1.setLotCapacity(2);
        parkingLotsSystem.addToLot(parkingLot1);
        parkingLotsSystem.registerParkingLots(owner);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        Object vehicle5 = new Object();
        try {
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle4, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(new Object(), DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle5, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isFullCapacity();
            Assert.assertTrue(capacityFull);
        }
    }

    //Check For Evenly Park Vehical
    @Test
    public void givenParkingLotVehicles_WhenAddMoreVehicle_ShouldParkEvenly() {
        parkingLot.setLotCapacity(6);
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        parkingLot.parkTheVehicle(new Object(), DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        parkingLot.parkTheVehicle(new Object(), DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
        Object emptySlotPosition = parkingLot.getEmptyParkingSlot().get(0);
        Assert.assertEquals(0, emptySlotPosition);
    }

    @Test
    public void givenParkingLot_WhenVehiclesParkedEvenlyIfOneVehicleUnparkedAndAnotherVehicleParked_ShouldReturnSlotNumber() {
        parkingLot.setLotCapacity(5);
        parkingLot.initializeParkingLot();
        ParkingLot parkingLot1 = new ParkingLot(2);

        parkingLot1.setLotCapacity(2);
        parkingLot1.initializeParkingLot();

        parkingLotsSystem.addToLot(parkingLot1);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        Object vehicle5 = new Object();

        try {
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle4, DriverType.NORMAL);
            parkingLotsSystem.unParkVehicle(vehicle);
            parkingLotsSystem.parkVehicle(vehicle5, DriverType.NORMAL);
            int locationParkedVehicle = parkingLot.autoParkingLocation;
            Assert.assertEquals(4, locationParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLotVehicleWithHandicappedDriver_WhenParkTheVehicle_ShouldParkNearestLot() {
        parkingLot.setLotCapacity(5);
        parkingLot.initializeParkingLot();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        try {
            parkingLot.parkTheVehicle(vehicle2, DriverType.HANDICAP, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle, DriverType.HANDICAP, VehicleType.SMALL_VEHICLE);
            parkingLot.unParking(vehicle2);
            parkingLot.parkTheVehicle(vehicle4, DriverType.HANDICAP, VehicleType.SMALL_VEHICLE);
            int findVehiclePosition = parkingLot.findVehicle(vehicle);
            Assert.assertEquals(1, findVehiclePosition);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //Check For Handicap
    @Test
    public void givenCarToPark_whenDriverIsHandicap_shouldParkedAtNearestSpot() {
        parkingLot.setLotCapacity(5);
        parkingLot.initializeParkingLot();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        try {
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL_VEHICLE);
            parkingLot.parkTheVehicle(vehicle, DriverType.HANDICAP, VehicleType.SMALL_VEHICLE);
            parkingLot.unParking(vehicle2);
            parkingLot.parkTheVehicle(vehicle4, DriverType.HANDICAP, VehicleType.SMALL_VEHICLE);
            int findVehiclePosition = parkingLot.findVehicle(vehicle4);
            Assert.assertEquals(1, findVehiclePosition);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }
}

