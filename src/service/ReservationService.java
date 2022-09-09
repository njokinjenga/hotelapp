package service;

import model.Customer;
import model.IRoom;
import model.Reservation;


import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    private static ReservationService SINGLE_INSTANCE;
    private Collection<IRoom> rooms = new ArrayList<>();
    Collection<Reservation> reservations = new ArrayList<>();

    private ReservationService(){}

    public static  ReservationService getSingleInstance(){
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE= new ReservationService();
        }
        return SINGLE_INSTANCE;
    }
    public void addRoom(IRoom room){
        rooms.add(room);
    }

    public IRoom getARoom(String roomId){
        return rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomId))
                .filter(r -> r.isVacant())
                .findFirst()
                .orElse(null);

    }
    public Collection<IRoom> getAllRooms(){
        return rooms;
    }
    public Reservation reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){

        Reservation newReservation= new Reservation(customer,room,checkInDate,checkOutDate);

        if (room.isVacant()) {
            reservations.add(newReservation);
            System.out.println("Reservation successful!");
        } else {
            System.out.println("Room is occupied");
        }
        room.setIsVacant(false);
        return newReservation;
    }
    public Collection<IRoom> findRooms(boolean include, Date checkInDate, Date checkOutDate){
        Collection<IRoom> reservedRooms = reservations.stream()
                .filter(r->r.getCheckInDate().equals(checkInDate))
                .filter(r->r.getCheckOutDate().equals(checkOutDate))
                .map(Reservation::getRoom)
                .collect(Collectors.toList());
        Collection<IRoom> unreservedRooms;
        if (include) {
            unreservedRooms = rooms.stream()
                    .filter(r->!reservedRooms.contains(r)).filter(r->r.getRoomPrice()<=0.00)
                    .filter(r->r.isVacant())
                    .collect(Collectors.toList());
        } else {
            unreservedRooms = rooms.stream()
                    .filter(r->!reservedRooms.contains(r)).filter(r->r.getRoomPrice()>0.00)
                    .filter(r->r.isVacant())
                    .collect(Collectors.toList());
        }
        return unreservedRooms;
    }

    public Collection<IRoom> recommendRooms(boolean include, Date checkInDate, int days){

        Calendar date= Calendar.getInstance();
        date.setTime(checkInDate);
        date.add(Calendar.DATE, days);
        Date inPlusDays = date.getTime();
        Collection<IRoom> recommendReservedRooms = reservations.stream()
                .filter(r-> r.getCheckOutDate().before(inPlusDays))
                .map(Reservation::getRoom)
                .collect(Collectors.toList());

        Collection<IRoom> roomsTobeAvailable = new ArrayList<>();
        if (include) {

            for (IRoom room:recommendReservedRooms){
                if (room.getRoomPrice()<=0.0) {
                    roomsTobeAvailable.add(room);
                }
            }
        }
        else {

            for (IRoom room : recommendReservedRooms) {
                if (room.getRoomPrice() > 0.0) {
                    roomsTobeAvailable.add(room);
                }

            }
        }

        return roomsTobeAvailable;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        return reservations.stream().
                filter(r->r.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }


    public void printAllReservation(){
        if ( !reservations.isEmpty()) {

            for (Reservation reserve:reservations) {
                System.out.println(reserve);
            }
        } else {
            System.out.println("You have zero reservations");
        }
    }

    void printSomething(){
        System.out.println("something.");
    }

}
