import carParkingLot.Enum.DriverType;
import carParkingLot.Enum.VehicleType;
import carParkingLot.Exceptions.ParkingLotException;
import carParkingLot.Handler.AirPortSecurityStaff;
import carParkingLot.Handler.ParkingLotOwner;
import carParkingLot.ParkingConcept.ParkingLot;
import carParkingLot.ParkingConcept.ParkingSlot;
import carParkingLot.ParkingConcept.Vehicle;
import carParkingLot.ParkingLotsSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        owner = new ParkingLotOwner();
        parkingLotsSystem = new ParkingLotsSystem();
        airportSecurity = new AirPortSecurityStaff();
        vehicle = new Vehicle("MH-27-BC-3361","White","BMW","ABC","Mumbai");
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
        parkingSlot.setVehicleAndTimeDate(vehicle);
        LocalDateTime vehicleParkingTime = parkingSlot.getParkingTime();
        Assert.assertEquals(expectedParkingTime, vehicleParkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_ShouldReturnFalse() {
        try {
            parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
        parkingLot.parking(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
        boolean isUnparked = parkingLot.unParking(vehicle);
        Assert.assertTrue(isUnparked);
    }

    //Check For Parking Lot Owner
    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        parkingLot.setLotCapacity(3);
        try {
            parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle3, DriverType.NORMAL, VehicleType.SMALL);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = owner.isFullCapacity();
            Assert.assertTrue(isCapacityFull);
        }
    }


    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {

        try {
            parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.unParking(vehicle);
            parkingLot.parking(vehicle3, DriverType.NORMAL, VehicleType.SMALL);
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
            parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle3, DriverType.NORMAL, VehicleType.SMALL);
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
            parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle3, DriverType.NORMAL, VehicleType.SMALL);
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
            parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
            parkingLot.parking(vehicle3, DriverType.NORMAL, VehicleType.SMALL);
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
            parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
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
            parkingLot.parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
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
            parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            int findVehicle = parkingLot.findVehicle(vehicle);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkAndTryToFindTheVehicle_ShouldThrowException() {
        try {
            parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.SMALL);
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
        parkingSlot.setVehicleAndTimeDate(vehicle);
        LocalDateTime vehicleParkingTime = parkingSlot.getParkingTime();
        Assert.assertEquals(expectedParkingTime, vehicleParkingTime);
    }


    @Test
    public void givenParkingLot_WhenVehicleIsNotParkedInParkingLot_ShouldThrowException() {
        try {
            parkingLot.parking(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
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

            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);

        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isFullCapacity();
            Assert.assertTrue(capacityFull);
        }
    }

    //Check For Evenly Park Vehical
    @Test
    public void givenParkingLotVehicles_WhenAddMoreVehicle_ShouldParkEvenly() {

        parkingLot.setLotCapacity(6);
        parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        parkingLot.parking(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
        parkingLot.parking(vehicle3, DriverType.NORMAL,VehicleType.SMALL);

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
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);

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
            parkingLot.parking(vehicle2, DriverType.HANDICAP,VehicleType.SMALL);
            parkingLot.parking(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.parking(vehicle, DriverType.HANDICAP,VehicleType.SMALL);
            parkingLot.unParking(vehicle2);
            parkingLot.parking(vehicle2, DriverType.HANDICAP,VehicleType.SMALL);
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
            parkingLot.parking(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.parking(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.parking(vehicle, DriverType.HANDICAP,VehicleType.SMALL);
            parkingLot.unParking(vehicle2);
            parkingLot.parking(vehicle2, DriverType.HANDICAP,VehicleType.SMALL);
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
        parkingLotsSystem.addToLot(parkingLot);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setLotCapacity(15);
        parkingLotsSystem.addToLot(parkingLot1);
        try {
            parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.LARGE);
            parkingLot.parking(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot1.parking(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot1.parking(vehicle4, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot1.parking(vehicle7, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.unParking(vehicle);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            int locationParkedVehicle = parkingLot.autoParkingLocation;
            Assert.assertEquals(9, locationParkedVehicle);
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
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
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
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.LARGE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);

            parkingLotsSystem.unParkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.LARGE);
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
        parkingLotsSystem.registerParkingLots(owner);
        Vehicle vehicle10=new Vehicle("MH-27-BC-5489","White","corona","NoOne","Home");
        try {
            parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.parking(vehicle2, DriverType.HANDICAP,VehicleType.SMALL);
            parkingLot.parking(vehicle3, DriverType.NORMAL,VehicleType.LARGE);
            parkingLot.parking(vehicle5, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.parking(vehicle10, DriverType.NORMAL,VehicleType.SMALL);
            List<Integer> carSlotList = parkingLot.findCarByColour("White");
            ArrayList<Integer> whiteColorCars = new ArrayList<>();
            whiteColorCars.add(7);
            whiteColorCars.add(8);
            whiteColorCars.add(9);
            System.out.println("Location Of White Cars Place" + carSlotList);
            Assert.assertEquals(whiteColorCars, carSlotList);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }
    @Test
    public void givenParkingLot_WhenNoWhiteVehicleIsParked_ShouldThrowException() {
        parkingLot.setLotCapacity(10);
        parkingLotsSystem.addToLot(parkingLot);
        parkingLotsSystem.registerParkingLots(owner);
        Vehicle vehicle10=new Vehicle("MH-27-BC-5489","White","corona","NoOne","Home");
        try {
            parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.parking(vehicle2, DriverType.HANDICAP,VehicleType.SMALL);
            parkingLot.parking(vehicle3, DriverType.NORMAL,VehicleType.LARGE);
            parkingLot.parking(vehicle5, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.parking(vehicle10, DriverType.NORMAL,VehicleType.SMALL);
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
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.LARGE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle7, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle6, DriverType.NORMAL,VehicleType.SMALL);
            List<String> carSlotList = parkingLot1.findCarByColourAndCarBrand("Blue","Toyota");
            List<String> carByColorAndBrand = new ArrayList<>();
            carByColorAndBrand.add("MH-23-VC-78961");
            carByColorAndBrand.add("MH-23-BV-4561");
            System.out.println("Location Of All Blue Toyota Cars " + carSlotList);
            Assert.assertEquals(carByColorAndBrand, carSlotList);
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
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.LARGE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle7, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            List<String> carSlotList = parkingLot1.findCarByCarBrand("BMW");
            List<String> carByColorAndBrand = new ArrayList<>();
            carByColorAndBrand.add("MH-25-BC-3341");
            carByColorAndBrand.add("MH-27-BC-3361");
            System.out.println("Location Of All BMW Cars " + carSlotList);
            Assert.assertEquals(carByColorAndBrand, carSlotList);
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
            parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.LARGE);
            parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle7, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            List<String> carSlotList = parkingLot1.getVehicleWhichIsParkedFrom30Min();
            List<String> carByColorAndBrand = new ArrayList<>();
            carByColorAndBrand.add("MH-25-BC-3341");
            carByColorAndBrand.add("MH-27-BC-3361");
            carByColorAndBrand.add("MH-78-BC-9685");
            carByColorAndBrand.add("MH-23-BV-4561");
            System.out.println("Location Of Vehicles Place From 30min " + carSlotList);
            Assert.assertEquals(carByColorAndBrand, carSlotList);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    //UC16
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldFindTheVehicleByDetail_ForSmallHandicapVehicle() {
            parkingLot.setLotCapacity(5);
            parkingLotsSystem.addToLot(parkingLot);
            try {
                parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
                parkingLotsSystem.parkVehicle(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
                parkingLotsSystem.parkVehicle(vehicle, DriverType.HANDICAP,VehicleType.SMALL);
                parkingLotsSystem.unParkVehicle(vehicle2);
                parkingLotsSystem.parkVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
                int findVehiclePosition = parkingLotsSystem.findVehicle(vehicle2);
                Assert.assertEquals(4, findVehiclePosition);
            } catch (ParkingLotException e) {
                Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
            }
        }

    //UC17
    @Test
    public void givenAllVehicles_whenThatAreParked_shouldReturnTotalSlotNumber() {
        parkingLot.setLotCapacity(10);
        parkingLotsSystem.addToLot(parkingLot);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setLotCapacity(15);
        parkingLotsSystem.addToLot(parkingLot1);
        try {
            parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.parking(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot1.parking(vehicle3, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot1.parking(vehicle4, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot1.parking(vehicle7, DriverType.NORMAL,VehicleType.SMALL);
            parkingLot.unParking(vehicle);
            parkingLotsSystem.parkVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            int totalParkedVehicles = parkingLot.autoParkingLocation;
            Assert.assertEquals(8, totalParkedVehicles);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }
}

