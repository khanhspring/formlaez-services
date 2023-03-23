CREATE TABLE sign_up_request
(
    id                 bigserial                              NOT NULL,

    email              varchar(250)                           NOT NULL,
    verification_code  varchar(25)                            NOT NULL,

    password           varchar(250) DEFAULT NULL,
    first_name         varchar(250) DEFAULT NULL,
    last_name          varchar(250) DEFAULT NULL,
    expire_date        timestamp                              NOT NULL,
    status             varchar(30)  DEFAULT NULL,

    created_date       timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         varchar(36)  DEFAULT NULL,
    last_modified_date timestamp    DEFAULT CURRENT_TIMESTAMP NULL,
    last_modified_by   varchar(36)  DEFAULT NULL,
    version            int,
    PRIMARY KEY (id),
    UNIQUE (verification_code)
);
