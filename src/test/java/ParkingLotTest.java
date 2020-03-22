import carParkingLot.ParkingLotAnalyser;
import carParkingLot.ParkingLotOwner;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {
    @Test
    public void giveWelcomeMessage_ForParking_Lot() throws Exception {
        ParkingLotAnalyser parkingLotAnalyser = new ParkingLotAnalyser();
        String welcomeCheck = parkingLotAnalyser.getWelcomeMessage();
        Assert.assertEquals("Welcome", welcomeCheck);
    }

    @Test
    public void givenParkingLotAnalyser_ForDriverParking_ShouldReturnTrue() {
        ParkingLotAnalyser parkingLotAnalyser = new ParkingLotAnalyser();
        boolean CheckparkingCar = parkingLotAnalyser.parkingCar(new Object());
        Assert.assertTrue("true", CheckparkingCar);
    }

    @Test
    public void givenParkingLotAnalyser_ForUnParkingVehical_ShouldReturnTrue() {
        ParkingLotAnalyser parkingLotAnalyser = new ParkingLotAnalyser();
        boolean unParkingCar = parkingLotAnalyser.UnParkingCar(new Object());
        Assert.assertTrue("true", unParkingCar);
    }

    //Check For Owner
    @Test
    public void givenParkingLot_CheckForOwner_IfFull_ShouldReturnTrue() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        boolean checkForLot = parkingLotOwner.checkForLot(new Object());
        Assert.assertEquals(true, checkForLot);
    }
    @Test
    public void givenParkingLot_CheckForOwner_IfNotFull_ShouldReturnTrue() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        boolean checkForLot = parkingLotOwner.checkForEmpty(new Object());
        Assert.assertEquals(true, checkForLot);
    }
}

