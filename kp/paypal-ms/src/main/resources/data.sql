insert into paypal_db.currency (`code`, `name`)  values ('RSD', 'Serbian dinar');
insert into paypal_db.currency (`code`, `name`)  values ('EUR', 'Euro');
insert into paypal_db.currency (`code`, `name`)  values ('BAM', 'Convertible mark');
insert into paypal_db.currency (`code`, `name`)  values ('USD', 'United States dollar');

insert into subscription_type (name ) values ("FIXED");
insert into subscription_type (name ) values ("INFINITE");

insert into subscription_frequency(name) values ("MONTH");
insert into subscription_frequency(name) values ("YEAR");

insert  into subscription_plan (cycles_number, frequency_id, seller_id, type_id) values (3, 1, 1, 1);
insert  into subscription_plan (cycles_number, frequency_id, seller_id, type_id) values (6, 1, 1, 1);
insert  into subscription_plan (cycles_number, frequency_id, seller_id, type_id) values (12, 1, 1, 1);
