CREATE TABLE form_ending
(
    id                 bigserial NOT NULL,
    form_id            bigint    NOT NULL,

    hide_button        boolean   NOT NULL DEFAULT true,
    content            text NULL,

    created_date       timestamp          DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36)        DEFAULT NULL,
    last_modified_date timestamp          DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36)        DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (form_id),
    CONSTRAINT fk_form_ending_form_id FOREIGN KEY (form_id) REFERENCES form (id)
);
