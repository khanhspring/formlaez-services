package com.formlaez.application.model.request.pageview;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdatePageViewRequest {

    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String title;
    private String description;

    @NotNull
    private List<@Valid PageViewListingFieldRequest> listingFields;

    @NotNull
    private @Valid PageViewDetailRequest detail;
}