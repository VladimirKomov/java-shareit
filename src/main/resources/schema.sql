-- CREATE TABLE IF NOT EXISTS users
-- (
--     id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--     name  VARCHAR(255)                            NOT NULL,
--     email VARCHAR(512)                            NOT NULL,
--     CONSTRAINT pk_user PRIMARY KEY (id),
--     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
-- );
--
-- CREATE TABLE IF NOT EXISTS items
-- (
--     id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--     name        VARCHAR(255),
--     description VARCHAR(4000),
--     available   BOOLEAN,
--     owner_id    BIGINT                                  NOT NULL,
--     request_id  BIGINT,
--     CONSTRAINT pk_item PRIMARY KEY (id),
--     CONSTRAINT FK_ITEM_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id),
--    -- CONSTRAINT FK_ITEM_ON_REQUEST FOREIGN KEY (request_id) REFERENCES requests (id),
--     CONSTRAINT UQ_OWNER_ITEM_NAME UNIQUE (owner_id, name)
-- );

create table if not exists users
(
    id int not null primary key auto_increment,
    name varchar(255) not null,
    email varchar(255) not null,
    UNIQUE (email)
);

create table if not exists items
(
    id int not null primary key auto_increment,
    name varchar(255),
    description varchar(500),
    available   boolean,
    owner_id    int references users (id) on DELETE cascade,
    request_id  varchar(255)
)