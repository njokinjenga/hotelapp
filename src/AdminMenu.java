import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Scanner;

public class AdminMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static final AdminResource ar = AdminResource.getSingleInstance();

    public static void main(String[] args) {
        welcome();
    }

    protected static void welcome(){
        while(MainMenu.proceed){
            try {
                System.out.println("Welcome Admin! Choose a number to continue: \n 1.See all customers" +
                        "\n 2.See all rooms \n 3.See all reservations \n 4.Add room \n 5.Back to Main Menu");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> getCustomers();
                    case 2 -> getRooms();
                    case 3 -> getReservations();
                    case 4 -> addRoom();
                    case 5 -> goToMainMenu();
                    default -> {
                        System.out.println("Wrong choice. Back To Menu.");
                        welcome();
                    }
                }

            } catch (Exception e) {
                System.out.println("We experienced an Error.Exiting! ");
                e.printStackTrace();
            }
        }
    }

    protected static void getCustomers(){
        if ( !ar.getAllCustomers().isEmpty()) {
            System.out.println(ar.getAllCustomers());
        } else {
            System.out.println("You don't have a customer.");
        }
    }
    protected static void getRooms(){
        if (ar.getAllRooms() !=null && !ar.getAllRooms().isEmpty()) {
            System.out.println(ar.getAllRooms());
        } else {
            System.out.println("There are no added rooms.");
        }
    }
    protected static void getReservations(){
        ar.displayAllReservations();
    }
    private static void addRoom() {
        try {
            String enteredRoom = null; //to be able to access it outside try catch
            try {
                scanner.nextLine(); //consume extra line added by nextInt
                System.out.println("Enter room number:");
                enteredRoom = scanner.nextLine();
                while (enteredRoom == null || enteredRoom.isEmpty() ) {
                    System.out.println("Invalid entry.Please try again:");
                    enteredRoom = scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println(enteredRoom+" invalid room number");
            }
            Double enteredPrice = null;
            try {
                System.out.println("Enter room price:0 if room is free");
                enteredPrice = scanner.nextDouble();
                scanner.nextLine();

            } catch (Exception e) {
                System.out.println("Price is invalid");
            }
            RoomType enteredType = null;
            try {
                System.out.println("Enter room type:SINGLE or DOUBLE");

                enteredType = RoomType.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Input");
            }

            IRoom room = new Room(enteredRoom,enteredPrice,enteredType);
            String finalEnteredRoom = enteredRoom;

            if (enteredRoom!=null) {
                if(ar.getAllRooms().stream().noneMatch(r->r.getRoomNumber().contains(finalEnteredRoom)))
                {
                    ar.addRoom(room);
                }
                else{
                    System.out.println("Add new room Number");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void goToMainMenu(){
        try {
            System.out.println("Customer menu");
            MainMenu.welcome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

