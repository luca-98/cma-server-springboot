alter table medicine_sale
add constraint medicine_sale_fk2 foreign key (staff_id) references staff (id);