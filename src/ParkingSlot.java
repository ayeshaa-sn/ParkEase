public class ParkingSlot {
    private Integer number;
    private boolean isEmpty;
    private Vehicle parkVehicle;
    public int parkedVehicleCounter=0;

    public ParkingSlot(Integer number){
        this.number=number;
        this.isEmpty=true;
    }

    public Vehicle getParkVehicle() {
        return parkVehicle;
    }

    public void setParkVehicle(Vehicle parkVehicle) {
        this.parkVehicle = parkVehicle;
    }

    public void vacateSlot(){
        this.parkVehicle=null;
        this.isEmpty= true;
        parkedVehicleCounter--;
    }

    public void occupySlot(Vehicle parkVehicle){
        this.parkVehicle=parkVehicle;
        this.isEmpty=false;
        parkedVehicleCounter++;
    }

    public Integer getSlotNumber(){
        return number;
    }

    public void setNumber(Integer number){
        this.number=number;
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

}
