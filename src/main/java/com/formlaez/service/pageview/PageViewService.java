package com.formlaez.service.pageview;

import com.fasterxml.jackson.databind.JsonNode;
import com.formlaez.application.model.response.pageview.PageViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageViewService {

    PageViewResponse findByCode(String code);
    Page<JsonNode> getPageViewData(String pageViewCode, Pageable pageable);
}
