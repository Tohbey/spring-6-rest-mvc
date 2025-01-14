package com.example.spring_6_rest_mvc.service.impl;

import com.example.spring_6_rest_mvc.dto.CustomerDTO;
import com.example.spring_6_rest_mvc.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 3")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap = new HashMap<>();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.of(customerMap.get(uuid));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO savedCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap.put(customer.getId(), savedCustomer);

        return customer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);

        existingCustomer.setName(customer.getName());
        existingCustomer.setVersion(customer.getVersion());
        existingCustomer.setUpdateDate(LocalDateTime.now());
        existingCustomer.setCreatedDate(LocalDateTime.now());

        customerMap.put(customerId, existingCustomer);
        return Optional.of(existingCustomer);
    }

    @Override
    public Boolean deleteCustomer(UUID customerId) {
        customerMap.remove(customerId);
        return Boolean.TRUE;
    }

    @Override
    public Optional<CustomerDTO> patchCustomer(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);

        if(StringUtils.hasText(customer.getName())){
            existingCustomer.setName(customer.getName());
        }

        if(customer.getVersion() != null){
            existingCustomer.setVersion(customer.getVersion());
        }

        customerMap.put(customerId, existingCustomer);

        return Optional.of(existingCustomer);
    }
}
