CREATE TABLE form
(
    id                 bigserial                             NOT NULL,
    code               varchar(30)                           NOT NULL,
    title              varchar(1000)                         NOT NULL,
    description        varchar(1000) NULL,

    scope              varchar(30)                           NOT NULL,

    cover_type         varchar(30)                           NOT NULL,
    cover_color        varchar(250) NULL,
    cover_image_url    varchar(1000) NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code)
);

CREATE TABLE form_page
(
    id                 bigserial                             NOT NULL,
    form_id            bigint                                NOT NULL,
    code               varchar(30)                           NOT NULL,
    title              varchar(1000)                         NOT NULL,
    description        varchar(1000) NULL,

    position           int                                   NOT NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code),
    UNIQUE (form_id, position) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT fk_form_page_form_id FOREIGN KEY (form_id) REFERENCES form (id)
);

CREATE TABLE form_section
(
    id                  bigserial     NOT NULL,
    form_page_id        bigint        NOT NULL,

    code                varchar(30)   NOT NULL,
    title               varchar(1000) NOT NULL,
    description         varchar(1000) NULL,

    variable_name       varchar(250)  NOT NULL,
    type                varchar(50)   NOT NULL,

    repeatable          boolean       NOT NULL DEFAULT false,
    repeat_button_label varchar(250) NULL,

    position            int           NOT NULL,

    created_date        timestamp              DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by          varchar(36)            DEFAULT NULL,
    last_modified_date  timestamp              DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by    varchar(36)            DEFAULT NULL,
    version             int,
    PRIMARY KEY (id),
    UNIQUE (code),
    UNIQUE (form_page_id, position) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT fk_form_section_form_page_id FOREIGN KEY (form_page_id) REFERENCES form_page (id)
);

CREATE TABLE form_field
(
    id                 bigserial     NOT NULL,
    form_section_id    bigint        NOT NULL,

    code               varchar(30)   NOT NULL,
    title              varchar(1000) NOT NULL,
    description        varchar(1000) NULL,

    variable_name      varchar(250)  NOT NULL,
    type               varchar(50)   NOT NULL,

    required           boolean       NOT NULL DEFAULT false,
    placeholder        varchar(1000) NULL,
    content            text NULL,

    hide_title         boolean       NOT NULL DEFAULT false,
    url                varchar(1000) NULL,
    caption            varchar(1000) NULL,
    accepted_domains   varchar(1000) NULL,

    min_length         int NULL,
    max_length         int NULL,
    min                int NULL,
    max                int NULL,

    readonly           boolean       NOT NULL DEFAULT false,
    multiple_selection boolean       NOT NULL DEFAULT false,
    show_time          boolean       NOT NULL DEFAULT false,

    position           int           NOT NULL,

    created_date       timestamp              DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36)            DEFAULT NULL,
    last_modified_date timestamp              DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36)            DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code),
    UNIQUE (form_section_id, position) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT fk_form_field_form_section_id FOREIGN KEY (form_section_id) REFERENCES form_section (id)
);

CREATE TABLE form_field_option
(
    id                 bigserial                             NOT NULL,
    form_field_id      bigint                                NOT NULL,

    code               varchar(30)                           NOT NULL,
    label              varchar(1000)                         NOT NULL,

    metadata           jsonb NULL,

    position           int                                   NOT NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code),
    UNIQUE (form_field_id, position) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT fk_field_option_form_field_id FOREIGN KEY (form_field_id) REFERENCES form_field (id)
);

CREATE TABLE form_submission
(
    id                 bigserial                             NOT NULL,
    code               varchar(30)                           NOT NULL,
    form_id            bigint                                NOT NULL,

    data               jsonb NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code),
    CONSTRAINT fk_form_submission_form_id FOREIGN KEY (form_id) REFERENCES form (id)
);

CREATE TABLE form_submission_snapshot
(
    id                 bigserial                             NOT NULL,
    code               varchar(30)                           NOT NULL,
    form_id            bigint                                NOT NULL,
    form_submission_id bigint                                NOT NULL,

    data               jsonb NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code),
    CONSTRAINT fk_form_submission_snapshot_form_id FOREIGN KEY (form_id) REFERENCES form (id),
    CONSTRAINT fk_form_submission_snapshot_form_submission_id FOREIGN KEY (form_submission_id) REFERENCES form_submission (id)
);

CREATE TABLE form_document_template
(
    id                 bigserial                             NOT NULL,
    form_id            bigint                                NOT NULL,
    attachment_id      bigint                                NOT NULL,

    code               varchar(30)                           NOT NULL,
    title              varchar(1000)                         NOT NULL,
    description        varchar(1000)                         NOT NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code),
    CONSTRAINT fk_form_document_template_form_id FOREIGN KEY (form_id) REFERENCES form (id),
    CONSTRAINT fk_form_document_template_attachment_id FOREIGN KEY (attachment_id) REFERENCES attachment (id)
);