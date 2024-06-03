import java.util.Date;
public class Receipt {
    private java.util.Date entryTime;
    private java.util.Date exitTime;
    private int totalCost;
    private int durationOfStay;

    public Receipt(java.util.Date entryTime, java.util.Date exitTime,int durationOfStay, int totalCost) {
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.durationOfStay=durationOfStay;
        this.totalCost = totalCost;
    }

    public java.util.Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(java.util.Date entryTime) {
        this.entryTime = entryTime;
    }

    public java.util.Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(java.util.Date exitTime) {
        this.exitTime = exitTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
    public String toString(){
        return "Entry Time:"+entryTime+"\nExit Time:"+exitTime+"\nDuration of stay:"+durationOfStay+"hours\nhours Parking charge:"+totalCost+" Taka" ;
    }
}