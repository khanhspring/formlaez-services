package com.formlaez.infrastructure.model.entity;

import com.formlaez.infrastructure.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.descriptor.java.UUIDJavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;

import java.sql.Types;
import java.util.UUID;

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
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
