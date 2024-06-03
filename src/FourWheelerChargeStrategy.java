public class FourWheelerChargeStrategy implements ParkingChargeStrategy{
    @Override
    public int getCharge(int parkHours) {
        if(parkHours<=2){
            return 40;
        }
        return 40+(parkHours-2)*15;
    }
}

