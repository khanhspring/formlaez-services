package com.formlaez.infrastructure.configuration;

import com.formlaez.infrastructure.property.BasicAuthProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.ObjectUtils;

@Configuration
public class BasicAuthConfiguration {

    @Bean
    public UserDetailsService users(BasicAuthProperties basicAuthProperties) {
        if (ObjectUtils.isEmpty(basicAuthProperties.getUsers())) {
            return new InMemoryUserDetailsManager();
        }

        var userDetails = new InMemoryUserDetailsManager();
        for (var basicAuthUser: basicAuthProperties.getUsers()) {
            UserDetails user = User.builder()
                    .username(basicAuthUser.getUsername())
                    .password(basicAuthUser.getPassword())
                    .roles(basicAuthUser.getRoles().toArray(String[]::new))
                    .build();
            userDetails.createUser(user);
        }
        return userDetails;
    }
}
