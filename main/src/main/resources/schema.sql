CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email VARCHAR(511)                            NOT NULL,
    name  VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(511),
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation        VARCHAR(2001)                           NOT NULL,
    category_id       BIGINT                                  NOT NULL,
    description       VARCHAR(7000)                           NOT NULL,
    eventDate         TIMESTAMP                               NOT NULL,
    initator_id       BIGINT                                  NOT NULL,
    lat               NUMERIC                                 NOT NULL,
    lon               NUMERIC                                 NOT NULL,
    paid              BOOLEAN                                 NOT NULL,
    participantLimit  INTEGER                                 NOT NULL,
    requestModeration BOOLEAN                                 NOT NULL,
    title             VARCHAR(120)                            NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_event_on_category FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_event_on_user FOREIGN KEY (initator_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN                                 NOT NULL,
    title  VARCHAR(511),
    CONSTRAINT pk_compilation PRIMARY KEY (id),
    CONSTRAINT fk_compilation_on_events FOREIGN KEY (id) REFERENCES events (id)
);
