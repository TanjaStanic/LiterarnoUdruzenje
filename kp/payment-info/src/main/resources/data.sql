insert into roles (name) values ("ROLE_ADMIN");
insert into roles (name) values ("ROLE_CLIENT");

insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date, role_id, token)
 values ("123", "123", "test@gmail.com", "Pero Peric", "$2a$10$hU3/RmxoPdJhSSrAdfNhlepQI0Y/sx78rrprOI4Twlw6BjiCMc1gq", true, true, null, 2, null);

 insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date, role_id, token)
  values ("12345", "12345", "abc4@example.com", "Mico Micic", "$2a$10$hU3/RmxoPdJhSSrAdfNhlepQI0Y/sx78rrprOI4Twlw6BjiCMc1gq", true, true, null, 2, null);

insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date, role_id, token)
 values ("1234567", "1234567", "test2@gmail.com", "Zika Zikic", "$2a$10$hU3/RmxoPdJhSSrAdfNhlepQI0Y/sx78rrprOI4Twlw6BjiCMc1gq", true, true, null, 2, null);

insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date, role_id, token)
 values (null, null, "aaa@aaa.com", "Janko Jankovic", "$2a$10$hU3/RmxoPdJhSSrAdfNhlepQI0Y/sx78rrprOI4Twlw6BjiCMc1gq", true, true, null, 1, null);

insert into payment_method (name, subscription_supported, application_name) values ("Banking", false , "bank-ms");
insert into payment_method (name, subscription_supported, application_name) values ("Paypal", true, "paypal-ms");
insert into payment_method (name, subscription_supported, application_name) values ("Bitcoin", false, "bitcoin-ms");

insert into client_payment_method (fk_client, fk_payment_method) values (1,1);
insert into client_payment_method (fk_client, fk_payment_method) values (1,2);
#insert into client_payment_method (fk_client, fk_payment_method) values (1,3);

insert into client_payment_method (fk_client, fk_payment_method) values (2,1);
insert into client_payment_method (fk_client, fk_payment_method) values (2,2);
insert into client_payment_method (fk_client, fk_payment_method) values (2,3);

insert into currency (`code`, `name`)  values ('RSD', 'Serbian dinar');
insert into currency (`code`, `name`)  values ('EUR', 'Euro');
insert into currency (`code`, `name`)  values ('BAM', 'Convertible mark');
insert into currency (`code`, `name`)  values ('USD', 'United States dollar');

insert into transaction (amount, cancel_url, created, error_url, failed_url, merchant_order_id, paymentid, status, success_url, currency_id, seller_id) values (200, null, "2021-01-28 12:30:00", null, null, 1234254768, null, "CREATED", null, 2, 3);
insert into transaction (amount, cancel_url, created, error_url, failed_url, merchant_order_id, paymentid, status, success_url, currency_id, seller_id) values (300, null, "2021-01-28 11:30:00", null, null, 12342548, null, "CREATED", null, 2, 3);


