set time zone + 7;
create extension if not exists "uuid-ossp";
create table if not exists user_group (
  id uuid not null primary key,
  user_group_code varchar(128) not null unique,
  user_group_name varchar(128) not null unique,
  status smallint,
  created_at timestamp,
  updated_at timestamp
);
create table if not exists permission (
  id uuid not null primary key,
  permission_code varchar(128) not null unique,
  permission_name varchar(128) not null unique,
  status smallint,
  created_at timestamp,
  updated_at timestamp
);
create table if not exists user_group_permission (
  user_group_id uuid not null,
  permission_id uuid not null,
  primary key (user_group_id, permission_id),
  constraint user_group_permission_fk1 foreign key (user_group_id) references user_group (id),
  constraint user_group_permission_fk2 foreign key (permission_id) references permission (id)
);
create table if not exists app_user (
  id uuid not null primary key,
  user_name varchar(36) not null unique,
  encrypted_password varchar(128) not null,
  user_group_id uuid not null,
  status smallint,
  created_at timestamp,
  updated_at timestamp,
  constraint app_user_fk1 foreign key (user_group_id) references user_group (id)
);
insert into user_group (
    id,
    user_group_code,
    user_group_name,
    status,
    created_at,
    updated_at
  )
values (
    'fcd1e9a9-f511-4c8e-a7da-54f1d4b88dfd',
    'ROLE_MANAGER',
    'Manager',
    1,
    now(),
    now()
  );
insert into app_user (
    id,
    user_name,
    encrypted_password,
    user_group_id,
    status,
    created_at,
    updated_at
  )
values (
    '87a5d03f-e810-42cf-8ef9-5c92a5302790',
    'test',
    '$2a$10$zDqt9loEIebbgOSmEdVdiODlgj.ntPTQJ32xpZL3TGRLoOYww.3Rq',
    'fcd1e9a9-f511-4c8e-a7da-54f1d4b88dfd',
    1,
    now(),
    now()
  );
--username: test
--password: 12345678