alter table receive_patient 
drop column examination_reason;

alter table receive_patient 
add medical_examination_id uuid;

alter table receive_patient 
add constraint receive_patient_fk4 foreign key (medical_examination_id) references medical_examination (id);