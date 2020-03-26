package mockitoTesting;

import carParkingLot.DriverType;
import carParkingLot.ParkingLotOwner;
import carParkingLot.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class ParkingLotOwnerTest {
    @Mock
    ParkingLotSystem parkingLotSystem;
    ParkingLotOwner parkingLotOwner;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLotSystem = mock(ParkingLotSystem.class);
        parkingLotOwner = new ParkingLotOwner();
        vehicle = new Object();
    }

    @Test
    public void check_isCapacityFullFunction() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotOwner.setCapaCity();
            return null;
        }).when(parkingLotSystem).parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(vehicle, DriverType.NORMAL);
        Assert.assertTrue(parkingLotOwner.isCapacityFull());
    }
}
