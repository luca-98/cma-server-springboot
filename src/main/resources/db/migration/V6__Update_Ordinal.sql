alter table ordinal_number
add staff_id uuid;
alter table ordinal_number
add constraint ordinal_number_fk2 foreign key (staff_id) references staff (id);