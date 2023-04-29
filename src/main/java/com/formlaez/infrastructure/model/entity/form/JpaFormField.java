package com.formlaez.infrastructure.model.entity.form;

import com.formlaez.infrastructure.enumeration.FormFieldType;
import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "form_field")
public class JpaFormField extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String title;
    private String description;
    private String variableName;

    @Enumerated(EnumType.STRING)
    private FormFieldType type;

    private boolean required;
    private String placeholder;
    private String content;
    private boolean hideTitle;
    private String url;
    private String caption;
    private String acceptedDomains;

    private Integer minLength;
    private Integer maxLength;

    private BigDecimal min;
    private BigDecimal max;

    private boolean readonly;
    private boolean multipleSelection;
    private boolean showTime;

    private int position;

    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    @OrderBy("position asc")
    private List<JpaFormFieldOption> options;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_section_id")
    private JpaFormSection section;
}
