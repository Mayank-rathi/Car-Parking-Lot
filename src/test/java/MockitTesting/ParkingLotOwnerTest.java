package MockitTesting;

import carParkingLot.Enum.DriverType;
import carParkingLot.Enum.VehicleType;
import carParkingLot.Handler.ParkingLotOwner;
import carParkingLot.ParkingConcept.ParkingLot;
import carParkingLot.ParkingConcept.Vehicle;
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
    Vehicle vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLot = mock(ParkingLot.class);
        parkingLotOwner = new ParkingLotOwner();
        vehicle = new Vehicle("MH-27-BC-3361","white","Maruti","ABC","Mumbai");
    }

    @Test
    public void check_isCapacityFullFunction() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotOwner.setParkingCapacityFull();
            return null;
        }).when(parkingLot).parking(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        Assert.assertTrue(parkingLotOwner.isFullCapacity());
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndCheckSpaceAvailable_ShouldReturnFalse() {
        doAnswer(invocationOnMock -> {
            parkingLotOwner.isLotSpaceAvailable();
            return null;
        }).when(parkingLot).parking(vehicle, DriverType.NORMAL, VehicleType.SMALL);
        parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        Assert.assertFalse(parkingLotOwner.isSpaceAvailable());
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        when(parkingLot.parking(vehicle, DriverType.NORMAL,VehicleType.SMALL)).thenReturn(true);
        boolean isParked = parkingLot.parking(this.vehicle, DriverType.NORMAL,VehicleType.SMALL);
        Assert.assertTrue(isParked);
    }
}
