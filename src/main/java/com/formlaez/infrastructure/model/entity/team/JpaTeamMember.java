package com.formlaez.infrastructure.model.entity.team;

import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import com.formlaez.infrastructure.model.entity.JpaUser;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team_member")
public class JpaTeamMember extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private JpaTeam team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private JpaUser user;

    @Enumerated(EnumType.STRING)
    private TeamMemberRole role;
}
