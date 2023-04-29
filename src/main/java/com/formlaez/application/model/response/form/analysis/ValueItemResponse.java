package com.formlaez.application.model.response.form.analysis;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValueItemResponse<V> {
    private V value;
    private long count;
}
