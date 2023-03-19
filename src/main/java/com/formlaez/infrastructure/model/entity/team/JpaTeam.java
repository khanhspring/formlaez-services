package com.formlaez.infrastructure.model.entity.team;

import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import com.formlaez.infrastructure.model.entity.JpaWorkspace;
import com.formlaez.infrastructure.model.entity.form.JpaFormPage;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class JpaTeam extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String description;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    @OrderBy("createdDate asc")
    private List<JpaTeamMember> members;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private JpaWorkspace workspace;
}
