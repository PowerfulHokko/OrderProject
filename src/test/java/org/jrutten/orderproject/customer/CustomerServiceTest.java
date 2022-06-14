package org.jrutten.orderproject.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        assertEquals(2, this.customerRepository.getCustomerMap().keySet().size());
    }

    @Test
    @DisplayName("When trying to register same customer twice then throw exception")
    void givenACustomer_whenTryingToRegisterSameCustomerTwice_thenCustomerAlreadyExistsException(){
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

}