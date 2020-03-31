import carParkingLot.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ParkingLotTest {
    ParkingLots parkingLots;
    ParkingSlot parkingSlot;
    ParkingLotOwner owner;
    AirPortSecurityStaff airportSecurity;
    Object vehicle, vehicle2, vehicle3;

    @Before
    public void setUp() {
        parkingLots = new ParkingLots(2);
        parkingSlot = new ParkingSlot(2);
        owner = new ParkingLotOwner();
        airportSecurity = new AirPortSecurityStaff();
        parkingLots.registredParkingAttendant(owner);
        vehicle = new Object();
        vehicle2 = new Object();
        vehicle3 = new Object();
    }

    @Test
    public void giveWelcomeMessage_ForParking_Lot() {
        String welcomeCheck = parkingLots.getWelcomeMessage();
        Assert.assertEquals(welcomeCheck, "Welcome");
    }

    //Check For Park And Unpark Vehicle
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldReturnTrue() {
        parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
        boolean isParked = parkingLots.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_ShouldReturnFalse() {
        try {
            parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
        boolean isUnparked = parkingLots.unParking(vehicle);
        Assert.assertTrue(isUnparked);
    }

    //Check For Parking Lot Owner
    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLots.registredParkingAttendant(owner);
        try {
            parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLots.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLots.parkTheVehicle(vehicle3, DriverType.NORMAL);
            boolean isCapacityFull = owner.isParkingLotFull();
            Assert.assertTrue(isCapacityFull);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {
        try {
            parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLots.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLots.unParking(vehicle);
            boolean isParkedVehicle = parkingLots.parkTheVehicle(vehicle3, DriverType.NORMAL);
            Assert.assertTrue(isParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, e.type);
        }
    }

    //Check For AirPort Security Staff
    @Test
    public void givenParkingLot_WhenLotIsFull_ShouldInfromAirPortSecurityStaff() {
        AirPortSecurityStaff airPortSecurity = new AirPortSecurityStaff();
        parkingLots.registredParkingAttendant(airPortSecurity);
        try {
            parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLots.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLots.parkTheVehicle(vehicle3, DriverType.NORMAL);
            boolean isCapacityFull = airPortSecurity.isParkingLotFull();
            Assert.assertTrue(isCapacityFull);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnTrue() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLots.registredParkingAttendant(owner);
        try {
            parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLots.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLots.parkTheVehicle(vehicle3, DriverType.NORMAL);
            parkingLots.unParking(vehicle2);
            boolean spaceAvailable = owner.isParkingLotFull();
            Assert.assertTrue(spaceAvailable);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }

    }

    @Test
    public void givenParkingLot_ForParkingAttendant_WhenParkingAttendantToParkACar_ShouldOwnerDecideWitchSlotParkTheCar() {
        parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingSlot.setVehicleParkingSlot(vehicle, 5);
        int vehicleParked = parkingLots.findVehicle(vehicle);
        Assert.assertEquals(1, vehicleParked);
    }

    @Test
    public void givenParkingLot_AsParkingAttendant_WhenParkingLotIsFull_ShouldThrowException() {
        ParkingSlot parkingSlot = new ParkingSlot(2);
        try {
            parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLots.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingSlot.setVehicleParkingSlot(vehicle, 7);
            parkingSlot.setVehicleParkingSlot(vehicle2, 4);
            boolean slotAvailable = parkingSlot.isSlotAvailable();
            parkingLots.parkTheVehicle(vehicle3, DriverType.NORMAL);
            Assert.assertTrue(slotAvailable);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_FULL, e.type);
        }
    }

    //Parking attendant to park cars
    @Test
    public void givenParkingLot_AsParkingAttendant_WhenSlotIsAvailable_ShouldReturnTrue() {
        parkingLots.getAutoParkingLocation(vehicle, DriverType.HANDICAP);
        boolean vehicleParked = parkingLots.isVehicleParked(vehicle);
        Assert.assertTrue(vehicleParked);
    }

    //Find my car
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleAtThatSlot() {
        parkingLots.parkTheVehicle(vehicle, DriverType.HANDICAP);
        parkingLots.parkTheVehicle(vehicle2, DriverType.NORMAL);
        int findSlotNo = parkingLots.findVehicle(vehicle);
        Assert.assertEquals(0, findSlotNo);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkAndTryToFindTheVehicle_ShouldThrowException() {
        try {
            parkingLots.findVehicle(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //Check for time
    @Test
    public void givenParkingLot_WhenDriverIsParkVehicalAtSomeTime_ShouldReturnTime() {
        boolean vehiclePark = parkingLots.parkTheVehicle(vehicle3, DriverType.NORMAL);
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
        parkingLots.setLotCapacity(6);
        parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLots.parkTheVehicle(vehicle2, DriverType.NORMAL);
        parkingLots.parkTheVehicle(vehicle3, DriverType.NORMAL);
        parkingLots.parkTheVehicle(new Object(), DriverType.NORMAL);
        parkingLots.parkTheVehicle(new Object(), DriverType.NORMAL);
        Object emptySlotPosition = parkingLots.getEmptyParkingSlot().get(0);
        Assert.assertEquals(0, emptySlotPosition);
    }

    //Check For Handicap
    @Test
    public void givenCarToPark_whenDriverIsHandicap_shouldParkedAtNearestSpot() {
        parkingLots.setLotCapacity(20);
        parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLots.parkTheVehicle(new Object(), DriverType.NORMAL);
        parkingLots.parkTheVehicle(vehicle2, DriverType.NORMAL);
        parkingLots.parkTheVehicle(new Object(), DriverType.HANDICAP);
        parkingLots.unParking(vehicle2);
        parkingLots.unParking(vehicle);
        parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLots.parkTheVehicle(vehicle2, DriverType.HANDICAP);
        int checkNearestLocation = parkingLots.findVehicle(vehicle2);
        Assert.assertEquals(1, checkNearestLocation);
    }
}

