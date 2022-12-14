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
    name VARCHAR(511) UNIQUE,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000),
    category_id        BIGINT                                  NOT NULL,
    description        VARCHAR(7000),
    event_date         TIMESTAMP,
    create_on          TIMESTAMP,
    published_on       TIMESTAMP,
    initiator_id       BIGINT                                  NOT NULL,
    lat                NUMERIC,
    lon                NUMERIC,
    paid               BOOLEAN,
    participant_limit  INTEGER,
    request_moderation BOOLEAN,
    state              VARCHAR(10),
    title              VARCHAR(120),
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_event_on_category FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_event_on_user FOREIGN KEY (initiator_id) REFERENCES users (id),
    CONSTRAINT event_state_check
        CHECK (state = 'PENDING' OR state = 'CONFIRMED' OR state = 'PUBLISHED' OR state = 'CANCELED')
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN                                 NOT NULL,
    title  VARCHAR(511),
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id BIGINT NOT NULL,
    event_id       BIGINT NOT NULL,
    CONSTRAINT pk_compilations_events PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT fk_compilations_events_on_compilations FOREIGN KEY (compilation_id) REFERENCES compilations (id),
    CONSTRAINT fk_compilations_events_on_event FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    created      TIMESTAMP,
    status       VARCHAR(10),
    CONSTRAINT pk_requests PRIMARY KEY (id),
    CONSTRAINT fk_request_on_event FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT fk_request_on_requester_user FOREIGN KEY (requester_id) REFERENCES users (id),
    CONSTRAINT requests_status_check
        CHECK (status = 'PENDING' OR status = 'CONFIRMED' OR status = 'REJECTED' OR status = 'CANCELED')
);

CREATE TABLE IF NOT EXISTS comments
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    owner_id     BIGINT                                  NOT NULL,
    text         VARCHAR(1000)                           NOT NULL,
    type_comment VARCHAR(8)                              NOT NULL,
    created      TIMESTAMP                               NOT NULL,
    last_update  TIMESTAMP                               NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id),
    CONSTRAINT fk_comments_on_event FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT fk_comments_on_user FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT comments_check
        CHECK (type_comment = 'POSITIVE' OR type_comment = 'NEUTRAL' OR type_comment = 'NEGATIVE')

);
