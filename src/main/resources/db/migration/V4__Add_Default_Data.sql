alter table service
add is_clinical_examination boolean default false;
insert into service (
        id,
        service_name,
        unit_name,
        price,
        status,
        created_at,
        updated_at,
        group_service_id,
        staff_id,
        is_clinical_examination
    )
values (
        '97baf3b4-fb47-47f6-99d3-4987cd7db3f9',
        'Khám lâm sàng',
        'lần',
        0,
        1,
        now(),
        now(),
        null,
        null,
        true
    );