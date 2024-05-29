package com.anjali.springboot.accounts.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CardsFeignClient cardsFeignClient;
    @Autowired
    private LoansFeignClient loansFeignClient;
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Customer","mobileNumber", mobileNumber)
                );
        Accounts customerAccount = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Account","customerId", customer.getCustomerId().toString())
                );
        CustomerDetailsDto customerDetailsDto =  CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(customerAccount, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity =  loansFeignClient.fetchLoanDetails(mobileNumber, correlationId);
        if(loansDtoResponseEntity != null) customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        ResponseEntity<CardDto> cardsDtoResponseEntity =  cardsFeignClient.fetchCardDetails(mobileNumber, correlationId);
        if(cardsDtoResponseEntity != null) customerDetailsDto.setCardDto(cardsDtoResponseEntity.getBody());
        return customerDetailsDto;
    }
}
