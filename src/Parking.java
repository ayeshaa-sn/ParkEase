import java.io.FileNotFoundException;
import java.text.ParseException;

public interface Parking {
    public Ticket park(Vehicle vehicle)throws ParkingFullException;
    public Receipt unPark(String plateNumber,VehicleSize vehicleSize) throws
            InvalidVehicleNumberException,
            ParseException, FileNotFoundException;
}



