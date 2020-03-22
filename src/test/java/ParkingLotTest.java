import carParkingLot.ParkingLotAnalyser;
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
}
