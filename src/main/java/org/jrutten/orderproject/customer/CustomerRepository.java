package org.jrutten.orderproject.customer;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.logging.Logger;

@Repository
public class CustomerRepository {
    private final Map<String, Customer> customerMap;
    private final Logger logger;

    public CustomerRepository() {
        this.customerMap = new HashMap<>();
        this.logger = Logger.getLogger(this.getClass().getName());

        initMap();
    }

    private void initMap() {
        Address address = new Address("Street", 1, "b", 1000, "Blank");
        Customer customer = new Customer("JD", "John", "Doe", "j.doe@mail.com", address, "555-4444-4321" );
        this.customerMap.put(customer.getId(), customer);
    }

    public void registerCustomerAccount(Customer customer) {
        checkCustomerInMap(customer);
        logger.info("Registered: " + customer.toString());
        this.customerMap.put(customer.getId(), customer);
    }

    private void checkCustomerInMap(Customer customer) {
        if(this.customerMap.containsKey(customer.getId())) {
            throw new CustomerAlreadyRegisteredException("Customer with following id was already registered: " + customer.getId());
        }

        if(this.customerMap.values().stream().anyMatch(val -> val.getEmail().equals(customer.getEmail()))) {
            throw new CustomerAlreadyRegisteredException("Customer with following email was already registered: " + customer.getEmail());
        }
    }

    public Map<String, Customer> getCustomerMap() {
        return Collections.unmodifiableMap(this.customerMap);
    }

    public List<Customer> getAllCustomers() {
      return this.customerMap.values().stream().toList();
    }

    public Customer getCustomerById(String id) {
        if(this.customerMap.containsKey(id)){
            return this.customerMap.get(id);
        }

        throw new NoSuchElementException("No customer with id: " + id);
    }
}
