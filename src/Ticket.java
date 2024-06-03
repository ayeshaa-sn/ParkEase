import java.io.Serializable;
//import java.util.String;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    private int slotNumber;
    private String vehicleNumber;
    private String date;
    private VehicleSize vehicleSize;
    private String vehicleColor;

    public Ticket(int slotNumber, String vehicleNumber, String vehicleColor, String date, VehicleSize vehicleSize) {
        super();
        this.slotNumber = slotNumber;
        this.vehicleNumber = vehicleNumber;
        this.vehicleColor=vehicleColor;
        this.date = date;
        this.vehicleSize=vehicleSize;

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(VehicleSize vehicleSize) {
        this.vehicleSize = vehicleSize;
    }

    @Override
    public String toString() {
        return "SLOT NUMBER:" + slotNumber + "\nVEHICLE NUMBER:" + vehicleNumber + "\nVEHICLE COLOR:"+vehicleColor+"\nENTRY TIME:" + date+
                "\nVEHICLE SIZE:"+vehicleSize;
    }

}
