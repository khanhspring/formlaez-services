package com.formlaez.infrastructure.model.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class JpaBaseEntity extends JpaTimestampEntity {
    @CreatedBy
    @JdbcTypeCode(Types.VARCHAR)
    private String createdBy;

    @LastModifiedBy
    @JdbcTypeCode(Types.VARCHAR)
    private String lastModifiedBy;

}
