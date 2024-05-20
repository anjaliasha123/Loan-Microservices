package com.anjali.springboot.accounts;

import com.anjali.springboot.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
//@ComponenetScans -> scan for controllers if external
//@EnableJpaRepositories -> if repositories package are external
//@EntityScan -> if entities are place outside main Accounts file
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "Capital First Accounts Microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Anjali",
						email = "ajacobash@gmail.com",
						url = "https://www.linkedin.com/in/anjali-asha/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.linkedin.com/in/anjali-asha/"
				)
		)
)
@EnableFeignClients
public class AccountsApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
