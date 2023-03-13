package com.formlaez.application.model.response.form;

import com.formlaez.infrastructure.enumeration.FormFieldType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormFieldResponse {
    private Long id;
    private String code;
    private String title;
    private String description;
    private String variableName;

    private FormFieldType type;

    private boolean required;
    private String placeholder;
    private String content;
    private boolean hideTitle;
    private String url;
    private String caption;
    private String acceptedDomains;

    private Integer minLength;
    private Integer maxLength;

    private BigDecimal min;
    private BigDecimal max;

    private boolean readonly;
    private boolean multipleSelection;
    private boolean showTime;

    private int position;

    private List<FormFieldOptionResponse> options;
}
