CREATE TABLE workspace_usage
(
    id                 bigserial NOT NULL,
    workspace_id       bigint    NOT NULL,

    total_form         int       NOT NULL DEFAULT 0,
    total_file_storage int       NOT NULL DEFAULT 0,
    total_member       int       NOT NULL DEFAULT 0,

    created_date       timestamp          DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36)        DEFAULT NULL,
    last_modified_date timestamp          DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36)        DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (workspace_id),
    CONSTRAINT fk_workspace_usage_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspace (id)
);

CREATE TABLE workspace_monthly_usage
(
    id                   bigserial NOT NULL,
    workspace_id         bigint    NOT NULL,

    month                int       NOT NULL, -- 202303
    total_submission     int       NOT NULL DEFAULT 0,
    total_document_merge int       NOT NULL DEFAULT 0,

    created_date         timestamp          DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by           varchar(36)        DEFAULT NULL,
    last_modified_date   timestamp          DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by     varchar(36)        DEFAULT NULL,
    version              int,
    PRIMARY KEY (id),
    UNIQUE (workspace_id, month),
    CONSTRAINT fk_workspace_usage_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspace (id)
);
