package MockitTesting;

import carParkingLot.Enum.DriverType;
import carParkingLot.Handler.AirPortSecurityStaff;
import carParkingLot.ParkingLot;
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
    ParkingLot parkingLot;
    AirPortSecurityStaff airPortSecurityStaff;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLot = mock(ParkingLot.class);
        airPortSecurityStaff = new AirPortSecurityStaff();
        vehicle = new Object();
    }

    @Test
    public void check_isCapacityFull_ReturnTrue() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            airPortSecurityStaff.setParkingCapacityFull();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        Assert.assertTrue(airPortSecurityStaff.isFullCapacity());
    }

    @Test
    public void givenParkingLot_TestCapacityAvailable_And_IsSpaceAvailable() {
        doAnswer(invocationOnMock -> {
            airPortSecurityStaff.isLotSpaceAvailable();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        Assert.assertTrue(airPortSecurityStaff.isSpaceAvailable());
    }
}
