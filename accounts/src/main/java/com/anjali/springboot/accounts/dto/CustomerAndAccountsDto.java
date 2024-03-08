package com.anjali.springboot.accounts.dto;

import lombok.Data;

@Data
public class CustomerAndAccountsDto {
    private String name;
    private String email;
    private String mobileNumber;
    private AccountsDto accountsDto;
}
