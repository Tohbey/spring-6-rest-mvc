package com.example.spring_6_rest_mvc.bootstrap;

import com.example.spring_6_rest_mvc.repositories.BeerRepository;
import com.example.spring_6_rest_mvc.repositories.CustomerRepository;
import com.example.spring_6_rest_mvc.service.BeerCsvService;
import com.example.spring_6_rest_mvc.service.impl.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService beerCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void Testrun() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isGreaterThan(200);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}