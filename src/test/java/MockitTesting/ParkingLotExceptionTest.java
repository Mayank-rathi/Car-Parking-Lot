package MockitTesting;

import carParkingLot.Enum.DriverType;
import carParkingLot.Enum.VehicleType;
import carParkingLot.Exceptions.ParkingLotException;
import carParkingLot.Handler.ParkingLotOwner;
import carParkingLot.ParkingConcept.ParkingLot;
import carParkingLot.ParkingConcept.Vehicle;
import carParkingLot.ParkingLotsSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingLotExceptionTest {
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
        parkingLot = new ParkingLot(2);
        vehicle = new Vehicle("MH-27-BC-3361", "white", "Maruti", "ABC", "Mumbai");
    }

    @Test
    public void newObjectPassedToUnParkFunctionNotMatchesVehicleObject_VehicleNotFoundException_ThrowVehicleNotFoundException() {
        when(parkingLot.unPark(any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0) == vehicle) {
                        return "vehicle is unParked";
                    }
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
                });
        try {
            parkingLot.unPark(new Vehicle("MH-27-BC3386", "Red", "BMW", "XYZ", "Nagpur"));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    @Test
    public void anotherObjectPassedToParkFunction_ParkingLotFullException_ThrowAnException() {
        when(parkingLot.park(any(), any(),any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0) == vehicle) {
                        throw new ParkingLotException("", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
                    }
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
                });
        try {
            parkingLot.park(new Vehicle("MH-27-BC3386", "Red", "BMW", "XYZ", "Nagpur"), DriverType.NORMAL, VehicleType.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    @Test
    public void vehicleObjectPassedToParkFunction_VehicleAlReadyParkedException_ThrowAnException() {
        when(parkingLot.park(any(), any(),any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0).equals(vehicle)) {
                        throw new ParkingLotException("Vehicle is already parked", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
                    }
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
                });
        try {
            parkingLot.park(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

}
