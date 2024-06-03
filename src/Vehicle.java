public class Vehicle {
    private String numberPlate;
    private String color;
    private VehicleSize vehicleSize;
    private String ownerContantNo;

    public String getOwnerContantNo() {
        return ownerContantNo;
    }

    public void setOwnerContantNo(String ownerContantNo) {
        this.ownerContantNo = ownerContantNo;
    }



    public Vehicle(String numberPlate, String color,VehicleSize vehicleSize, String ownerContantNo) {
        this.numberPlate = numberPlate;
        this.color = color;
        this.vehicleSize=vehicleSize;
        this.ownerContantNo=ownerContantNo;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(VehicleSize vehicleSize) {
        this.vehicleSize = vehicleSize;
    }
}
