package com.formlaez.infrastructure.configuration;

import com.formlaez.infrastructure.property.SendgridProperties;
import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SendgridConfiguration {

    @Bean
    public SendGrid sendGrid(SendgridProperties sendgridProperties) {
        return new SendGrid(sendgridProperties.getApiKey());
    }

}
