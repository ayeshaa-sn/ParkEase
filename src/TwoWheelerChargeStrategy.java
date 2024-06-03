public class TwoWheelerChargeStrategy implements ParkingChargeStrategy{
    @Override
    public int getCharge(int parkHours){
        if(parkHours<2){
            return 20;
        }
        return 20+(parkHours-2)*10;
    }
}