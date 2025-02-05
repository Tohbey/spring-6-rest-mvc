package com.example.spring_6_rest_mvc.mappers;

import com.example.spring_6_rest_mvc.dto.BeerDTO;
import com.example.spring_6_rest_mvc.model.Beer;
import com.example.spring_6_rest_mvc.model.BeerAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeerMapper {

    BeerDTO beertoBeerDTO(Beer beer);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "beerOrderLine", ignore = true)
    Beer beerDTOtoBeer(BeerDTO beerDTO);

    @Mapping(target = "createdDateAudit", ignore = true)
    @Mapping(target = "auditId", ignore = true)
    @Mapping(target = "auditEventType", ignore = true)
    @Mapping(target = "principalName", ignore = true)
    BeerAudit beerToBeerAudit(Beer beer);
}
