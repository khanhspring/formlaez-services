package com.formlaez.infrastructure.model.entity;

import com.formlaez.infrastructure.enumeration.WorkspaceType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workspace")
public class JpaWorkspace extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private WorkspaceType type;
}
