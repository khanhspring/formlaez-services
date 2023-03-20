CREATE TABLE subscription
(
    id                 bigserial                             NOT NULL,
    workspace_id       bigint                                NOT NULL,
    workspace_type     varchar(30)                           NOT NULL,
    status             varchar(30)                           NOT NULL,

    valid_from         timestamp                             NOT NULL,
    valid_till         timestamp   DEFAULT NULL,
    last_renewed_date  timestamp                             NOT NULL,

    subscribed_by      varchar(36) DEFAULT NULL,

    cancel_url         varchar(2000)                         NOT NULL,
    external_id        varchar(50)                           NOT NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    CONSTRAINT fk_subscription_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspace (id)
);
