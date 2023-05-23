CREATE TABLE openai_api_setting
(
    id                 bigserial                             NOT NULL,

    workspace_id       bigint                                NOT NULL,
    api_key            varchar(250)                          NOT NULL,
    model              varchar(50)                           NOT NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (workspace_id),
    CONSTRAINT fk_openai_api_setting_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspace (id)
);
