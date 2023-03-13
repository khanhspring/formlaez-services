CREATE TABLE team
(
    id                 bigserial                             NOT NULL,
    workspace_id       bigint                                NOT NULL,
    code               varchar(50)                           NOT NULL,
    name               varchar(200)                          NOT NULL,
    description        varchar(1000) NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (code),
    CONSTRAINT fk_team_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspace (id)
);

CREATE TABLE team_member
(
    id                 bigserial                             NOT NULL,
    team_id            bigint                                NOT NULL,
    user_id            varchar(36)                           NOT NULL,
    role               varchar(20)                           NOT NULL,

    created_date       timestamp   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36) DEFAULT NULL,
    last_modified_date timestamp   DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36) DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (team_id, user_id),
    CONSTRAINT fk_team_member_team_id FOREIGN KEY (team_id) REFERENCES team (id)
);