import carParkingLot.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ParkingLotTest {
    private static ParkingLotSystem parkingLotSystem;
    public static Object vehicle, vehicle2, vehicle3;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(2);
        vehicle = new Object();
        vehicle2 = new Object();
        vehicle3 = new Object();
    }

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

    //Parking-Lot Owner
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

    //AirPort Security-Staff
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

}

