insert into bank (account_number, address, email, name, phone_number, unique_bank_number) 
	values ('1234123444','ul Svetog Save','bank@email.com','My Bank', '06612365','123412');


insert into client(active,email,merchantid, merchant_password,name,account_id) 
	values (true,'test@gmail.com',"merchantid","merchantpass","Pero Peric",1);
	
insert into client (active,email,merchantid, merchant_password,name,account_id) 
	values (true,'test2@gmail.com',"merchantid2","merchantpass2","Marko Markovic",2);
	
insert into account(account_number,available_funds,owner_id) values ('12346',1000,1);
insert into account(account_number,available_funds,owner_id) values ('12345',200, 2);

insert into card(cvv,expiration_date,pan,account_id) values ('333','03/20','1234-1234-1234-1234',1);
insert into card(cvv,expiration_date,pan,account_id) values ('334','03/20','1234-1234-1234-1235',2);

insert into payment_concentrator_request(amount, merchant_id, merchant_password,merchant_order_id, merchant_timestamp, success_url, failed_url, error_url) 
	values (200, 'merchantid','merchantpass',1, '2016-11-16 06:43:19.77', 'https://www.google.ba/search?q=success', 'https://www.google.com/search?q=faild', 'https://www.google.com/search?q=error');