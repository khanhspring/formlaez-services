CREATE TABLE page_view_template
(
    id                 bigserial                             NOT NULL,

    code               varchar(50)                           NOT NULL,
    name               varchar(250)                          NOT NULL,
    description        varchar(1000) NULL,
    example_url        varchar(500) NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code)
);

CREATE TABLE page_view
(
    id                 bigserial                             NOT NULL,

    template_id        bigint                                NOT NULL,
    form_id            bigint                                NOT NULL,
    code               varchar(50)                           NOT NULL,
    status             varchar(20)                           NOT NULL,
    title              varchar(250)                          NOT NULL,
    description        varchar(1000) NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code),
    CONSTRAINT fk_page_view_template_id FOREIGN KEY (template_id) REFERENCES page_view_template (id)
);

CREATE TABLE page_view_listing_field
(
    id                 bigserial                             NOT NULL,

    page_view_id       bigint                                NOT NULL,
    type               varchar(50)                           NOT NULL,
    field_code         varchar(50)                           NOT NULL,
    target_field_code  varchar(50) NULL,
    fixed_value        varchar(2000) NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (page_view_id, field_code),
    CONSTRAINT fk_page_view_listing_field_page_view_id FOREIGN KEY (page_view_id) REFERENCES page_view (id)
);

CREATE TABLE page_view_detail
(
    id                 bigserial                             NOT NULL,

    page_view_id       bigint                                NOT NULL,
    type               varchar(50)                           NOT NULL,

    custom_content     text NULL,
    redirect_url       varchar(500) NULL,
    redirect_type      varchar(20) NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    CONSTRAINT fk_page_view_detail_page_view_id FOREIGN KEY (page_view_id) REFERENCES page_view (id)
);