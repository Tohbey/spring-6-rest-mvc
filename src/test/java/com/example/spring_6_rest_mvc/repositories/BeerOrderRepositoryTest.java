package com.example.spring_6_rest_mvc.repositories;

import com.example.spring_6_rest_mvc.model.Beer;
import com.example.spring_6_rest_mvc.model.BeerOrder;
import com.example.spring_6_rest_mvc.model.BeerOrderShipment;
import com.example.spring_6_rest_mvc.model.Customer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);
    }

    @Test
    @Transactional
    void testBeerOrder(){
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("Test Order")
                .customer(testCustomer)
                .beerOrderShipment(
                        BeerOrderShipment
                                .builder()
                                .trackingNumber("Hello")
                                .build()
                )
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
        System.out.println(savedBeerOrder.getCustomerRef());
    }
}