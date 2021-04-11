create table user_roles
(
    user_id bigint null,
    role_id bigint null,
    constraint fk_user_roles_roles
        foreign key (role_id) references roles (id)
            on delete cascade,
    constraint fk_user_roles_user
        foreign key (user_id) references users (id)
            on delete cascade
);