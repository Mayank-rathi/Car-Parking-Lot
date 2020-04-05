package MockitTesting;

import carParkingLot.Enum.DriverType;
import carParkingLot.Exceptions.ParkingLotException;
import carParkingLot.Handler.ParkingLotOwner;
import carParkingLot.ParkingConcept.ParkingLot;
import carParkingLot.ParkingLotsSystem;
import carParkingLot.ParkingConcept.Vehicle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class ParkingLotTest {
    @Mock
    ParkingLot parkingLot;
    ParkingLotsSystem parkingLotsSystem;
    ParkingLotOwner parkingLotOwner;
    Vehicle vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLotsSystem = mock(ParkingLotsSystem.class);
        parkingLotOwner = new ParkingLotOwner();
        parkingLot=new ParkingLot(2);
        vehicle = new Vehicle("MH-27-BC-3361","white","Maruti","ABC","Mumbai");
    }

    @Test
    public void called_ParkFunction_ShouldParkTheVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLot.parking(vehicle, DriverType.NORMAL);
            return null;
        }).when(parkingLotsSystem).parkVehicle(vehicle, DriverType.NORMAL);
        boolean isParked = parkingLot.parking(vehicle, DriverType.NORMAL);
        assertTrue(isParked);
    }

    @Test
    public void called_FindVehicleFunction_ShouldParkTheVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLot.findVehicle(vehicle);
            return null;
        }).when(parkingLotsSystem).findVehicle(vehicle);
        try {
            parkingLot.findVehicle(vehicle);
        } catch (ParkingLotException e) {
            assertSame(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }
}
