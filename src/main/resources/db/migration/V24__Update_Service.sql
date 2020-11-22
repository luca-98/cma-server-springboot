alter table service
add template_report_id uuid;

alter table service
add constraint service_fk3 foreign key (template_report_id) references template_report (id);