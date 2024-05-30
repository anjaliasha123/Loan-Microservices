package com.anjali.springboot.accounts.controller;


import com.anjali.springboot.accounts.dto.CustomerDetailsDto;
import com.anjali.springboot.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Customers in Capital First",
        description = "CRUD REST APIs to  FETCH  customer details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
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
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader("roadMapLearner-correlation-id") String correlationId,
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
            String mobileNumber
    ){
//        logger.debug("roadMapLearner-correlation-id found {}", correlationId);
        logger.debug("fetch customer details");
        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber, correlationId);
        logger.debug("fetch customer details end");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDetailsDto);
    }
}
