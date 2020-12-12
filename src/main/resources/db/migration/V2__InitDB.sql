create table if not exists manager (
	id uuid not null primary key,
	full_name varchar(128) not null unique,
	email varchar(254) not null unique,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	app_user_id uuid not null unique,
	constraint manager_fk1 foreign key (app_user_id) references app_user (id)
);
create table if not exists staff (
	id uuid not null primary key,
	full_name varchar(128) not null unique,
	email varchar(254) not null unique,
	phone varchar(10) not null,
	address text,
	date_of_birth date,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	app_user_id uuid not null unique,
	constraint staff_fk1 foreign key (app_user_id) references app_user (id)
);
create table if not exists patient (
	id uuid not null primary key,
	patient_name varchar(256),
	patient_name_search varchar(256),
	patient_code varchar(11) not null unique,
	date_of_birth date,
	gender int,
	address varchar(256),
	address_search varchar(256),
	phone varchar(10) unique,
	debt bigint,
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
create table if not exists supplier (
	id uuid not null primary key,
	supplier_name varchar(256),
	address varchar(256),
	phone varchar(10) unique,
	email varchar(254) unique,
	account_number varchar(30),
	debt bigint,
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
create table if not exists voucher_type (
	id uuid not null primary key,
	type_name varchar(128) not null unique,
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
create table if not exists payment_voucher (
	id uuid not null primary key,
	date date,
	amount bigint,
	description text,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	voucher_type_id uuid,
	staff_id uuid,
	patient_id uuid,
	supplier_id uuid,
	constraint payment_voucher_fk1 foreign key (voucher_type_id) references voucher_type (id),
	constraint payment_voucher_fk2 foreign key (staff_id) references staff (id),
	constraint payment_voucher_fk3 foreign key (patient_id) references patient (id),
	constraint payment_voucher_fk4 foreign key (supplier_id) references supplier (id)
);
create table if not exists receipt_voucher (
	id uuid not null primary key,
	date date,
	amount bigint,
	description text,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	voucher_type_id uuid,
	staff_id uuid,
	patient_id uuid,
	supplier_id uuid,
	constraint receipt_voucher_fk1 foreign key (voucher_type_id) references voucher_type (id),
	constraint receipt_voucher_fk2 foreign key (staff_id) references staff (id),
	constraint receipt_voucher_fk3 foreign key (patient_id) references patient (id),
	constraint receipt_voucher_fk4 foreign key (supplier_id) references supplier (id)
);
create table if not exists debt_payment_slip (
	id uuid not null primary key,
	date date,
	amount bigint,
	note text,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	staff_id uuid,
	patient_id uuid,
	supplier_id uuid,
	constraint debt_payment_slip_fk1 foreign key (staff_id) references staff (id),
	constraint debt_payment_slip_fk2 foreign key (patient_id) references patient (id),
	constraint debt_payment_slip_fk3 foreign key (supplier_id) references supplier (id)
);
create table if not exists group_template_report (
	id uuid not null primary key,
	group_template_name varchar(128),
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
create table if not exists template_report (
	id uuid not null primary key,
	template_name varchar(128),
	html_report text,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	staff_id uuid,
	group_template_report_id uuid,
	constraint template_report_fk1 foreign key (staff_id) references staff (id),
	constraint template_report_fk2 foreign key (group_template_report_id) references group_template_report (id)
);
create table if not exists group_service (
	id uuid not null primary key,
	group_service_name varchar(128),
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
create table if not exists service (
	id uuid not null primary key,
	service_name varchar(128),
	unit_name varchar(128),
	price bigint,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	group_service_id uuid,
	staff_id uuid,
	constraint service_fk1 foreign key (group_service_id) references group_service (id),
	constraint service_fk2 foreign key (staff_id) references staff (id)
);
create table if not exists room_service (
	id uuid not null primary key,
	room_name varchar(128),
	unit_name varchar(128),
	total_receive smallint,
	total_done smallint,
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
create table if not exists service_room_service (
	service_id uuid not null,
	room_service_id uuid not null,
	primary key (service_id, room_service_id),
	constraint service_room_service_fk1 foreign key (service_id) references service (id),
	constraint service_room_service_fk2 foreign key (room_service_id) references room_service (id)
);
create table if not exists staff_room_service (
	staff_id uuid not null,
	room_service_id uuid not null,
	primary key (staff_id, room_service_id),
	constraint staff_room_service_fk1 foreign key (staff_id) references staff (id),
	constraint staff_room_service_fk2 foreign key (room_service_id) references room_service (id)
);
create table if not exists ordinal_number (
	id uuid not null primary key,
	day_of_examination date,
	ordinal_number smallint,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	room_service_id uuid unique,
	constraint ordinal_number_fk1 foreign key (room_service_id) references room_service (id)
);
create table if not exists receive_patient (
	id uuid not null primary key,
	examination_reason text,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	ordinal_number_id uuid unique,
	patient_id uuid,
	staff_id uuid,
	constraint receive_patient_fk1 foreign key (ordinal_number_id) references ordinal_number (id),
	constraint receive_patient_fk2 foreign key (patient_id) references patient (id),
	constraint receive_patient_fk3 foreign key (staff_id) references staff (id)
);
create table if not exists appointment (
	id uuid not null primary key,
	day_of_examination date,
	time varchar(5),
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	ordinal_number_id uuid unique,
	patient_id uuid,
	staff_id uuid,
	constraint appointment_fk1 foreign key (ordinal_number_id) references ordinal_number (id),
	constraint appointment_fk2 foreign key (patient_id) references patient (id),
	constraint appointment_fk3 foreign key (staff_id) references staff (id)
);
create table if not exists group_material (
	id uuid not null primary key,
	group_material_name varchar(128),
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
create table if not exists material (
	id uuid not null primary key,
	material_name varchar(256),
	unit_name varchar(128),
	quantity int,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	group_material_id uuid,
	constraint material_fk1 foreign key (group_material_id) references group_material (id)
);
create table if not exists group_medicine (
	id uuid not null primary key,
	group_medicine_name varchar(128),
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
create table if not exists medicine (
	id uuid not null primary key,
	medicine_name varchar(256),
	price bigint,
	unit_name varchar(128),
	quantity smallint,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	group_medicine_id uuid,
	constraint medicine_fk1 foreign key (group_medicine_id) references group_medicine (id)
);
create table if not exists prescription (
	id uuid not null primary key,
	note text,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	staff_id uuid,
	medical_examination_id uuid unique,
	constraint prescription_fk1 foreign key (staff_id) references staff (id)
);
create table if not exists prescription_detail (
	id uuid not null primary key,
	quantity smallint,
	dosage text,
	note text,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	prescription_id uuid,
	medicine_id uuid,
	constraint prescription_detail_fk1 foreign key (prescription_id) references prescription (id),
	constraint prescription_detail_fk2 foreign key (medicine_id) references medicine (id)
);
create table if not exists medicine_sale (
	id uuid not null primary key,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	prescription_id uuid,
	constraint medicine_sale_fk1 foreign key (prescription_id) references prescription (id)
);
create table if not exists medicine_sale_detail (
	id uuid not null primary key,
	quantity smallint,
	quantity_taken smallint,
	amount bigint,
	type int,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	medicine_sale_id uuid,
	medicine_id uuid,
	constraint medicine_sale_detail_fk1 foreign key (medicine_sale_id) references medicine_sale (id),
	constraint medicine_sale_detail_fk2 foreign key (medicine_id) references medicine (id)
);
create table if not exists invoice (
	id uuid not null primary key,
	total_amount bigint,
	amount_paid bigint,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	patient_id uuid,
	staff_id uuid,
	constraint invoice_fk1 foreign key (patient_id) references patient (id),
	constraint invoice_fk2 foreign key (staff_id) references staff (id)
);
create table if not exists invoice_detailed (
	id uuid not null primary key,
	quantity smallint,
	amount bigint,
	amount_paid bigint,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	invoice_id uuid,
	service_id uuid,
	medicine_sale_id uuid unique,
	constraint invoice_detailed_fk1 foreign key (invoice_id) references invoice (id),
	constraint invoice_detailed_fk2 foreign key (service_id) references service (id),
	constraint invoice_detailed_fk3 foreign key (medicine_sale_id) references medicine_sale (id)
);
create table if not exists receipt (
	id uuid not null primary key,
	total_amount bigint,
	amount_paid bigint,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	supplier_id uuid,
	staff_id uuid,
	constraint receipt_fk1 foreign key (supplier_id) references supplier (id),
	constraint receipt_fk2 foreign key (staff_id) references staff (id)
);
create table if not exists receipt_detail (
	id uuid not null primary key,
	quantity smallint,
	amount bigint,
	amount_paid bigint,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	receipt_id uuid,
	medicine_id uuid,
	material_id uuid,
	constraint receipt_detail_fk1 foreign key (receipt_id) references receipt (id),
	constraint receipt_detail_fk2 foreign key (medicine_id) references medicine (id),
	constraint receipt_detail_fk3 foreign key (material_id) references material (id)
);
create table if not exists disease (
	id uuid not null primary key,
	icd10_code varchar(10),
	disease_name varchar(256),
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
create table if not exists medical_examination (
	id uuid not null primary key,
	medical_examination_code varchar(10) not null unique,
	examination_reason text,
	blood_vessel varchar(128),
	blood_pressure varchar(128),
	breathing varchar(128),
	temperature varchar(128),
	height varchar(128),
	weight varchar(128),
	symptom varchar(128),
	summary varchar(128),
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	main_disease_id uuid unique,
	extra_disease_id uuid unique,
	staff_id uuid,
	patient_id uuid,
	constraint medical_examination_fk1 foreign key (main_disease_id) references disease (id),
	constraint medical_examination_fk2 foreign key (extra_disease_id) references disease (id),
	constraint medical_examination_fk3 foreign key (staff_id) references staff (id),
	constraint medical_examination_fk4 foreign key (patient_id) references patient (id)
);
create table if not exists service_report (
	id uuid not null primary key,
	result text,
	html_report text,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	service_id uuid,
	staff_id uuid,
	medical_examination_id uuid unique,
	constraint service_report_fk1 foreign key (service_id) references service (id),
	constraint service_report_fk2 foreign key (staff_id) references staff (id),
	constraint service_report_fk3 foreign key (medical_examination_id) references medical_examination (id)
);
create table if not exists image (
	id uuid not null primary key,
	image_path text,
	status smallint,
	created_at timestamp,
	updated_at timestamp,
	service_report_id uuid,
	constraint image_fk1 foreign key (service_report_id) references service_report (id)
);
alter table prescription
add constraint prescription_fk2 foreign key (medical_examination_id) references medical_examination (id);