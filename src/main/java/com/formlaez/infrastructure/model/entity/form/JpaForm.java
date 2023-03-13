package com.formlaez.infrastructure.model.entity.form;

import com.formlaez.infrastructure.enumeration.*;
import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import com.formlaez.infrastructure.model.entity.JpaWorkspace;
import com.formlaez.infrastructure.model.entity.team.JpaTeam;
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

    @Enumerated(EnumType.STRING)
    private FormStatus status;

    @Enumerated(EnumType.STRING)
    private FormSharingScope sharingScope;

    private boolean acceptResponses;
    private boolean allowPrinting;
    private boolean allowResponseEditing;

    @OneToMany(mappedBy = "form", fetch = FetchType.LAZY)
    @OrderBy("position asc")
    private List<JpaFormPage> pages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private JpaWorkspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private JpaTeam team;
}
