alter table debt_payment_slip
add voucher_type_id uuid;
alter table debt_payment_slip
add constraint debt_payment_slip_fk4 foreign key (voucher_type_id) references voucher_type (id);