alter table medicine_sale
add patient_id uuid;

alter table medicine_sale
add constraint medicine_sale_fk3 foreign key (patient_id) references patient (id);