insert into bank (account_number, address, email, name, phone_number, unique_bank_number) 
	values ('1234123444','ul Svetog Save','bank@email.com','My Bank', '06612365','123412');


insert into client(active,email,merchantid, merchant_password,name,account_id) 
	values (true,'test@gmail.com',"merchantid","merchantpass","Pero Peric",1);
	
insert into client (active,email,merchantid, merchant_password,name,account_id) 
	values (true,'test2@gmail.com',"merchantid2","merchantpass2","Marko Markovic",2);
	
insert into account(account_number,available_funds,owner_id) values ('12346',1000,1);
insert into account(account_number,available_funds,owner_id) values ('12345',1000, 2);

insert into card(cvv,expiration_date,pan,account_id) 
	values ('$2y$12$zrlN2XkxnumVQ4DEXBu3xeGl0YKOMYkzeYL56hV3d9.Gky9xZMX.K','03/20','$2y$12$TsFApOaR4OWZTIa7hPyxWuyEyhAzbDdNxLCIP4FG54turrOky3QEK',1);
insert into card(cvv,expiration_date,pan,account_id) 
	values ('$2y$12$jGE.NW7MOYRpl19vp6SOoOUviur7Z38HsWe7yiE9HAeDNa0LzHcPi','03/20','$2y$12$6l3xBH4XdSwwzk8AzLg.gOpadAg8QUl2oUq.3v4mHQNvzJ3PFcga2',2);

insert into payment_concentrator_request(amount, merchant_id, merchant_password,merchant_order_id, merchant_timestamp, success_url, failed_url, error_url) 
	values (200, 'merchantid','merchantpass',1, '2016-11-16 06:43:19.77', 'https://www.google.ba/search?q=success', 'https://www.google.com/search?q=failed', 'https://www.google.com/search?q=error');