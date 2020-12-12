alter table payment_voucher 
add number_voucher bigint;

alter table receipt_voucher 
add number_voucher bigint;

alter table payment_voucher 
add object_payment text;

alter table receipt_voucher 
add object_receipt text;

alter table medical_examination 
add print_data_html text;