alter table invoice
add medical_examination_id uuid;
alter table invoice
add constraint invoice_fk3 foreign key (medical_examination_id) references medical_examination (id);