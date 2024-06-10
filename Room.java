package codeAlpha;

public class Room {
    private int roomNo;
    private String category;
    private double cost;
    private boolean isAvailable;

    //constructors
    public Room(int roomNo, String category, double cost, boolean isAvailable) {
        this.roomNo = roomNo;
        this.category = category;
        this.cost = cost;
        this.isAvailable = isAvailable;
    }

    //getters
    public int getRoomNo() { return roomNo; }
    public String getCategory() { return category; }
    public double getCost() { return cost; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

    @Override
    public String toString() {
        return "roomNo=" + roomNo + "  category=" + category + "  cost=" + cost + (isAvailable==true ? "\nAvailable" : "\nNot Available")+"\n__________________________________";
    }
}