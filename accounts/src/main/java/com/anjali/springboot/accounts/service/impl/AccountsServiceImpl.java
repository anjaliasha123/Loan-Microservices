package com.anjali.springboot.accounts.service.impl;

import com.anjali.springboot.accounts.constants.AccountsConstants;
import com.anjali.springboot.accounts.dto.AccountsDto;
import com.anjali.springboot.accounts.dto.CustomerDto;
import com.anjali.springboot.accounts.entity.Accounts;
import com.anjali.springboot.accounts.entity.Customer;
import com.anjali.springboot.accounts.exception.CustomerAlreadyExistException;
import com.anjali.springboot.accounts.exception.ResourceNotFoundException;
import com.anjali.springboot.accounts.mapper.AccountsMapper;
import com.anjali.springboot.accounts.mapper.CustomerMapper;
import com.anjali.springboot.accounts.repository.AccountsRepository;
import com.anjali.springboot.accounts.repository.CustomerRepository;
import com.anjali.springboot.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }
    /**
     *
     * @param customer
     * @return Accounts -> new Account
     */
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Customer","mobileNumber", mobileNumber)
                );
        Accounts customerAccount = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Account","customerId", customer.getCustomerId().toString())
                );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(customerAccount, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountdto = customerDto.getAccountsDto();
        if(accountdto != null){
            Accounts accounts = accountsRepository
                    .findById(accountdto.getAccountNumber())
                    .orElseThrow(
                            ()-> new ResourceNotFoundException("Account", "AccountNumber", accountdto.getAccountNumber().toString())
                    );
            AccountsMapper.mapToAccounts(accountdto, accounts);
            accounts = accountsRepository.save(accounts);
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(
                            ()-> new ResourceNotFoundException("Customer","CustomerId", customerId.toString())
                    );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber.toString())
                );
        Long customerId = customer.getCustomerId();
        accountsRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);
        return true;
    }
}
