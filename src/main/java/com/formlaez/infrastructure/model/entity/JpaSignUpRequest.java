package com.formlaez.infrastructure.model.entity;

import com.formlaez.infrastructure.enumeration.SignUpRequestStatus;
import com.formlaez.infrastructure.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sign_up_request")
public class JpaSignUpRequest extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String verificationCode;
    private String password;
    private String firstName;
    private String lastName;
    private Instant expireDate;

    @Enumerated(EnumType.STRING)
    private SignUpRequestStatus status;
}
