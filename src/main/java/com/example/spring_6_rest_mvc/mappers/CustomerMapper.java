package com.example.spring_6_rest_mvc.mappers;

import com.example.spring_6_rest_mvc.dto.CustomerDTO;
import com.example.spring_6_rest_mvc.model.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDTOtoCustomer(CustomerDTO customerDTO);

    CustomerDTO customertoCustomerDTO(Customer customer);
}
