package com.formlaez.application.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserRequest {
    private String keyword;
    private String email;
}
