CREATE TABLE "user"
(
    id                 varchar(36)  NOT NULL,
    username           varchar(30)  NOT NULL,
    email              varchar(250) NOT NULL,
    password           varchar(250) DEFAULT NULL,
    status             varchar(30)  NOT NULL,
    first_name         varchar(250) DEFAULT NULL,
    last_name          varchar(250) DEFAULT NULL,

    created_date       timestamp    NOT NULL,
    created_by         varchar(36)  DEFAULT NULL,
    last_modified_date timestamp    DEFAULT NULL,
    last_modified_by   varchar(36)  DEFAULT NULL,
    version            int,
    PRIMARY KEY (id)
);

CREATE TABLE workspace
(
    id                 bigserial                             NOT NULL,
    code               varchar(50)                           NOT NULL,
    name               varchar(200)                          NOT NULL,
    description        varchar(1000) NULL,

    type               varchar(30)                           NOT NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code)
);

CREATE TABLE workspace_member
(
    id                 bigserial                             NOT NULL,
    workspace_id       bigint                                NOT NULL,
    user_id            varchar(36)                           NOT NULL,
    role               varchar(20)                           NOT NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (workspace_id, user_id),
    CONSTRAINT fk_workspace_member_account_id FOREIGN KEY (workspace_id) REFERENCES workspace (id)
);


CREATE TABLE attachment
(
    id                     bigserial                             NOT NULL,
    workspace_id           bigint                                NOT NULL,

    code                   varchar(30)                           NOT NULL,

    name                   varchar(200)                          NOT NULL,
    path                   varchar(1000)                         NOT NULL,
    description            varchar(1000) NULL,
    extension              varchar(30)                           NOT NULL,
    size                   int                                   NOT NULL,
    type                   varchar(30)                           NOT NULL,

    cloud_storage_location varchar(1000)                         NOT NULL,

    created_date           timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by             varchar(36) DEFAULT NULL,
    last_modified_date     timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by       varchar(36) DEFAULT NULL,
    version                int,
    PRIMARY KEY (id),
    UNIQUE (code),
    CONSTRAINT fk_attachment_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspace (id)
);