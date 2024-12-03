create table user_roles (
                            user_id bigint not null,
                            name varchar(255) check (name in ('ROLE_ADMIN','ROLE_USER'))
);
