import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingLot implements Parking{
    private static ParkingLot parkinglot;
    private final List<ParkingSlot>twoWheelerSlots;
    private final List<ParkingSlot>fourWheelerSlots;

    public int twoWheelersParkedCounter=0;
    public int fourWheelersParkedCounter=0;
    public int twCost=0;
    public int fwCost=0;


    private ParkingLot(){
        this.twoWheelerSlots=new ArrayList<>();
        this.fourWheelerSlots=new ArrayList<>();
    }

    public static ParkingLot getParkingLot(){
        if(parkinglot==null){
            parkinglot=new ParkingLot();
        }
        return parkinglot;
    }
    public static void clearAll(){
        parkinglot=null;
    }
    public void initializeParkingSlots(int numberOfTwoWheelersParkingSlots, int numberOfFourWheelerParkingSlots){
        for(int i=1 ; i<=numberOfTwoWheelersParkingSlots ; i++){
            twoWheelerSlots.add(new ParkingSlot(i));
        }
        for(int i=numberOfTwoWheelersParkingSlots+1 ; i<=numberOfTwoWheelersParkingSlots+numberOfFourWheelerParkingSlots ; i++){
            fourWheelerSlots.add(new ParkingSlot(i));
        }
    }

    private ParkingSlot findNextAvailableFourWheelerSlot() throws ParkingFullException{
        for(ParkingSlot slot: fourWheelerSlots){
            if(slot.isEmpty()){
                fourWheelersParkedCounter++;
                return slot;
            }
        }
        throw new ParkingFullException("No empty slot available!");
    }
    private ParkingSlot findNextAvailableTwoWheelerSlot() throws ParkingFullException{
        for(ParkingSlot slot: twoWheelerSlots){
            if(slot.isEmpty()){
                twoWheelersParkedCounter++;
                return slot;
            }
        }
        throw new ParkingFullException("No empty slot available!");
    }

    @Override
    public Ticket park(Vehicle vehicle) throws ParkingFullException {
        ParkingSlot nextAvailableSlot;
        System.out.println(vehicle.getVehicleSize().equals(VehicleSize.FOURWHEELER));
        if (vehicle.getVehicleSize().equals(VehicleSize.FOURWHEELER)) {
            nextAvailableSlot = findNextAvailableFourWheelerSlot();
        } else {
            nextAvailableSlot = findNextAvailableTwoWheelerSlot();

        }
        nextAvailableSlot.occupySlot(vehicle);
        System.out.println("Your vehicle has been allocated to slot number: " + nextAvailableSlot.getSlotNumber());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();

        Ticket ticket=new Ticket(nextAvailableSlot.getSlotNumber(), vehicle.getNumberPlate(),vehicle.getColor(), dtf.format(now), vehicle.getVehicleSize());
        return ticket;
    }

    private ParkingSlot getFourWheelerSlotByVehicleNumber(String vehicleNumber) throws InvalidVehicleNumberException{
        for(ParkingSlot slot: fourWheelerSlots){
            Vehicle vehicle=slot.getParkVehicle();
            if(vehicle!=null && vehicle.getNumberPlate().equals(vehicleNumber)){
                fourWheelersParkedCounter--;
                return slot;
            }
        }
        throw new InvalidVehicleNumberException("Vehicle number does not match!");
    }

    private ParkingSlot getTwoWheelerSlotByVehicleNumber(String vehicleNumber) throws InvalidVehicleNumberException{
        for(ParkingSlot slot: twoWheelerSlots){
            Vehicle vehicle=slot.getParkVehicle();
            if(vehicle!=null && vehicle.getNumberPlate().equals(vehicleNumber)){
                twoWheelersParkedCounter--;
                return slot;
            }
        }
        throw new InvalidVehicleNumberException("Vehicle number does not match!");
    }

    @Override
    public Receipt unPark(String plateNumber,VehicleSize vehicleSize) throws InvalidVehicleNumberException, ParseException, FileNotFoundException {
        int costByHours=0;
        ParkingSlot slot;
        ParkingChargeStrategy parkingChargeStrategy;


        if(vehicleSize.equals(VehicleSize.FOURWHEELER)){
            slot=getFourWheelerSlotByVehicleNumber(plateNumber);
            parkingChargeStrategy=new FourWheelerChargeStrategy();

        }
        else{
            slot=getTwoWheelerSlotByVehicleNumber(plateNumber);
            parkingChargeStrategy=new TwoWheelerChargeStrategy();
        }
        slot.vacateSlot();
        System.out.println("Slot"+slot.getSlotNumber());

        List<Ticket> ticket_list = Main.loadTickets("InVehiclesList.txt");
        Ticket foundTicket=null;
        for(Ticket tick:ticket_list){
            System.out.println(tick);
        }
        for(Ticket t:ticket_list){
            if(t.getSlotNumber()==slot.getSlotNumber() && t.getVehicleNumber().equals(plateNumber) && t.getVehicleSize().equals(vehicleSize)){
                foundTicket=t;
                break;
            }
        }
        System.out.println(foundTicket);

        SimpleDateFormat formatter6=new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
        Date date=formatter6.parse(foundTicket.getDate());

        int hours=getHoursParked(date,new Date());
        costByHours=parkingChargeStrategy.getCharge(hours);

        if(vehicleSize.equals(VehicleSize.TWOWHEELER)){
            twCost+=costByHours;
        }
        else{
            fwCost+=costByHours;
        }

        System.out.println("twcost:"+twCost+"fwcost:"+fwCost);



        PrintWriter writer = new PrintWriter("InVehiclesList.txt");
        writer.print("");
// other operations
        writer.close();

        for(Ticket tic:ticket_list){
            if(tic!=foundTicket){
                Main.saveTicket(tic,"InVehiclesList.txt");
            }
        }

        Main.saveTicket(foundTicket,"OutVehiclesList.txt");




        return new Receipt(date,new Date(),getHoursParked(date,new Date()),costByHours);



    }



    private int getHoursParked(Date startDate, Date endDate){
        long secs=(endDate.getTime()- startDate.getTime())/1000;
        int hours=(int)(secs/3600);
        return hours;
    }
}
