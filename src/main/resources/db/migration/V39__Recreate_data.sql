delete from app_user;

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
    'admin',
    '$2a$10$zDqt9loEIebbgOSmEdVdiODlgj.ntPTQJ32xpZL3TGRLoOYww.3Rq',
    'fcd1e9a9-f511-4c8e-a7da-54f1d4b88dfd',
    1,
    now(),
    now()
  );

insert into staff (
    id,
    full_name,
    email,
    phone,
    address,
    date_of_birth,
    status,
    created_at,
    updated_at,
    app_user_id,
    staff_name_search
  )
values (
    '509242f0-9e6b-4c67-8de1-add7685a3646',
    'Tài khoản quản lý',
    'email@email.com',
    '0000000000',
    null,
    null,
    1,
    now(),
    now(),
    '87a5d03f-e810-42cf-8ef9-5c92a5302790',
    'tai khoan quan ly'
  );

insert into group_service_staff values ('7e8257c6-7930-421f-8797-7f1039ecb2c8', '509242f0-9e6b-4c67-8de1-add7685a3646');
