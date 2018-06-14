-- auto-generated definition
create table "user"
(
    uuid         varchar(36) not null
        constraint user_pkey
        primary key,
    username     text,
    email        text,
    date_created timestamp default now()
);

create unique index user_username_uindex
    on "user" (username);

create unique index user_email_uindex
    on "user" (email);

