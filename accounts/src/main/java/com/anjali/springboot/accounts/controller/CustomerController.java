package com.anjali.springboot.accounts.controller;

import com.anjali.springboot.accounts.dto.AccountsDto;
import com.anjali.springboot.accounts.dto.CardDto;
import com.anjali.springboot.accounts.dto.CustomerDetailsDto;
import com.anjali.springboot.accounts.dto.LoansDto;
import com.anjali.springboot.accounts.entity.Accounts;
import com.anjali.springboot.accounts.entity.Customer;
import com.anjali.springboot.accounts.exception.ResourceNotFoundException;
import com.anjali.springboot.accounts.mapper.AccountsMapper;
import com.anjali.springboot.accounts.mapper.CustomerMapper;
import com.anjali.springboot.accounts.repository.AccountsRepository;
import com.anjali.springboot.accounts.repository.CustomerRepository;
import com.anjali.springboot.accounts.service.ICustomerService;
import com.anjali.springboot.accounts.service.client.CardsFeignClient;
import com.anjali.springboot.accounts.service.client.LoansFeignClient;
import com.anjali.springboot.accounts.service.impl.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "CRUD REST APIs for Customers in Capital First",
        description = "CRUD REST APIs to  FETCH  customer details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;
    @Operation(
            summary = "Fetch customer details - accounts, loan & card details REST API",
            description = "REST API to fetch customer details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits") String mobileNumber){
        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDetailsDto);
    }
}
