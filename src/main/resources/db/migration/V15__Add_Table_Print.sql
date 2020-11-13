create table if not exists print_form (
	id uuid not null primary key,
	print_name varchar(128) not null unique,
	print_code varchar(20) not null,
	template_html text,
	status smallint,
	created_at timestamp,
	updated_at timestamp
);