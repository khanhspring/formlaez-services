package com.formlaez.application.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.formlaez.application.model.response.pageview.PageViewResponse;
import com.formlaez.service.pageview.PageViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/page-views")
public class PageViewController {

    private final PageViewService pageViewService;

    @GetMapping("{code}")
    public PageViewResponse findByCode(@PathVariable String code) {
        return pageViewService.findByCode(code);
    }

    @GetMapping("{code}/data")
    public Page<JsonNode> getPageViewData(@PathVariable String code,
                                          @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return pageViewService.getPageViewData(code, pageable);
    }
}
