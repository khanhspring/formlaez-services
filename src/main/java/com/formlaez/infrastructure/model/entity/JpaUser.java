package com.formlaez.infrastructure.model.entity;

import com.formlaez.infrastructure.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class JpaUser extends JpaBaseEntity {
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
