package com.example.spring_6_rest_mvc.repositories;

import com.example.spring_6_rest_mvc.bootstrap.BootstrapData;
import com.example.spring_6_rest_mvc.model.Beer;
import com.example.spring_6_rest_mvc.model.BeerStyle;
import com.example.spring_6_rest_mvc.service.impl.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My Beer 0123345678901233456789012334567890123345678901233456789012334567890123345678901233456789")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("234234234234")
                    .price(new BigDecimal("11.99"))
                    .build());

            beerRepository.flush();
        });
    }

    @Test
    void testGetBeerListByName() {
        Pageable pageable  = PageRequest.of(0, 200);
        Page<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", pageable);

        assertThat(list.getContent().size()).isEqualTo(200);
    }

    @Test
    void testGetBeerListByBeerStyle() {
        Pageable pageable  = PageRequest.of(0, 200);

        Page<Beer> list = beerRepository.findAllByBeerStyle(BeerStyle.PALE_ALE, pageable);

        assertThat(list.getContent().size()).isEqualTo(14);
    }

    @Test
    void saveBeer(){
        Beer savedBeer = beerRepository.save(Beer
                .builder()
                .beerName("My Beer")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("234234234234")
                .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}