package mockitoTesting;

import carParkingLot.AirPortSecurityStaff;
import carParkingLot.DriverType;
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

public class AirportSecurityStaffTest {
    @Mock
    ParkingLots parkingLots;
    AirPortSecurityStaff airPortSecurityStaff;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLots = mock(ParkingLots.class);
        airPortSecurityStaff = new AirPortSecurityStaff();
        vehicle = new Object();
    }

    @Test
    public void check_isCapacityFull_ReturnTrue() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            airPortSecurityStaff.setParkingCapacityFull();
            return null;
        }).when(parkingLots).parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLots.parkTheVehicle(vehicle,DriverType.NORMAL);
        Assert.assertTrue(airPortSecurityStaff.isParkingLotFull());
    }
}
