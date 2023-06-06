package com.formlaez.infrastructure.model.entity.pageview;

import com.formlaez.infrastructure.enumeration.PageViewListingFieldType;
import com.formlaez.infrastructure.model.entity.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "page_view_listing_field")
public class JpaPageViewListingField extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_view_id")
    private JpaPageView pageView;

    @Enumerated(EnumType.STRING)
    private PageViewListingFieldType type;

    private String fieldCode;
    private String targetFieldCode;
    private String fixedValue;
}
