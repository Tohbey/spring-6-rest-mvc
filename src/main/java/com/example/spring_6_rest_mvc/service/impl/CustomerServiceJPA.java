package com.example.spring_6_rest_mvc.service.impl;

import com.example.spring_6_rest_mvc.dto.CustomerDTO;
import com.example.spring_6_rest_mvc.mappers.CustomerMapper;
import com.example.spring_6_rest_mvc.repositories.CustomerRepository;
import com.example.spring_6_rest_mvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CacheManager cacheManager;

    @Cacheable(cacheNames = "customerCache", key = "#uuid")
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.of(
                customerRepository.findById(uuid)
                        .map(customerMapper::customertoCustomerDTO))
                .orElse(null);
    }

    @Cacheable(cacheNames = "customerListCache")
    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customertoCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO savedCustomer(CustomerDTO customer) {
        cacheManager.getCache("customerListCache").clear();

        return customerMapper.customertoCustomerDTO(customerRepository.save(customerMapper.customerDTOtoCustomer(customer)));
    }

    @Override
    public Optional<CustomerDTO>  updateCustomer(UUID customerId, CustomerDTO customer) {
        clearCache(customerId);

        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setName(customer.getName());
            atomicReference.set(Optional.of(customerMapper
                    .customertoCustomerDTO(customerRepository.save(foundCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

//    @Caching(evict = {
//            @CacheEvict(cacheNames = "customerCache", key = "#customerId"),
//            @CacheEvict(cacheNames = "customerListCache")
//    })
    @Override
    public Boolean deleteCustomer(UUID customerId) {
        clearCache(customerId);
        if(customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    @Override
    public Optional<CustomerDTO> patchCustomer(UUID customerId, CustomerDTO customer) {
        clearCache(customerId);

        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            if (StringUtils.hasText(customer.getName())){
                foundCustomer.setName(customer.getName());
            }
            atomicReference.set(Optional.of(customerMapper
                    .customertoCustomerDTO(customerRepository.save(foundCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    private void clearCache(UUID customerId) {
        cacheManager.getCache("customerCache").evict(customerId);
        cacheManager.getCache("customerListCache").clear();
    }
}
