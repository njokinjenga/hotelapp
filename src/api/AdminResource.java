package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static AdminResource SINGLE_INSTANCE;
    private static final CustomerService cs = CustomerService.getSingleInstance();
    private static final ReservationService rs = ReservationService.getSingleInstance();
    private AdminResource(){}
    public static AdminResource getSingleInstance(){
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE= new AdminResource();
        }
        return SINGLE_INSTANCE;
    }
    public Customer getCustomer(String email){
        return cs.getCustomer(email);
    }
    public void addRooms(List<IRoom> roomslist){
        roomslist.stream().forEach(r->rs.addRoom(r));
        System.out.println("Room added successfully!");
    }
    public void addRoom(IRoom room ){
        rs.addRoom(room);
        System.out.println("Room added successfully!");
    }
    public Collection<IRoom> getAllRooms(){
        return rs.getAllRooms();
    }
    public Collection<Customer> getAllCustomers(){
        return cs.getAllCustomers();

    }
    public void displayAllReservations(){
        rs.printAllReservation();
    }
}

