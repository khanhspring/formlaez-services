package com.formlaez.application.model.response.pageview;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageViewTemplateResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String exampleUrl;
}
