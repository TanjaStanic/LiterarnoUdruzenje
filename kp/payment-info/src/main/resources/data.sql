insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date)
 values ("123", "123", "test@gmail.com", "Pero Peric", "blabla", true, true, null);

 insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date)
  values ("12345", "12345", "sb-zx3ys4123984@business.example.com", "Mico Micic", "blabla", true, true, null);

insert into payment_method (name, subscription_supported, application_name) values ("Bank", false , "bank-ms");

insert into payment_method (name, subscription_supported, application_name) values ("Paypal", true, "paypal-ms");

insert into payment_method (name, subscription_supported, application_name) values ("Bitcoin", false, "bitcoin-ms");

insert into client_payment_methods (client_id, payment_methods_id) values (1,1);
insert into client_payment_methods (client_id, payment_methods_id) values (1,2);
insert into client_payment_methods (client_id, payment_methods_id) values (1,3);

insert into client_payment_methods (client_id, payment_methods_id) values (2,1);
insert into client_payment_methods (client_id, payment_methods_id) values (2,2);
insert into client_payment_methods (client_id, payment_methods_id) values (2,3);

