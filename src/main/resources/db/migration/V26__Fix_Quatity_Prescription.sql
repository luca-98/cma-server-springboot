alter table prescription
drop column quantity_taken;

alter table prescription_detail
add quantity_taken smallint;