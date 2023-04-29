package com.formlaez.infrastructure.model.entity.form;

import com.fasterxml.jackson.databind.JsonNode;
import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "form_field_option")
public class JpaFormFieldOption extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String label;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode metadata;

    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_field_id")
    private JpaFormField field;
}
