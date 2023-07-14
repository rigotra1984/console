CREATE TABLE event
(
    id serial not null primary key,
    date timestamp not null,
    priority       VARCHAR(255),
    description   VARCHAR(255)
);