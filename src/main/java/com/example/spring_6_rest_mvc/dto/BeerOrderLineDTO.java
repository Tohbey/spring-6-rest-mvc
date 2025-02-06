package com.example.spring_6_rest_mvc.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BeerOrderLineDTO {
    private UUID id;

    private Long version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private BeerDTO beer;

    @Min(value = 1, message = "Quantity On Hand must be greater than 0")
    private Integer orderQuantity;
    private Integer quantityAllocated;
}
