package com.example.spring_6_rest_mvc.repositories;

import com.example.spring_6_rest_mvc.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveCustomer() {
        Customer customer = customerRepository.save(Customer.builder()
                .name("New Name")
                .build());

        assertThat(customer.getId()).isNotNull();

    }
}