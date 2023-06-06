package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.pageview.CreatePageViewRequest;
import com.formlaez.application.model.request.pageview.UpdatePageViewRequest;
import com.formlaez.application.model.response.pageview.PageViewResponse;
import com.formlaez.service.admin.pageview.PageViewAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
public class PageViewAdminController {
    private final PageViewAdminService pageViewAdminService;

    @GetMapping("forms/{formId}/page-views")
    public List<PageViewResponse> findByForm(@PathVariable Long formId) {
        return pageViewAdminService.findAllByForm(formId);
    }

    @PostMapping("forms/{formId}/page-views")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@PathVariable Long formId, @RequestBody @Valid CreatePageViewRequest request) {
        request.setFormId(formId);
        return pageViewAdminService.create(request);
    }

    @PutMapping("page-views/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid UpdatePageViewRequest request) {
        request.setId(id);
        pageViewAdminService.update(request);
    }

    @PostMapping("page-views/{id}/publish")
    public void publish(@PathVariable Long id) {
        pageViewAdminService.publish(id);
    }

    @PostMapping("page-views/{id}/unpublish")
    public void unPublish(@PathVariable Long id) {
        pageViewAdminService.unPublish(id);
    }

    @DeleteMapping("page-views/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        pageViewAdminService.delete(id);
    }
}
