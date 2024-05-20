package com.anjali.springboot.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer Details",
        description = "Schema to hold Customer, Cards, Loans and Account information"
)
public class CustomerDetailsDto {
    @Schema(
            description = "Name of the customer",
            example = "Capital"
    )
    @NotEmpty(message = "Name can not be empty")
    @Size(min = 5, max = 30, message = "The length of name should be between 5-30 characters")
    private String name;
    @Schema(
            description = "Email of the customer",
            example = "example@gmail.com"
    )
    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @Schema(
            description = "Mobile number of the customer",
            example = "923456788"
    )
    @NotEmpty(message = "Mobile number can not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
    @Schema(
            description = "Account details of the customer"
    )
    private AccountsDto accountsDto;
    @Schema(
            description = "Loan details of the customer"
    )
    private LoansDto loansDto;
    @Schema(
            description = "Card details of the customer"
    )
    private CardDto cardDto;
}
