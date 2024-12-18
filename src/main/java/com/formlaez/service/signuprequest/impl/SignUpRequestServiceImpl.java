package com.formlaez.service.signuprequest.impl;

import com.formlaez.application.model.request.ConfirmSignUpRequest;
import com.formlaez.application.model.request.EmailTemplatedSendingRequest;
import com.formlaez.application.model.request.SignUpRequest;
import com.formlaez.infrastructure.client.UserAdminClient;
import com.formlaez.infrastructure.client.model.CreateUserRequest;
import com.formlaez.infrastructure.configuration.exception.DuplicatedException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.enumeration.SignUpRequestStatus;
import com.formlaez.infrastructure.enumeration.UserStatus;
import com.formlaez.infrastructure.model.entity.JpaSignUpRequest;
import com.formlaez.infrastructure.model.entity.JpaUser;
import com.formlaez.infrastructure.property.SendgridProperties;
import com.formlaez.infrastructure.property.aws.AwsProperties;
import com.formlaez.infrastructure.repository.JpaSignUpRequestRepository;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.infrastructure.util.RandomUtils;
import com.formlaez.service.email.EmailService;
import com.formlaez.service.signuprequest.SignUpRequestService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class SignUpRequestServiceImpl implements SignUpRequestService {

    private static final int EXPIRE_DAYS = 1;
    private final JpaUserRepository jpaUserRepository;
    private final JpaSignUpRequestRepository jpaSignUpRequestRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAdminClient userAdminClient;
    private final EmailService emailService;
    private final SendgridProperties sendgridProperties;


    public SignUpRequestServiceImpl(JpaUserRepository jpaUserRepository,
                                    JpaSignUpRequestRepository jpaSignUpRequestRepository,
                                    PasswordEncoder passwordEncoder,
                                    UserAdminClient userAdminClient,
                                    EmailService emailService,
                                    SendgridProperties sendgridProperties) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaSignUpRequestRepository = jpaSignUpRequestRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAdminClient = userAdminClient;
        this.emailService = emailService;
        this.sendgridProperties = sendgridProperties;
    }

    @Override
    @Transactional
    public void signUp(SignUpRequest request) {
        if (jpaUserRepository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException();
        }
        var signUpRequest = JpaSignUpRequest.builder()
                .email(request.getEmail())
                .verificationCode(RandomUtils.randomNanoId(7))
                .expireDate(Instant.now().plus(EXPIRE_DAYS, ChronoUnit.DAYS))
                .build();
        jpaSignUpRequestRepository.save(signUpRequest);

        var emailRequest = EmailTemplatedSendingRequest.builder()
                .fromAddress(sendgridProperties.getDefaultSender())
                .fromName(sendgridProperties.getDefaultSenderName())
                .templateId(sendgridProperties.getSignUpTemplateId())
                .data(Map.of("verification_code", signUpRequest.getVerificationCode()))
                .toAddresses(List.of(request.getEmail()))
                .build();
        emailService.sendTemplatedEmail(emailRequest);
    }

    @Override
    @Transactional
    public void confirm(ConfirmSignUpRequest request) {
        Assert.isTrue(request.getPassword().length() >= 7, "Password must be at least 7 characters");
        var signUpRequest = jpaSignUpRequestRepository.findByVerificationCode(request.getVerificationCode())
                .orElseThrow(ResourceNotFoundException::new);

        if (!signUpRequest.getEmail().equals(request.getEmail())) {
            throw new InvalidParamsException("Email is incorrect");
        }

        if (signUpRequest.getStatus() == SignUpRequestStatus.Success) {
            throw new InvalidParamsException("Verification code has expired or incorrect");
        }

        if (signUpRequest.getExpireDate().isBefore(Instant.now())) {
            throw new InvalidParamsException("Verification code has expired or incorrect");
        }

        signUpRequest.setFirstName(request.getFirstName().trim());
        signUpRequest.setLastName(request.getLastName().trim());
        signUpRequest.setPassword(passwordEncoder.encode(request.getPassword()));
        signUpRequest.setStatus(SignUpRequestStatus.Success);
        jpaSignUpRequestRepository.save(signUpRequest);

        var createUserRequest = CreateUserRequest.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .rawPassword(request.getPassword())
                .build();
        var response = userAdminClient.createUser(createUserRequest);

        var user = JpaUser.builder()
                .id(response.getId())
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(UserStatus.Active)
                .password(signUpRequest.getPassword())
                .build();
        jpaUserRepository.save(user);

        var emailRequest = EmailTemplatedSendingRequest.builder()
                .fromAddress(sendgridProperties.getDefaultSender())
                .fromName(sendgridProperties.getDefaultSenderName())
                .templateId(sendgridProperties.getWelcomeTemplateId())
                .data(Map.of("name", signUpRequest.getFirstName()))
                .toAddresses(List.of(request.getEmail()))
                .build();
        emailService.sendTemplatedEmail(emailRequest);
    }
}
