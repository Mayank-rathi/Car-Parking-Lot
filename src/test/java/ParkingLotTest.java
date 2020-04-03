import carParkingLot.Enum.DriverType;
import carParkingLot.Exceptions.ParkingLotException;
import carParkingLot.Handler.AirPortSecurityStaff;
import carParkingLot.Handler.ParkingLotOwner;
import carParkingLot.ParkingLot;
import carParkingLot.ParkingLotsSystem;
import carParkingLot.ParkingSlot;
import carParkingLot.Vehicle;
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
    Vehicle vehicle, vehicle2, vehicle3,vehicle4,vehicle5,vehicle6,vehicle7;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
       // parkingSlot = new ParkingSlot(2);
        owner = new ParkingLotOwner();
        parkingLotsSystem = new ParkingLotsSystem();
        airportSecurity = new AirPortSecurityStaff();
        vehicle = new Vehicle("MH-27-BC-3361","White","Maruti","ABC","Mumbai");
        vehicle2 = new Vehicle("MH-25-BC-3341","Red","BMW","XYZ","Navi-Mumbai");
        vehicle3 = new Vehicle("MH-23-BV-4561","Blue","Toyota","PQR","Thane");
        vehicle4 = new Vehicle("MH-78-WS-9685","Black","Tata","LMN","Kalyan");
        vehicle5 = new Vehicle("MH-25-ER-1141","White","Mercedes","AWS","Navi-Mumbai");
        vehicle6 = new Vehicle("MH-23-VC-78961","Blue","Toyota","QWE","Thane");
        vehicle7 = new Vehicle("MH-78-BC-9685","Black","Tata","GHJ","Kalyan");

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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
        boolean isUnparked = parkingLot.unParking(vehicle);
        Assert.assertTrue(isUnparked);
    }

    //Check For Parking Lot Owner
    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        parkingLot.setLotCapacity(3);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = owner.isFullCapacity();
            Assert.assertTrue(isCapacityFull);
        }
    }


    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {

        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.unParking(vehicle);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
            boolean isCapacityFull = airPortSecurity.isFullCapacity();
            Assert.assertTrue(isCapacityFull);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLotFull_ShouldInfromAirPortSecurityStaffs() {
        parkingLotsSystem.registerParkingLots(airportSecurity);

        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
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
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            int findVehicle = parkingLot.findVehicle(vehicle);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkAndTryToFindTheVehicle_ShouldThrowException() {
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            int findVehicle = parkingLot.findVehicle(vehicle3);
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
        try {
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
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
        try {

            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL);

        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isFullCapacity();
            Assert.assertTrue(capacityFull);
        }
    }

    //Check For Evenly Park Vehical
    @Test
    public void givenParkingLotVehicles_WhenAddMoreVehicle_ShouldParkEvenly() {

        parkingLot.setLotCapacity(6);
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
        parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);

        Object emptySlotPosition = parkingLot.getEmptyParkingSlot().get(0);
        Assert.assertEquals(0, emptySlotPosition);
    }

    @Test
    public void givenParkingLot_WhenVehiclesParkedEvenlyIfOneVehicleUnparkedAndAnotherVehicleParked_ShouldReturnSlotNumber() {
        parkingLot.setLotCapacity(5);
        ParkingLot parkingLot1 = new ParkingLot(2);

        parkingLot1.setLotCapacity(2);

        parkingLotsSystem.addToLot(parkingLot1);


        try {
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL);

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


        try {
            parkingLot.parkTheVehicle(vehicle2, DriverType.HANDICAP);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle, DriverType.HANDICAP);
            parkingLot.unParking(vehicle2);
            parkingLot.parkTheVehicle(vehicle2, DriverType.HANDICAP);
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

        try {
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle, DriverType.HANDICAP);
            parkingLot.unParking(vehicle2);
            parkingLot.parkTheVehicle(vehicle2, DriverType.HANDICAP);
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

        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.LARGE_VEHICLE);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot1.parkTheVehicle(vehicle3, DriverType.SMALL_VEHICLE);
            parkingLot1.parkTheVehicle(vehicle4, DriverType.NORMAL);
            parkingLot1.parkTheVehicle(vehicle7, DriverType.NORMAL);
            parkingLot.unParking(vehicle);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.SMALL_VEHICLE);
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

        try {
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.LARGE_VEHICLE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL);
            boolean isVehicleParked = parkingLotsSystem.CheckVehicleParked(vehicle2);
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

        try {
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.LARGE_VEHICLE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL);

            parkingLotsSystem.unParkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.LARGE_VEHICLE);
            int findVehicle = parkingLot.getParkVehicleCount();
            Assert.assertEquals(0, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //UC12
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleByColor() {
        parkingLot.setLotCapacity(10);
        parkingLotsSystem.addToLot(parkingLot);
        try {
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            //parkingLotsSystem.parkVehicle(vehicle5, DriverType.NORMAL);
            List<Integer> carSlotList = parkingLotsSystem.getVehicleByColor("White");
            Assert.assertEquals(1, carSlotList.size());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }
    //UC13
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleByDetail_NamelBlueToyota() {
        parkingLot.setLotCapacity(15);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setLotCapacity(12);
        parkingLotsSystem.addToLot(parkingLot1);
        parkingLotsSystem.registerParkingLots(owner);
        try {
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.LARGE_VEHICLE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle7, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle6, DriverType.NORMAL);
            List<Integer> carSlotList = parkingLotsSystem.getVehicleByNumberPlate("White");
            Assert.assertEquals(2, carSlotList.size());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }
    //UC14
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleByDetail_BMW() {
        parkingLot.setLotCapacity(15);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setLotCapacity(12);
        parkingLotsSystem.addToLot(parkingLot1);
        parkingLotsSystem.registerParkingLots(owner);
        try {
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.LARGE_VEHICLE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle7, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLotsSystem.unParkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.HANDICAP);
            List<Integer> carSlotList = parkingLotsSystem.getVehicleByCarManufacturer("White");
            Assert.assertEquals(2, carSlotList.size());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //UC15
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleByDetail_CarParkInLast30Min() {
        parkingLot.setLotCapacity(15);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setLotCapacity(12);
        parkingLotsSystem.addToLot(parkingLot1);
        parkingLotsSystem.registerParkingLots(owner);
        try {
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.LARGE_VEHICLE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle7, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle6, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle5, DriverType.NORMAL);
            List<String> carSlotList =parkingLot.getVehicleWhichIsParkedFrom30Min();
            Assert.assertEquals(2, carSlotList.size());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }
}

