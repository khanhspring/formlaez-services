package com.formlaez.infrastructure.model.entity;

import com.formlaez.infrastructure.enumeration.AttachmentCategory;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attachment")
public class JpaAttachment extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String originalName;
    private String description;
    private String extension;
    private long size;
    private String type;
    private String cloudStorageLocation;

    @Enumerated(EnumType.STRING)
    private AttachmentCategory category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private JpaWorkspace workspace;
}
