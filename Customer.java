package codeAlpha;

public class Customer {
    private String name;
    private int roomNo;
    private String checkInTime;
    private String checkOutTime;
    private double amountPaid;

    //constructor
    public Customer(String name, int roomNo, String checkInTime, String checkOutTime, double amountPaid) {
        this.name = name;
        this.roomNo = roomNo;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.amountPaid = amountPaid;
    }
    //getters
    public String getName() { return name; }
    public int getRoomNo() { return roomNo; }
    public String getCheckInTime() { return checkInTime; }
    public String getCheckOutTime() { return checkOutTime; }
    public double getAmountPaid() { return amountPaid; }

    @Override
    public String toString() {
        return "Customer name=" + name + "\nRoomNo=" + roomNo + "\nCheckin Time=" + checkInTime +
               "\ncheckout Time=" + checkOutTime + "\nAmount Paid=" + amountPaid + "\n------------------------------";
    }
}