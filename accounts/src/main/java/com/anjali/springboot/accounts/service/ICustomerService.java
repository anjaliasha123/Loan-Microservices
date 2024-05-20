package com.anjali.springboot.accounts.service;

import com.anjali.springboot.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
