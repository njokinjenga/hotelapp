package model;

public class Tester {
    public static void main(String[] args) {
        try {
            Customer customer= new Customer("first","second","j@domain.com");
            System.out.println(customer);

        }  catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        try {
            Customer customer= new Customer("first","second","email");
            System.out.println(customer);//won't get here

        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }


    }
}
