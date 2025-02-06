package com.example.spring_6_rest_mvc.mappers;

import com.example.spring_6_rest_mvc.dto.BeerOrderDTO;
import com.example.spring_6_rest_mvc.dto.BeerOrderLineDTO;
import com.example.spring_6_rest_mvc.dto.BeerOrderShipmentDTO;
import com.example.spring_6_rest_mvc.model.BeerOrder;
import com.example.spring_6_rest_mvc.model.BeerOrderLine;
import com.example.spring_6_rest_mvc.model.BeerOrderShipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeerOrderMapper {

    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderShipment beerOrderShipmentDtoToBeerOrderShipment(BeerOrderShipmentDTO beerOrderShipmentDTO);

    BeerOrderShipmentDTO beerOrderShipmentToBeerOrderShipmentDto(BeerOrderShipment beerOrderShipment);

    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDTO beerOrderLineDTO);

    BeerOrderLineDTO beerOrderLineToBeerOrderLineDto(BeerOrderLine beerOrderLine);

    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDTO beerOrder);

    BeerOrderDTO beerOrderToBeerOrderDto(BeerOrder beerOrder);
}
