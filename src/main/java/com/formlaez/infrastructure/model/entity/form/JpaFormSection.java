package com.formlaez.infrastructure.model.entity.form;

import com.formlaez.infrastructure.enumeration.FormSectionType;
import com.formlaez.infrastructure.model.entity.JpaBaseEntityWithoutVersion;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "form_section")
public class JpaFormSection extends JpaBaseEntityWithoutVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String title;
    private String description;
    private String variableName;

    @Enumerated(EnumType.STRING)
    private FormSectionType type;

    private boolean repeatable;
    private String repeatButtonLabel;
    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_page_id")
    private JpaFormPage page;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    @OrderBy("position asc")
    private List<JpaFormField> fields;
}
