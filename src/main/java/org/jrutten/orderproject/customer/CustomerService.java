package org.jrutten.orderproject.customer;

import lombok.AllArgsConstructor;
import org.jrutten.orderproject.customer.representations.CreateCustomerDTO;
import org.jrutten.orderproject.customer.representations.Customer;
import org.jrutten.orderproject.customer.representations.CustomerDTO;
//import org.jrutten.orderproject.security.SecurityLogger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
public class CustomerService {
    private final JPACustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
//    private final SecurityLogger securityLogger;



    public CustomerDTO createCustomerAccount(CreateCustomerDTO createCustomerDTO) {
        if(this.customerRepository.findByEmail(createCustomerDTO.getEmail()).isPresent()){
            throw new CustomerAlreadyRegisteredException("Email already used");
        }

        Customer customer = this.customerMapper.toCustomer(createCustomerDTO);
        CustomerDTO customerDTO = this.customerMapper.toCustomerDTO(customer);

        this.customerRepository.save(customer);

        return customerDTO;
    }

    public List<CustomerDTO> getAllCustomers() {
        return this.customerMapper.toCustomerDTO(this.customerRepository.findAll());
    }

    public CustomerDTO getCustomerById(int id) {
        return this.customerMapper.toCustomerDTO(this.customerRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public void logSecurity(Class<? extends CustomerController> aClass, String methodName, String token) {
//        this.securityLogger.log(aClass, methodName, token);
    }
}
