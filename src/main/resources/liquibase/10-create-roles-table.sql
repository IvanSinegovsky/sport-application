create table roles
(
    id      bigint auto_increment
        primary key,
    name    varchar(100)                          not null,
    created timestamp   default CURRENT_TIMESTAMP not null,
    updated timestamp   default CURRENT_TIMESTAMP not null,
    status  varchar(25) default 'ACTIVE'          not null,
    constraint name
        unique (name)
);s