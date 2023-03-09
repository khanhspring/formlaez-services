package com.formlaez.infrastructure.model.entity.form;

import com.formlaez.infrastructure.enumeration.FormCoverType;
import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import com.formlaez.infrastructure.model.entity.JpaWorkspace;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "form_page")
public class JpaFormPage extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String title;
    private String description;
    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private JpaForm form;

    @OneToMany(mappedBy = "page", fetch = FetchType.LAZY)
    @OrderBy("position asc")
    private List<JpaFormSection> sections;
}
