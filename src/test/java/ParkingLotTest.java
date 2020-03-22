import carParkingLot.ParkingLotAnalyser;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {
    @Test
    public void giveWelcomeMessage_ForParking_Lot() throws Exception {
        ParkingLotAnalyser welcomeMessage = new ParkingLotAnalyser();
        String welcomeCheck = welcomeMessage.getWelcomeMessage();
        Assert.assertEquals(welcomeCheck, "Welcome");
    }
}
