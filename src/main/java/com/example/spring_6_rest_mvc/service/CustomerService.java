package com.example.spring_6_rest_mvc.service;

import com.example.spring_6_rest_mvc.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional<CustomerDTO> getCustomerById(UUID uuid);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO savedCustomer(CustomerDTO customer);

    Optional<CustomerDTO>  updateCustomer(UUID customerId, CustomerDTO customer);

    Boolean deleteCustomer(UUID customerId);

    Optional<CustomerDTO> patchCustomer(UUID customerId, CustomerDTO customer);
}
