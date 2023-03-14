package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.form.FormEndingResponse;
import com.formlaez.application.model.response.form.FormResponse;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.form.JpaFormEnding;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FormEndingResponseConverter implements Converter<JpaFormEnding, FormEndingResponse> {

    @Nullable
    @Override
    public FormEndingResponse convert(@Nullable JpaFormEnding source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return FormEndingResponse.builder()
                .content(source.getContent())
                .hideButton(source.isHideButton())
                .build();
    }
}
