package com.example.spring_6_rest_mvc.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class BeerOrderCreateDTO {
    private String customerRef;

    @NotNull
    private UUID customerId;

    private Set<BeerOrderLineCreateDTO> beerOrderLines;
}
