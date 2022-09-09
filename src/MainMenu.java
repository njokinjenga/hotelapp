import api.HotelResource;
import model.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;


public class MainMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static final HotelResource hr = HotelResource.getSingleInstance();
    private static Collection<IRoom> availableRooms= new ArrayList<>();
    public static boolean proceed =true;

    public static void main(String[] args) {
        welcome();
    }

    protected static void welcome() {
        while (proceed) {
            try {
                System.out.println("Welcome Customer! Please choose a number to continue: \n 1.Find and reserve room" +
                        "\n 2.See my reservations \n 3.Create account \n 4.Admin \n 5.Exit");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        findOrReserveRoom();
                        break;
                    case 2:
                        myReservations();
                        break;
                    case 3:
                        createAccount();
                        break;
                    case 4:
                        goToAdmin();
                        break;
                    case 5:
                        Exit();
                        break;
                    default:
                        System.out.println("Wrong Input, Try Again!");
                        welcome();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //search and book
    public static void findOrReserveRoom(){
        try {
            scanner.nextLine(); //consume extra line added by nextInt
            String option; //String option=null would be redundant initialization;
            boolean include = false;
            try {
                System.out.println("Input YES for free room and No for Paid room");
                option = scanner.nextLine().toUpperCase();
                while (!option.equalsIgnoreCase("YES")  && !option.equalsIgnoreCase("NO") ) {
                    System.out.println("Invalid entry.Type YES or NO:");
                    option  = scanner.nextLine().toUpperCase();
                }
                if(option.equals("YES")){
                    include=true;
                }

            } catch (Exception e) {
                System.out.println("Entry is invalid");
            }

            Date  checkInDate=null; SimpleDateFormat formatter; String inDateString;
            try {
                System.out.println("Enter your check in date use the format: dd/MM/yyyy");
                inDateString= scanner.nextLine();
                formatter= new SimpleDateFormat("dd/MM/yyyy");
                checkInDate = formatter.parse(inDateString);
                while(!inDateString.equals(formatter.format(checkInDate))){
                    System.out.println("Invalid entry. Re-enter Check in date use the format: dd/MM/yyyy");
                    inDateString= scanner.nextLine();
                    formatter= new SimpleDateFormat("dd/MM/yyyy");
                    checkInDate = formatter.parse(inDateString);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date  checkOutDate=null; SimpleDateFormat Outformatter; String OutDateString;
            try {
                System.out.println("Enter your check out date use the format: dd/MM/yyyy");
                OutDateString= scanner.nextLine();
                Outformatter= new SimpleDateFormat("dd/MM/yyyy");
                checkOutDate = Outformatter.parse(OutDateString);
                while(!OutDateString.equals(Outformatter.format(checkOutDate))){
                    System.out.println("Wrong format. Re-enter Check out date use the format: dd/MM/yyyy");
                    OutDateString= scanner.nextLine();
                    Outformatter= new SimpleDateFormat("dd/MM/yyyy");
                    checkOutDate = Outformatter.parse(OutDateString);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            availableRooms= hr.findARoom(include,checkInDate,checkOutDate);

            if(!availableRooms.isEmpty()) {
                System.out.println("The available rooms are :" + availableRooms);

                String enteredRoom = null;
                try {
                    System.out.println("enter your preferred Room Number to book:");
                    enteredRoom = scanner.nextLine();
                    while (enteredRoom == null || enteredRoom.isEmpty()) {
                        System.out.println("Incorrect room no. Re-enter enter room Number to book");
                        enteredRoom = scanner.nextLine();
                    }
                } catch (Exception e) {
                    System.out.println("Room is invalid");
                }
                final IRoom availableRoom = hr.getRoom(enteredRoom);
                String email = getEmail();
                hr.bookARoom(email, availableRoom, checkInDate, checkOutDate);
            }
            else {
                System.out.println("There are no more rooms");
                int days = 0;
                try {
                    System.out.println("Lets see available rooms in a few days. Enter number of days to check:");
                    days = scanner.nextInt();
                    while (days ==0 ) {
                        System.out.println("Invalid Input.Please reenter:");
                        days = scanner.nextInt();
                        scanner.nextLine();
                    }

                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
                final Collection<IRoom> nextAvailable= hr.recomendRooms(include,checkInDate,days);
                if (!nextAvailable.isEmpty()) {
                    System.out.println("Rooms that will be available in the next "+days+" days:"+nextAvailable);
                } else {
                    System.out.println("Sorry, no rooms available in the next "+days+" days.");
                    System.out.println("Going back.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void myReservations(){
        scanner.nextLine(); //Consume extra line
        String email= getEmail();
        if (!hr.getCustomersReservation(email).isEmpty()) {
            System.out.println(hr.getCustomersReservation(email));
        }else {
            System.out.println("You don't have a reservation.");
        }

    }
    static void createAccount(){
        String firstName = null;
        try {
            scanner.nextLine();
            System.out.println("Enter your first name:");
            firstName = scanner.nextLine();
            while (firstName == null || firstName.isEmpty() ) {
                System.out.println("Invalid Input. Please Try again:");
                firstName = scanner.nextLine();
            }

        } catch (Exception e) {
            System.out.println("First name is invalid");
        }
        String lastName = null;
        try {
            System.out.println("Enter your last name:");
            lastName = scanner.nextLine();
            while (lastName == null || lastName.isEmpty() ) {
                System.out.println("Invalid Input. Please Try again:");
                lastName = scanner.nextLine();
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        String email = getEmail();
        hr.createACustomer(firstName,lastName,email);
        System.out.println("Going back to Customer menu.");
    }
    //Go to admin menu
    static void goToAdmin(){
        try {
            System.out.println("Going to Admin menu.");
            AdminMenu.welcome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void Exit(){
        proceed = false;
        System.out.println("Thanks you,see you soon.");
    }
    private static String getEmail(){
        String email = null;
        try {

            System.out.println("Enter your email address:");
            email = scanner.nextLine();

            String emailRegex = "^(.+)@(.+).com$";
            Pattern pattern = Pattern.compile(emailRegex);
            while (!pattern.matcher(email).matches()) {
                System.out.println("Invalid Input. Please Try again:");
                email = scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Invalid email");
        }
        return email;
    }

}


