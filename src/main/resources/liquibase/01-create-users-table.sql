create table users
(
    id         bigint auto_increment
        primary key,
    username   varchar(100)                          not null,
    email      varchar(255)                          not null,
    first_name varchar(100)                          not null,
    last_name  varchar(100)                          not null,
    password   varchar(255)                          not null,
    created    timestamp   default CURRENT_TIMESTAMP not null,
    updated    timestamp   default CURRENT_TIMESTAMP not null,
    status     varchar(25) default 'ACTIVE'          not null,
    constraint email
        unique (email),
    constraint username
        unique (username)
);