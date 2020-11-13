alter table group_service
add group_service_code varchar(128);
insert into group_service(
        id,
        group_service_name,
        group_service_code,
        status,
        created_at,
        updated_at
    )
values(
        '7e8257c6-7930-421f-8797-7f1039ecb2c8',
        'Khám lâm sàng',
        'CLINICAL_EXAMINATION',
        1,
        now(),
        now()
    );
alter table service drop column is_clinical_examination;
update service
set group_service_id = '7e8257c6-7930-421f-8797-7f1039ecb2c8'
where id = '97baf3b4-fb47-47f6-99d3-4987cd7db3f9';
create table if not exists group_service_staff (
    group_service_id uuid not null,
    staff_id uuid not null,
    primary key (group_service_id, staff_id),
    constraint group_service_staff_fk1 foreign key (group_service_id) references group_service (id),
    constraint group_service_staff_fk2 foreign key (staff_id) references staff (id)
);