package mockitoTesting;

import carParkingLot.AirPortSecurityStaff;
import carParkingLot.DriverType;
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

public class AirportSecurityStaffTest {
    @Mock
    ParkingLotSystem parkingLotSystem;
    AirPortSecurityStaff airPortSecurityStaff;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLotSystem = mock(ParkingLotSystem.class);
        airPortSecurityStaff = new AirPortSecurityStaff();
        vehicle = new Object();
    }

    @Test
    public void check_isCapacityFull_ReturnTrue() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            airPortSecurityStaff.setCapaCity();
            return null;
        }).when(parkingLotSystem).parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLotSystem.parkTheVehicle(vehicle,DriverType.NORMAL);
        Assert.assertTrue(airPortSecurityStaff.isCapacityFull());
    }
}
