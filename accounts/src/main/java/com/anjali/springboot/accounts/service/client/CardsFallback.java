package com.anjali.springboot.accounts.service.client;

import com.anjali.springboot.accounts.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardDto> fetchCardDetails(String mobileNumber, String correlationId) {
        return null;
    }
}
