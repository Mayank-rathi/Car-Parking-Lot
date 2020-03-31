package mockitoTesting;

import carParkingLot.DriverType;
import carParkingLot.ParkingLotOwner;
import carParkingLot.ParkingLots;
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
    ParkingLots parkingLots;
    ParkingLotOwner parkingLotOwner;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLots = mock(ParkingLots.class);
        parkingLotOwner = new ParkingLotOwner();
        vehicle = new Object();
    }

    @Test
    public void check_isCapacityFullFunction() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotOwner.setParkingCapacityFull();
            return null;
        }).when(parkingLots).parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLots.parkTheVehicle(vehicle, DriverType.NORMAL);
        Assert.assertTrue(parkingLotOwner.isParkingLotFull());
    }
}
