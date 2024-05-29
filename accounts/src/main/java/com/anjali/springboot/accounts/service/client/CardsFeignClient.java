package com.anjali.springboot.accounts.service.client;

import com.anjali.springboot.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CARDS", fallback = CardsFallback.class)
public interface CardsFeignClient {
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CardDto> fetchCardDetails(@RequestParam String mobileNumber,@RequestHeader("roadMapLearner-correlation-id") String correlationId);
}
