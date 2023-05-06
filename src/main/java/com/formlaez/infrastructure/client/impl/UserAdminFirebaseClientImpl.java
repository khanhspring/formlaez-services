package com.formlaez.infrastructure.client.impl;

import com.formlaez.application.model.response.ResponseId;
import com.formlaez.infrastructure.client.UserAdminClient;
import com.formlaez.infrastructure.client.model.ChangeUserPasswordRequest;
import com.formlaez.infrastructure.client.model.CreateUserRequest;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.property.AuthInternalClientProperties;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Objects;

@Slf4j
@Service("userAdminFirebaseClient")
public class UserAdminFirebaseClientImpl implements UserAdminClient {

    @Override
    public ResponseId<String> createUser(CreateUserRequest request) {
        try {
            var createRequest = new UserRecord.CreateRequest()
                    .setEmail(request.getEmail())
                    .setEmailVerified(true)
                    .setPassword(request.getRawPassword())
                    .setDisplayName(request.getFirstName() + " " + request.getLastName())
                    .setDisabled(false);

            var userRecord = FirebaseAuth.getInstance().createUser(createRequest);
            return ResponseId.of(userRecord.getUid());
        } catch (Exception e) {
            log.error("Create user error", e);
            throw new ApplicationException(e);
        }
    }

    @Override
    public void changeUserPassword(ChangeUserPasswordRequest request) {
        try {
            var updateRequest = new UserRecord.UpdateRequest(request.getUserId())
                    .setPassword(request.getNewPassword());

            FirebaseAuth.getInstance().updateUser(updateRequest);
        } catch (Exception e) {
            log.error("Change user password error", e);
            throw new ApplicationException(e);
        }
    }
}
