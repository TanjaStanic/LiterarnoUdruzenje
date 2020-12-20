insert into client(active,email,merchantid, merchant_password,name,account_id) 
	values (true,'test@gmail.com',"merchantid","merchantpass","Literarno Udruzenje 1",1);
	
insert into client (active,email,merchantid, merchant_password,name,account_id) 
	values (true,'test2@gmail.com',"merchantid2","merchantpass2","Literarno Udruzenje 2",2);

insert into payment_method (id, name, create_transactionuri, check_statusuri, is_bank, isPayPal, isBitcoin) values (1, 'Bank', https://localhost:8762/bank-ms/api/pay, null, true, false, false);
insert into payment_method (id, name, create_transactionuri, check_statusuri, is_bank, isPayPal, isBitcoin) values (2, 'PayPal', 'https://localhost:8762/paypal-ms/api/pay', 'https://localhost:8762/paypal_service/api/status', false, true, false);
insert into payment_method (id, name, create_transactionuri, check_statusuri, is_bank, isPayPal, isBitcoin) values (3, 'Bitcoin', 'https://localhost:8762/bitcoin-ms/api/order', 'https://localhost:8762/bitcoin_service/api/order/status', false, false, true);
	
insert into client_payment_methods(client_id, payment_methods_id) VALUES (1, 1);
insert into client_payment_methods(client_id, payment_methods_id) VALUES (1, 3);
insert into client_payment_methods(client_id, payment_methods_id) VALUES (2, 1);
insert into client_payment_methods(client_id, payment_methods_id) VALUES (2, 2);
insert into client_payment_methods(client_id, payment_methods_id) VALUES (2, 3);