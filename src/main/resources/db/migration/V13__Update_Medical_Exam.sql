alter table medical_examination 
drop constraint if exists medical_examination_fk1;

alter table medical_examination 
drop constraint if exists medical_examination_fk2;

alter table medical_examination 
drop column if exists main_disease_id;

alter table medical_examination 
drop column if exists extra_disease_id;

alter table medical_examination 
add main_disease varchar(256);

alter table medical_examination 
add main_disease_code varchar(10);

alter table medical_examination 
add extra_disease varchar(256);

alter table medical_examination 
add extra_disease_code varchar(10);