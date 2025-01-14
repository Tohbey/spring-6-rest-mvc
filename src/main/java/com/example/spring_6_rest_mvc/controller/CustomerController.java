package com.example.spring_6_rest_mvc.controller;

import com.example.spring_6_rest_mvc.dto.CustomerDTO;
import com.example.spring_6_rest_mvc.exception.NotFoundException;
import com.example.spring_6_rest_mvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(CustomerController.CUSTOMER_PATH)
@RequiredArgsConstructor
@RestController
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_BY_ID =  "/{customerId}";


    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDTO> listAllCustomers(){
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = CUSTOMER_BY_ID, method = RequestMethod.GET)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID id){
        return customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer){
        CustomerDTO savedCustomer = customerService.savedCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/"+savedCustomer.getId().toString());

        return new ResponseEntity<CustomerDTO>(savedCustomer, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = CUSTOMER_BY_ID)
    public ResponseEntity updateCustomer(@PathVariable("customerId") UUID customerId,
                                                   @RequestBody CustomerDTO customer){
        if(customerService.updateCustomer(customerId, customer).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = CUSTOMER_BY_ID)
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable("customerId") UUID customerId,
                                                   @RequestBody CustomerDTO customer){
        if(customerService.patchCustomer(customerId, customer).isEmpty()){
          throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = CUSTOMER_BY_ID)
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") UUID customerId){
        if(!customerService.deleteCustomer(customerId)){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
