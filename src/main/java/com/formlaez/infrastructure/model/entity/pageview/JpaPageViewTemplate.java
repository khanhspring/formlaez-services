package com.formlaez.infrastructure.model.entity.pageview;

import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "page_view_template")
public class JpaPageViewTemplate extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String exampleUrl;
}
