package com.amigoscode.fullstack;

import com.amigoscode.fullstack.customer.Customer;
import com.amigoscode.fullstack.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FullstackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullstackApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {

            Customer nadya = new Customer("Nadya","nadya@gmail.com", 37);
            Customer nick = new Customer("Nick","nick@gmail.com", 38);

            customerRepository.save(nadya);
            customerRepository.save(nick);
        };
    }
}
