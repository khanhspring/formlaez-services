package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.UserResponse;
import com.formlaez.infrastructure.model.entity.JpaUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserResponseConverter implements Converter<JpaUser, UserResponse> {

    @Nullable
    @Override
    public UserResponse convert(@Nullable JpaUser source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return UserResponse.builder()
                .id(source.getId())
                .email(source.getEmail())
                .username(source.getUsername())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .status(source.getStatus())
                .build();
    }
}
