package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, 0.00, enumeration); //set price of superclass to 0 here
    }

    @Override
    public String toString() {
        return "FreeRoom{" +
                "roomNumber='" + getRoomNumber() + '\'' +
                ", price=" + getRoomPrice() +
                ", enumeration=" + getRoomType() +
                '}';
    }
}
