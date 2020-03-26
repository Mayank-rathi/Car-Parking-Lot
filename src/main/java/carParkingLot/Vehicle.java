package carParkingLot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Vehicle {
    public static boolean timeCheck(Object vehicle) {
        if (vehicle != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String time = formatter.format(date);
            return true;
        }
        throw new ParkingLotException("No Such Vehicle Found", ParkingLotException.ExceptionType.NO_VEHICLE_FOUND);
    }
}