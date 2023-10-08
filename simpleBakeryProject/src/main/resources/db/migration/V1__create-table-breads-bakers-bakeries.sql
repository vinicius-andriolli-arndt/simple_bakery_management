CREATE TABLE bakers
(
    id          UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    birth_date  DATE         NOT NULL,
    nationality VARCHAR(255),
    CONSTRAINT pk_authors PRIMARY KEY (id)
);

CREATE TABLE breads
(
    id               UUID         NOT NULL,
    ifs             VARCHAR(13)  NOT NULL,
    flavor            VARCHAR(255) NOT NULL,
    type            VARCHAR(255) NOT NULL,
    publication_year INTEGER      NOT NULL,
    quantity         INTEGER      NOT NULL,
    baker_id        UUID         NOT NULL,
    bakery_id     UUID         NOT NULL,
    CONSTRAINT pk_breads PRIMARY KEY (id)
);

CREATE TABLE bakeries
(
    id           UUID         NOT NULL,
    name         VARCHAR(255) NOT NULL,
    address      VARCHAR(255),
    phone_number VARCHAR(16)  NOT NULL,
    CONSTRAINT pk_bakeries PRIMARY KEY (id)
);

ALTER TABLE breads
    ADD CONSTRAINT uc_breads_ifs UNIQUE (ifs);

ALTER TABLE bakeries
    ADD CONSTRAINT uc_bakeries_name UNIQUE (name);

ALTER TABLE breads
    ADD CONSTRAINT FK_BREADS_ON_BAKER FOREIGN KEY (author_id) REFERENCES bakers (id);

ALTER TABLE breads
    ADD CONSTRAINT FK_BREADS_ON_BAKERY FOREIGN KEY (publisher_id) REFERENCES bakeries (id);