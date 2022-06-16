package org.jrutten.orderproject.customer;

import org.jrutten.orderproject.customer.representations.Address;
import org.jrutten.orderproject.customer.representations.CreateCustomerDTO;
import org.jrutten.orderproject.customer.representations.Customer;
import org.jrutten.orderproject.customer.representations.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


class CustomerServiceTest {
    CustomerService customerService;
    CustomerMapper customerMapper;
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp(){
        this.customerMapper = new CustomerMapper();
        this.customerRepository = new CustomerRepository();
        this.customerService = new CustomerService(customerRepository, customerMapper);
    }

    @Test
    @DisplayName("Given all fields are filled in properly when creating a costumer, then succesfully create customer.")
    void givenAllFieldsFilled_whenCreatingACustomer_thenSuccesfullyCreate(){
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO(
                "Fred",
                "Flinstone",
                "fred@stone.dk",
                new Address("magmastreet", 2, "", 1, "Bedrock"),
                "No phones existed at the time"
                );

        CustomerDTO customerAccount = this.customerService.createCustomerAccount(createCustomerDTO);

        assertTrue(customerAccount.equals(createCustomerDTO));
    }

    @Test
    @DisplayName("Given all fields are filled in properly when creating two costumers with different emails, then succesfully create customers.")
    void givenAllFieldsFilled_whenCreatingTwoUniqueCustomer_thenSuccesfullyCreate(){
        int initialSize = this.customerRepository.getCustomerMap().size();

        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO(
                "Fred",
                "Flinstone",
                "fred@stone.dk",
                new Address("magmastreet", 2, "", 1, "Bedrock"),
                "No phones existed at the time"
        );

        CreateCustomerDTO createCustomerDTO2 = new CreateCustomerDTO(
                "Maria",
                "VanHalen",
                "Mvhalen@something.no",
                new Address("brune", 2, "", 44000, "Bergen"),
                "478-4258-4454"
        );

        CustomerDTO customerAccount = this.customerService.createCustomerAccount(createCustomerDTO);
        CustomerDTO customerAccount2 = this.customerService.createCustomerAccount(createCustomerDTO2);

        assertEquals(initialSize+2, this.customerRepository.getCustomerMap().keySet().size());
    }

    @Test
    @DisplayName("When trying to register same id twice then throw exception")
    void givenACustomer_whenTryingToRegisterSameID_thenCustomerAlreadyExistsException(){
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO(
                "Fred",
                "Flinstone",
                "fred@stone.dk",
                new Address("magmastreet", 2, "", 1, "Bedrock"),
                "No phones existed at the time"
        );

        CustomerDTO customerAccount = this.customerService.createCustomerAccount(createCustomerDTO);

        assertThrows(CustomerAlreadyRegisteredException.class, ()->this.customerService.createCustomerAccount(createCustomerDTO));
    }

    @Test
    @DisplayName("When trying to register same id twice then throw exception")
    void givenACustomer_whenTryingToRegisterSameEmail_thenCustomerAlreadyExistsException(){
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO(
                "Fred",
                "Flinstone",
                "fred@stone.dk",
                new Address("magmastreet", 2, "", 1, "Bedrock"),
                "No phones existed at the time"
        );

        CreateCustomerDTO createCustomerDTO2 = new CreateCustomerDTO(
                "Maria",
                "Flinstone",
                "fred@stone.dk",
                new Address("magmastreet", 2, "", 1, "Bedrock"),
                "Smoke signals"
        );

        CustomerDTO customerAccount = this.customerService.createCustomerAccount(createCustomerDTO);

        assertThrows(CustomerAlreadyRegisteredException.class, ()->this.customerService.createCustomerAccount(createCustomerDTO2));
    }

    @Test
    @DisplayName("When trying to register a customer without a firstname, then throw exception")
    void givenCustomerWithNoFirstName_thenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, ()->{
            new CreateCustomerDTO(
                    " ",
                    "Flinstone",
                    "fred@stone.dk",
                    new Address("magmastreet", 2, "", 1, "Bedrock"),
                    "No phones existed at the time"
            );
        });
    }

    @Test
    @DisplayName("given an Id for an existing customer when searching, return this customer")
    void givenAnId_whenCustomerExists_thenReturnCustomer(){
        CreateCustomerDTO customerDTO = new CreateCustomerDTO(
                "Fred",
                "Flinstone",
                "fred@stone.dk",
                new Address("magmastreet", 2, "", 1, "Bedrock"),
                "No phones existed at the time"
        );

        Customer customer = this.customerMapper.toCustomer(customerDTO);
        this.customerRepository.registerCustomerAccount(customer);

        CustomerDTO customerById = this.customerService.getCustomerById(customer.getId());

        assertEquals(customer, customerById);
    }

    @Test
    @DisplayName("given an Id for an non-existing customer when searching, throw NoSuchElementException")
    void givenAnId_whenCustomerDoesNotExists_throwException(){
        assertThrows(NoSuchElementException.class, () -> this.customerService.getCustomerById("EEEEEEEEEE"));
    }

}