import carParkingLot.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ParkingLotTest {
    private static ParkingLotSystem parkingLotSystem;
    private static ParkingSlot parkingSlot;
    public static Object vehicle, vehicle2, vehicle3;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(2);
        parkingSlot = new ParkingSlot(2);
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
        boolean isParked = parkingLotSystem.parkTheVehicle(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.parkTheVehicle(vehicle);
            parkingLotSystem.parkTheVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLotSystem.parkTheVehicle(vehicle);
        boolean isUnparked = parkingLotSystem.UnParking(vehicle);
        Assert.assertTrue(isUnparked);
    }

    //Check For Parking Lot Owner
    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotSystem.registredParkingLotObserver(owner);
        try {
            parkingLotSystem.parkTheVehicle(vehicle);
            parkingLotSystem.parkTheVehicle(vehicle2);
            parkingLotSystem.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
        }
        boolean isCapacityFull = owner.isCapacityFull();
        Assert.assertTrue(isCapacityFull);
    }

    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {
        try {
            parkingLotSystem.parkTheVehicle(vehicle);
            parkingLotSystem.parkTheVehicle(vehicle2);
            parkingLotSystem.UnParking(vehicle);
            boolean isParkedVehicle = parkingLotSystem.parkTheVehicle(vehicle3);
            Assert.assertTrue(isParkedVehicle);
        } catch (ParkingLotException e) {
        }
    }

    //Check For AirPort Security Staff
    @Test
    public void givenParkingLot_WhenLotIsFull_ShouldInfromAirPortSecurityStaff() {
        AirPortSecurityStaff airPortSecurity = new AirPortSecurityStaff();
        parkingLotSystem.registredParkingLotObserver(airPortSecurity);
        try {
            parkingLotSystem.parkTheVehicle(vehicle);
            parkingLotSystem.parkTheVehicle(vehicle2);
            parkingLotSystem.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
        }
        boolean isCapacityFull = airPortSecurity.isCapacityFull();
        Assert.assertTrue(isCapacityFull);
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnTrue() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLotSystem.registredParkingLotObserver(owner);
        try {
            parkingLotSystem.parkTheVehicle(vehicle);
            parkingLotSystem.parkTheVehicle(vehicle2);
            parkingLotSystem.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
        }
        parkingLotSystem.UnParking(vehicle2);
        boolean spaceAvailable = owner.isCapacityFull();
        Assert.assertTrue(spaceAvailable);
    }

    @Test
    public void givenParkingLot_ForParkingAttendant_WhenParkingAttendantToParkACar_ShouldOwnerDecideWitchSlotParkTheCar() {
        ParkingSlot parkingSlot = new ParkingSlot(1);
        try {
            parkingLotSystem.parkTheVehicle(vehicle);
            parkingSlot.setVehicleParkingSlot(vehicle, 5);
            boolean vehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(vehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_AsParkingAttendant_WhenParkingLotIsFull_ShouldThrowException() {
        ParkingSlot parkingSlot = new ParkingSlot(2);
        try {
            parkingLotSystem.parkTheVehicle(vehicle);
            parkingLotSystem.parkTheVehicle(vehicle2);
            parkingSlot.setVehicleParkingSlot(vehicle, 7);
            parkingSlot.setVehicleParkingSlot(vehicle2, 4);
            boolean slotAvailable = parkingSlot.isSlotAvailable();
            parkingLotSystem.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_AsParkingAttendant_WhenSlotIsAvailable_ShouldReturnTrue() {
        ParkingSlot parkingSlot = new ParkingSlot(2);
        try {
            parkingLotSystem.parkTheVehicle(vehicle);
            parkingSlot.setVehicleParkingSlot(vehicle, 12);
            boolean slotAvailable = parkingSlot.isSlotAvailable();
            Assert.assertTrue(slotAvailable);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleAtThatSlot() {
        try {
            Boolean vehicle0 = parkingLotSystem.parkTheVehicle(vehicle);
            parkingSlot.setVehicleParkingSlot(vehicle, 15);
            boolean findVehicle = parkingLotSystem.findVehicle(vehicle0);
            Assert.assertTrue(findVehicle);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkAndTryToFindTheVehicle_ShouldThrowException() {
        try {
            parkingLotSystem.findVehicle(ParkingLotTest.vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }
}

