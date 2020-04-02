package MockitTesting;

import carParkingLot.Enum.DriverType;
import carParkingLot.Handler.ParkingLotOwner;
import carParkingLot.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class ParkingLotOwnerTest {
    @Mock
    ParkingLot parkingLot;
    ParkingLotOwner parkingLotOwner;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLot = mock(ParkingLot.class);
        parkingLotOwner = new ParkingLotOwner();
        vehicle = new Object();
    }

    @Test
    public void check_isCapacityFullFunction() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotOwner.setParkingCapacityFull();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        Assert.assertTrue(parkingLotOwner.isFullCapacity());
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndCheckSpaceAvailable_ShouldReturnFalse() {
        doAnswer(invocationOnMock -> {
            parkingLotOwner.isLotSpaceAvailable();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle, DriverType.NORMAL);
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        Assert.assertFalse(parkingLotOwner.isSpaceAvailable());
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        when(parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL)).thenReturn(true);
        boolean isParked = parkingLot.parkTheVehicle(this.vehicle, DriverType.NORMAL);
        Assert.assertTrue(isParked);
    }
}
