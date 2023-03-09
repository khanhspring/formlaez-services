package com.formlaez.infrastructure.model.entity.form;

import com.formlaez.infrastructure.enumeration.FormCoverType;
import com.formlaez.infrastructure.enumeration.FormScope;
import com.formlaez.infrastructure.enumeration.WorkspaceType;
import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "form")
public class JpaForm extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private FormScope scope;

    @Enumerated(EnumType.STRING)
    private FormCoverType coverType;

    private String coverColor;
    private String coverImageUrl;

    @OneToMany(mappedBy = "form", fetch = FetchType.LAZY)
    @OrderBy("position asc")
    private List<JpaFormPage> pages;
}
