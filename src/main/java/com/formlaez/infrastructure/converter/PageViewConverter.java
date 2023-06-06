package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.pageview.PageViewDetailResponse;
import com.formlaez.application.model.response.pageview.PageViewListingFieldResponse;
import com.formlaez.application.model.response.pageview.PageViewResponse;
import com.formlaez.application.model.response.pageview.PageViewTemplateResponse;
import com.formlaez.infrastructure.model.entity.pageview.JpaPageView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PageViewConverter implements Converter<JpaPageView, PageViewResponse> {

    @Nullable
    @Override
    public PageViewResponse convert(@Nullable JpaPageView source) {
        if (Objects.isNull(source)) {
            return null;
        }

        var template = PageViewTemplateResponse.builder()
                .id(source.getTemplate().getId())
                .name(source.getTemplate().getName())
                .code(source.getTemplate().getCode())
                .description(source.getTemplate().getDescription())
                .exampleUrl(source.getTemplate().getExampleUrl())
                .build();

        PageViewDetailResponse detail = null;
        if (Objects.nonNull(source.getDetail())) {
            detail = PageViewDetailResponse.builder()
                    .type(source.getDetail().getType())
                    .customContent(source.getDetail().getCustomContent())
                    .redirectType(source.getDetail().getRedirectType())
                    .redirectUrl(source.getDetail().getRedirectUrl())
                    .build();
        }

        List<PageViewListingFieldResponse> listingFields = new ArrayList<>();
        for (var field : source.getListingFields()) {
            var fieldResponse = PageViewListingFieldResponse.builder()
                    .fieldCode(field.getFieldCode())
                    .targetFieldCode(field.getTargetFieldCode())
                    .fixedValue(field.getFixedValue())
                    .type(field.getType())
                    .build();
            listingFields.add(fieldResponse);
        }

        return PageViewResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .description(source.getDescription())
                .title(source.getTitle())
                .status(source.getStatus())
                .template(template)
                .detail(detail)
                .listingFields(listingFields)
                .build();
    }
}
