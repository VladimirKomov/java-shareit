-- DROP TABLE bookings CASCADE;
-- DROP TABLE items CASCADE;
-- DROP TABLE requests CASCADE;
-- DROP TABLE users CASCADE;
-- DROP TABLE comments cascade;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255),
    email VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uc_users_email UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT NOT NULL,
    description  VARCHAR(255),
    requestor_id BIGINT,
    created_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_requests PRIMARY KEY (id),
    CONSTRAINT FK_REQUESTS_ON_REQUESTOR FOREIGN KEY (requestor_id) REFERENCES users (id)
);


CREATE TABLE IF NOT EXISTS items
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    available   BOOLEAN,
    owner_id    BIGINT,
    request_id  BIGINT,
    CONSTRAINT pk_items PRIMARY KEY (id),
    CONSTRAINT FK_ITEMS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id) on DELETE cascade,
    CONSTRAINT FK_ITEMS_ON_REQUEST FOREIGN KEY (request_id) REFERENCES requests (id) on DELETE cascade
);

CREATE TABLE IF NOT EXISTS bookings
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_time TIMESTAMP WITHOUT TIME ZONE,
    end_time   TIMESTAMP WITHOUT TIME ZONE,
    item_id    BIGINT,
    booker_id  BIGINT,
    status     VARCHAR(255),
    CONSTRAINT pk_bookings PRIMARY KEY (id),
    CONSTRAINT FK_BOOKINGS_ON_ITEM FOREIGN KEY (item_id) REFERENCES items (id) on DELETE cascade,
    CONSTRAINT FK_BOOKINGS_ON_USER FOREIGN KEY (booker_id) REFERENCES users (id) on DELETE cascade
);

CREATE TABLE IF NOT EXISTS comments
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text      VARCHAR(255),
    item_id   BIGINT,
    author_id BIGINT,
    created   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_comments PRIMARY KEY (id),
    CONSTRAINT FK_COMMENTS_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id) on DELETE cascade,
    CONSTRAINT FK_COMMENTS_ON_ITEM FOREIGN KEY (item_id) REFERENCES items (id) on DELETE cascade
);




