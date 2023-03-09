package com.formlaez.infrastructure.model.entity;

import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workspace_member")
public class JpaWorkspaceMember extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private JpaWorkspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private JpaUser user;

    @Enumerated(EnumType.STRING)
    private WorkspaceMemberRole role;
}
