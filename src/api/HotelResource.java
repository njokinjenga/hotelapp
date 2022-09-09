package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource SINGLE_INSTANCE;
    private static final CustomerService cs= CustomerService.getSingleInstance();
    private static final ReservationService rs= ReservationService.getSingleInstance();
    private HotelResource(){
    }
    public static HotelResource getSingleInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new HotelResource();
        }
        return SINGLE_INSTANCE;
    }
    public Customer getCustomer(String email){
        return cs.getCustomer(email);
    }
    public void createACustomer(String email, String firstName, String lastName){
        cs.addCustomer(email, firstName, lastName);
        System.out.println("Customer successfully added.");
    }
    public IRoom getRoom(String roomNumber){
        return rs.getARoom(roomNumber);
    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        if (cs.getCustomer(customerEmail) ==null)  {
            System.out.println("Please use the email used in the creation of your account. Create account if you have none");
            return null;

        } else if(!room.isVacant()){
            System.out.println("Room is occupied. Pick a different room number");
            return null;
        }
        else{
            return rs.reserveRoom(cs.getCustomer(customerEmail),room,checkInDate,checkOutDate);
        }

    }
    public Collection<Reservation> getCustomersReservation(String customerEmail){
        return rs.getCustomersReservation(cs.getCustomer(customerEmail));
    }
    public Collection<IRoom> findARoom(boolean include,Date checkInDate, Date checkOutDate){
        return rs.findRooms(include, checkInDate, checkOutDate);
    }
    public Collection<IRoom> recomendRooms(boolean include,Date checkInDate,int days){
        return rs.recommendRooms(include, checkInDate, days);
    }

}

