package com.example.spring_6_rest_mvc.mappers;

import com.example.spring_6_rest_mvc.dto.BeerDTO;
import com.example.spring_6_rest_mvc.model.Beer;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    BeerDTO beertoBeerDTO(Beer beer);

    Beer beerDTOtoBeer(BeerDTO beerDTO);
}
