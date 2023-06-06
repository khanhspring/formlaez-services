package com.formlaez.infrastructure.model.entity.pageview;

import com.formlaez.infrastructure.enumeration.PageViewDetailRedirectType;
import com.formlaez.infrastructure.enumeration.PageViewDetailType;
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
@Table(name = "page_view_detail")
public class JpaPageViewDetail extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_view_id")
    private JpaPageView pageView;

    @Enumerated(EnumType.STRING)
    private PageViewDetailType type;

    private String customContent;
    private String redirectUrl;

    @Enumerated(EnumType.STRING)
    private PageViewDetailRedirectType redirectType;
}
