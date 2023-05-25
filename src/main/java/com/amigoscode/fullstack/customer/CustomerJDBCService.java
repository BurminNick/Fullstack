package com.amigoscode.fullstack.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCService implements CustomerDAO{
    private final JdbcTemplate jdbcTemplate;

    public CustomerJDBCService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT * FROM customers
                """;
        RowMapper<Customer> rowMapper = (rs, rowNum) -> {
            Customer customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_email"),
                    rs.getInt("customer_age")
            );
            return customer;
        };
        List<Customer> customers = jdbcTemplate.query(sql, rowMapper);
        return customers;
    }

    @Override
    public void insertCustomer(Customer customer){
        var sql = """
                INSERT INTO customers(customer_name, customer_email, customer_age)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return false;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return false;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void deleteCustomerById(Integer id) {

    }

    @Override
    public void updateCustomer(Customer customer) {

    }
}
