package carParkingLot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotInformer {
    static List<ParkingLotObserver> observersList;

    public ParkingLotInformer() {
        observersList = new ArrayList<>();
    }

    public void notifyParkingFull() {
        for (ParkingLotObserver observers : observersList)
            observers.setParkingCapacityFull();
    }

    public void notifyParkingAvailable() {
        for (ParkingLotObserver element : observersList)
            element.isParkingLotFull();
    }

    public void register(ParkingLotObserver observers) {
        observersList.add(observers);
    }
}

