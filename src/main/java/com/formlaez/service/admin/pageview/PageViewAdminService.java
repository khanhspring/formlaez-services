package com.formlaez.service.admin.pageview;

import com.formlaez.application.model.request.pageview.CreatePageViewRequest;
import com.formlaez.application.model.request.pageview.UpdatePageViewRequest;
import com.formlaez.application.model.response.pageview.PageViewResponse;

import java.util.List;

public interface PageViewAdminService {
    List<PageViewResponse> findAllByForm(Long formId);
    Long create(CreatePageViewRequest request);
    void update(UpdatePageViewRequest request);
    void publish(Long id);
    void unPublish(Long id);
    void delete(Long id);
}
