package com.formlaez.infrastructure.model.entity.form;

import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "form_ending")
public class JpaFormEnding extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private boolean hideButton;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private JpaForm form;
}
