import carParkingLot.Enum.DriverType;
import carParkingLot.Enum.VehicleColors;
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
import java.util.List;


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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
        parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.RED);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.unParking(vehicle);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            int findVehicle = parkingLot.findVehicle(vehicle);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkAndTryToFindTheVehicle_ShouldThrowException() {
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle4, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle5, DriverType.NORMAL, VehicleColors.WHITE);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isFullCapacity();
            Assert.assertTrue(capacityFull);
        }
    }

    //Check For Evenly Park Vehical
    @Test
    public void givenParkingLotVehicles_WhenAddMoreVehicle_ShouldParkEvenly() {
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle = new Object();
        Object vehicle5 = new Object();
        parkingLot.setLotCapacity(6);
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
        parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
        parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
        parkingLot.parkTheVehicle(new Object(), DriverType.NORMAL, VehicleColors.WHITE);
        parkingLot.parkTheVehicle(new Object(), DriverType.NORMAL, VehicleColors.WHITE);
        Object emptySlotPosition = parkingLot.getEmptyParkingSlot().get(0);
        Assert.assertEquals(0, emptySlotPosition);
    }

    @Test
    public void givenParkingLot_WhenVehiclesParkedEvenlyIfOneVehicleUnparkedAndAnotherVehicleParked_ShouldReturnSlotNumber() {
        parkingLot.setLotCapacity(5);
        ParkingLot parkingLot1 = new ParkingLot(2);

        parkingLot1.setLotCapacity(2);

        parkingLotsSystem.addToLot(parkingLot1);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        Object vehicle5 = new Object();

        try {
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle4, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.unParkVehicle(vehicle);
            parkingLotsSystem.parkVehicle(vehicle5, DriverType.NORMAL, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle2, DriverType.HANDICAP, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle, DriverType.HANDICAP, VehicleColors.WHITE);
            parkingLot.unParking(vehicle2);
            parkingLot.parkTheVehicle(vehicle4, DriverType.HANDICAP, VehicleColors.WHITE);
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
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle, DriverType.HANDICAP, VehicleColors.WHITE);
            parkingLot.unParking(vehicle2);
            parkingLot.parkTheVehicle(vehicle4, DriverType.HANDICAP, VehicleColors.WHITE);
            int findVehiclePosition = parkingLot.findVehicle(vehicle);
            Assert.assertEquals(0, findVehiclePosition);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //UC11
    @Test
    public void givenParkingAttendant_WhenLargeCareEnter_ShouldAlocateLotWhichHasHighestNumberOfFreeSpace() {
        parkingLot.setLotCapacity(10);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setLotCapacity(15);
        parkingLotsSystem.addToLot(parkingLot1);

        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        Object vehicle5 = new Object();
        Object vehicle = new Object();

        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.LARGE_VEHICLE, VehicleColors.WHITE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot1.parkTheVehicle(vehicle3, DriverType.SMALL_VEHICLE, VehicleColors.WHITE);
            parkingLot1.parkTheVehicle(vehicle4, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLot.unParking(vehicle);
            parkingLotsSystem.parkVehicle(vehicle5, DriverType.SMALL_VEHICLE, VehicleColors.WHITE);
            int locationParkedVehicle = parkingLot.autoParkingLocation;
            Assert.assertEquals(8, locationParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenLargeVehicleParked_ShouldReturnTrue() {
        parkingLot.setLotCapacity(5);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setLotCapacity(8);
        parkingLotsSystem.addToLot(parkingLot1);

        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingLot2.setLotCapacity(2);
        parkingLotsSystem.addToLot(parkingLot2);

        parkingLotsSystem.registerParkingLots(owner);

        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        Object vehicle5 = new Object();
        Object vehicle = new Object();
        try {
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle4, DriverType.HANDICAP, VehicleColors.WHITE);
            parkingLotsSystem.parkVehicle(vehicle5, DriverType.NORMAL, VehicleColors.WHITE);
            boolean isVehicleParked = parkingLotsSystem.CheckVehicleParked(vehicle4);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenParkedLargeVehicleNotFoundInLot_ShouldThrowException() {
        parkingLot.setLotCapacity(15);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setLotCapacity(12);
        parkingLotsSystem.addToLot(parkingLot1);
        parkingLotsSystem.registerParkingLots(owner);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        Object vehicle5 = new Object();
        Object vehicle = new Object();
        try {
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleColors.RED);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.LARGE_VEHICLE, VehicleColors.WHITE);
            parkingLotsSystem.unParkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.HANDICAP, VehicleColors.RED);
            parkingLotsSystem.parkVehicle(vehicle4, DriverType.SMALL_VEHICLE, VehicleColors.WHITE);
            boolean findVehicle = parkingLotsSystem.CheckVehicleParked(vehicle2);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //UC12
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleByColor() {
        parkingLot.setLotCapacity(15);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setLotCapacity(12);
        parkingLotsSystem.addToLot(parkingLot1);
        parkingLotsSystem.registerParkingLots(owner);
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        Object vehicle5 = new Object();
        Object vehicle = new Object();
        try {
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleColors.RED);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.LARGE_VEHICLE, VehicleColors.WHITE);
            parkingLotsSystem.unParkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.HANDICAP, VehicleColors.RED);
            parkingLotsSystem.parkVehicle(vehicle4, DriverType.SMALL_VEHICLE, VehicleColors.WHITE);
            List<Integer> carSlotList = parkingLot.getVehicleByColor(VehicleColors.WHITE);
            Assert.assertEquals(3, carSlotList.size());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }
}

