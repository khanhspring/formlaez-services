package com.formlaez.infrastructure.model.entity.pageview;

import com.formlaez.infrastructure.enumeration.PageViewStatus;
import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "page_view")
public class JpaPageView extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private PageViewStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private JpaPageViewTemplate template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private JpaForm form;

    @OneToMany(mappedBy = "pageView", fetch = FetchType.LAZY)
    private List<JpaPageViewListingField> listingFields;

    @OneToOne(mappedBy = "pageView", fetch = FetchType.LAZY)
    private JpaPageViewDetail detail;
}
