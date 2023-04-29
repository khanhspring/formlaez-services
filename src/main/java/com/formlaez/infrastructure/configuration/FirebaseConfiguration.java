package com.formlaez.infrastructure.configuration;

import com.formlaez.infrastructure.property.FirebaseProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfiguration {

    private final FirebaseProperties properties;

    @PostConstruct
    public void init() throws IOException {
        var serviceAccount = properties.getServiceAccountJsonFile().getInputStream();

        var options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
