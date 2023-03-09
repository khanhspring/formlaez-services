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
@Table(name = "form_submission_snapshot")
public class JpaFormSubmissionSnapshot extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private JpaForm form;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_submission_id")
    private JpaFormSubmission submission;
}
