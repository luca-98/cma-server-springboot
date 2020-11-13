create table if not exists count_id (
	id uuid not null primary key,
	count_name varchar(50) unique,
	count_value smallint,
	status smallint,
	created_at timestamp,
	updated_at timestamp
);
insert into count_id (
		id,
		count_name,
		count_value,
		status,
		created_at,
		updated_at
	)
values (
		'cff56cde-6c6d-48fd-be5b-bc1c7519301b',
		'PATIENT_CODE',
		0,
		1,
		now(),
		now()
	);
insert into count_id (
		id,
		count_name,
		count_value,
		status,
		created_at,
		updated_at
	)
values (
		'423e78af-0e85-4b15-ac27-245c4a3d6873',
		'MEDICAL_EXAMINATION_CODE',
		0,
		1,
		now(),
		now()
	);