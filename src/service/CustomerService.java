package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerService {
    private static CustomerService SINGLE_INSTANCE; //static reference
    private Collection<Customer> customers = new ArrayList<>();
    private CustomerService(){}
    public static  CustomerService getSingleInstance(){
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE= new CustomerService();
        }
        return SINGLE_INSTANCE;
    }
    public void addCustomer(String email, String firstName, String lastName){
        Customer newCustomer= new Customer(email,firstName,lastName);
        customers.add(newCustomer);

    }
    public Customer getCustomer(String customerEmail){
        return customers.stream()
                .filter(c -> c.getEmail().equals(customerEmail))
                .findFirst().orElse(null);

    }
    public Collection<Customer> getAllCustomers(){
        return customers;
    }
}
