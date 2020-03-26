import carParkingLot.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ParkingLotTest {
    ParkingLotSystem parkingLotSystem;
    ParkingSlot parkingSlot;
    ParkingLotOwner owner;
    AirPortSecurityStaff airportSecurity;
    Object vehicle, vehicle2, vehicle3;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(2);
        parkingSlot = new ParkingSlot(2);
        owner = new ParkingLotOwner();
        airportSecurity = new AirPortSecurityStaff();
        parkingLotSystem.registredParkingAttendant(owner);
        vehicle = new Object();
        vehicle2 = new Object();
        vehicle3 = new Object();
    }

    @Test
    public void giveWelcomeMessage_ForParking_Lot() {
        String welcomeCheck = parkingLotSystem.getWelcomeMessage();
        Assert.assertEquals(welcomeCheck, "Welcome");
    }

    //Check For Park And Unpark Vehicle
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldReturnTrue() {
        parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
        boolean isUnparked = parkingLotSystem.unParking(vehicle);
        Assert.assertTrue(isUnparked);
    }

    //Check For Parking Lot Owner
    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotSystem.registredParkingAttendant(owner);
        try {
            parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLotSystem.parkTheVehicle(vehicle3, DriverType.NORMAL);
            boolean isCapacityFull = owner.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {
        try {
            parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLotSystem.unParking(vehicle);
            boolean isParkedVehicle = parkingLotSystem.parkTheVehicle(vehicle3, DriverType.NORMAL);
            Assert.assertTrue(isParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, e.type);
        }
    }

    //Check For AirPort Security Staff
    @Test
    public void givenParkingLot_WhenLotIsFull_ShouldInfromAirPortSecurityStaff() {
        AirPortSecurityStaff airPortSecurity = new AirPortSecurityStaff();
        parkingLotSystem.registredParkingAttendant(airPortSecurity);
        try {
            parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLotSystem.parkTheVehicle(vehicle3, DriverType.NORMAL);
            boolean isCapacityFull = airPortSecurity.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnTrue() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotSystem.registredParkingAttendant(owner);
        try {
            parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLotSystem.parkTheVehicle(vehicle3, DriverType.NORMAL);
            parkingLotSystem.unParking(vehicle2);
            boolean spaceAvailable = owner.isCapacityFull();
            Assert.assertTrue(spaceAvailable);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }

    }

    @Test
    public void givenParkingLot_ForParkingAttendant_WhenParkingAttendantToParkACar_ShouldOwnerDecideWitchSlotParkTheCar() {
        parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingSlot.setVehicleParkingSlot(vehicle, 5);
        int vehicleParked = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(1, vehicleParked);
    }

    @Test
    public void givenParkingLot_AsParkingAttendant_WhenParkingLotIsFull_ShouldThrowException() {
        ParkingSlot parkingSlot = new ParkingSlot(2);
        try {
            parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingSlot.setVehicleParkingSlot(vehicle, 7);
            parkingSlot.setVehicleParkingSlot(vehicle2, 4);
            boolean slotAvailable = parkingSlot.isSlotAvailable();
            parkingLotSystem.parkTheVehicle(vehicle3, DriverType.NORMAL);
            Assert.assertTrue(slotAvailable);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, e.type);
        }
    }

    //Parking attendant to park cars
    @Test
    public void givenParkingLot_AsParkingAttendant_WhenSlotIsAvailable_ShouldReturnTrue() {
        parkingLotSystem.getAutoParkingLocation(vehicle, DriverType.HANDICAP);
        boolean vehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(vehicleParked);
    }

    //Find my car
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleAtThatSlot() {
        parkingLotSystem.parkTheVehicle(vehicle, DriverType.HANDICAP);
        parkingLotSystem.parkTheVehicle(vehicle2, DriverType.NORMAL);
        int findSlotNo = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(0, findSlotNo);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkAndTryToFindTheVehicle_ShouldThrowException() {
        try {
            parkingLotSystem.findVehicle(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //Check for time
    @Test
    public void givenParkingLot_WhenDriverIsParkVehicalAtSomeTime_ShouldReturnTime() {
        boolean vehiclePark = parkingLotSystem.parkTheVehicle(vehicle3, DriverType.NORMAL);
        boolean timeCheck = Vehicle.timeCheck(vehiclePark);
        Assert.assertTrue(timeCheck);
    }

    @Test
    public void givenParkingLot_WhenDriverIsNotParkVehicalAtSomeTime_ShouldThrowException() {
        try {
            boolean conditions = Vehicle.timeCheck(null);
            Assert.assertTrue(conditions);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //Check For Evenly Park Vehical
    @Test
    public void givenParkingLotVehicles_WhenAddMoreVehicle_ShouldParkEvenly() {
        parkingLotSystem.setLotCapacity(6);
        parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(vehicle2, DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(vehicle3, DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(new Object(), DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(new Object(), DriverType.NORMAL);
        Object emptySlotPosition = parkingLotSystem.getEmptyParkingSlot().get(0);
        Assert.assertEquals(0, emptySlotPosition);
    }

    //Check For Handicap
    @Test
    public void givenCarToPark_whenDriverIsHandicap_shouldParkedAtNearestSpot() {
        parkingLotSystem.setLotCapacity(20);
        parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(new Object(), DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(vehicle2, DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(new Object(), DriverType.HANDICAP);
        parkingLotSystem.unParking(vehicle2);
        parkingLotSystem.unParking(vehicle);
        parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(vehicle2, DriverType.HANDICAP);
        int checkNearestLocation = parkingLotSystem.findVehicle(vehicle2);
        Assert.assertEquals(1, checkNearestLocation);
    }
}

