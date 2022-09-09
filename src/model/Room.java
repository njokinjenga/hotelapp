package model;

import java.util.Objects;

public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;
    private boolean isVacant=true;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return true;
    }
    @Override //override and implement this too
    public boolean isVacant() {
        return isVacant;
    }

    @Override //override and implement this too
    public void setIsVacant(boolean isVacant){
        this.isVacant=isVacant;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return getRoomNumber().equals(room.getRoomNumber()) && price.equals(room.price) && enumeration == room.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomNumber(), price, enumeration);
    }

    @Override
    public String toString() {
        return  "Room Number:"+roomNumber+" Price:"+price+" Room Type:"+enumeration;
    }
}
