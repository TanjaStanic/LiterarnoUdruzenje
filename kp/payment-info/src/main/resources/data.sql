insert into roles (name) values ("ADMIN");
insert into roles (name) values ("CLIENT");

insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date, role_id, token)
 values ("123", "123", "test@gmail.com", "Pero Peric", "$2a$10$hU3/RmxoPdJhSSrAdfNhlepQI0Y/sx78rrprOI4Twlw6BjiCMc1gq", true, true, null, 2, null);

 insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date, role_id, token)
  values ("12345", "12345", "sb-zx3ys4123984@business.example.com", "Mico Micic", "$2a$10$hU3/RmxoPdJhSSrAdfNhlepQI0Y/sx78rrprOI4Twlw6BjiCMc1gq", true, true, null, 2, null);

insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date, role_id, token)
 values ("1234567", "1234567", "test2@gmail.com", "Zika Zikic", "$2a$10$hU3/RmxoPdJhSSrAdfNhlepQI0Y/sx78rrprOI4Twlw6BjiCMc1gq", true, true, null, 2, null);

insert into payment_method (name, subscription_supported, application_name) values ("Bank", false , "bank-ms");
insert into payment_method (name, subscription_supported, application_name) values ("Paypal", true, "paypal-ms");
insert into payment_method (name, subscription_supported, application_name) values ("Bitcoin", false, "bitcoin-ms");

insert into client_payment_methods (client_id, payment_methods_id) values (1,1);
insert into client_payment_methods (client_id, payment_methods_id) values (1,2);
insert into client_payment_methods (client_id, payment_methods_id) values (1,3);

insert into client_payment_methods (client_id, payment_methods_id) values (2,1);
insert into client_payment_methods (client_id, payment_methods_id) values (2,2);
insert into client_payment_methods (client_id, payment_methods_id) values (2,3);

insert into currency (`code`, `name`)  values ('RSD', 'Serbian dinar');
insert into currency (`code`, `name`)  values ('EUR', 'Euro');
insert into currency (`code`, `name`)  values ('BAM', 'Convertible mark');
insert into currency (`code`, `name`)  values ('USD', 'United States dollar');

