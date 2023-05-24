package com.amigoscode.fullstack.customer;

import com.amigoscode.fullstack.exception.DuplicateResourceException;
import com.amigoscode.fullstack.exception.RequestValidationException;
import com.amigoscode.fullstack.exception.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> allCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().
                map(c -> new CustomerDTO(c.getId(), c.getName())).collect(Collectors.toList());
    }

    public Customer getCustomer(Integer id) {
        return customerRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("customer with id [%s] not found".formatted(id)));
    }


    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        String email = customerRegistrationRequest.email();
        if(customerRepository.existsCustomerByEmail(email)){
            throw new DuplicateResourceException("email is already taken");
        }
        customerRepository.save(new Customer(
                customerRegistrationRequest.name(),
                email,
                customerRegistrationRequest.age()
        ));
    }

    public void updateCustomer(Integer id, CustomerUpdateRequest updateRequest){
        Customer customer = getCustomer(id);
        boolean changes = false;
        if(updateRequest.name()!=null && !updateRequest.name().equals(customer.getName())){
            customer.setName(updateRequest.name());
            changes = true;
        }
        if(updateRequest.email()!=null && !updateRequest.email().equals(customer.getEmail())){
            customer.setEmail(updateRequest.email());
            changes = true;
        }
        if(updateRequest.age()!=null && updateRequest.age()!=customer.getAge()){
            customer.setAge(updateRequest.age());
            changes = true;
        }
        if(!changes){
            throw new RequestValidationException("no changes found");
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id){
        if(!customerRepository.existsCustomerById(id)){
            throw new ResourceNotFound("customer with id [%s] not found".formatted(id));
        }
        customerRepository.deleteById(id);
    }
}
