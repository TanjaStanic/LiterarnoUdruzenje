insert into bank (account_number, address, email, name, phone_number, unique_bank_number) 
	values ('1234123444','ul Svetog Save','bank@email.com','My Bank', '06612365','123412');
insert into bank (account_number, address, email, name, phone_number, unique_bank_number) 
	values ('1234133444','ul Nikole Tesle','bank2@email.com','Bank 2', '06612365','123413');

insert into client(active,email,merchantid, merchant_password,name,account_id) 
	values (true,'test@gmail.com',"merchantid","merchantpass","Pero Peric",1);
	
insert into client (active,email,merchantid, merchant_password,name,account_id) 
	values (true,'test2@gmail.com',"merchantid2","merchantpass2","Marko Markovic",2);
	
insert into client (active,email,merchantid, merchant_password,name,account_id) 
	values (true,'test3@gmail.com',"merchantid2","merchantpass2","Jovo Jovic",3);

insert into client (active,email,merchantid, merchant_password,name,account_id)
	values (true,'sb-zx3ys4123984@business.example.com',"vWjFeK3gZm8bAynOIL9wUx3tTZIzPr","4A0wJ33AYM9Foy2UQjKdJPlJuVgQ6mU1KyP4J4Th33A5khqL1Pj9SMLLEIo3YKYzbNoriUJSWyWgvoPM1WDatrS0wSiRAhVB6zr8","Sima Simic",4);

insert into account(account_number,available_funds,owner_id,bank_id,currency_id) 
	values ('$2y$12$gxKH8vqDUFbWiTORXCg9yu5/hyQIcwNZ0TSvIiSzX0/GxJtac8bse',1000,1,1,1);
insert into account(account_number,available_funds,owner_id,bank_id,currency_id) 
	values ('$2y$12$2GZ197S5L7errcGWF3yXfujxb.wtcDRghMJ8hRQNYTkDxMCtvbfWu',1000, 2,1,1);
insert into account(account_number,available_funds,owner_id,bank_id,currency_id) 
	values ('$2y$12$uIhMVO9scNr7rBrvlZjsIuovzW3VnD0TbbIqirhziYM6FjgM.QuDS',1000, 3,2,1);

insert into account(account_number,available_funds,owner_id,bank_id,currency_id)
	values ('$2a$10$yDqJQlLRVdZ/1mZ2wxLeEuw58e.52gTJmNOACY74q1gs6wfNaH.Oy',100, 4,1,1);

insert into card(cvv,expiration_date,pan,account_id) 
	values ('$2y$12$zrlN2XkxnumVQ4DEXBu3xeGl0YKOMYkzeYL56hV3d9.Gky9xZMX.K','03/20','$2y$12$TsFApOaR4OWZTIa7hPyxWuyEyhAzbDdNxLCIP4FG54turrOky3QEK',1);
insert into card(cvv,expiration_date,pan,account_id) 
	values ('$2y$12$jGE.NW7MOYRpl19vp6SOoOUviur7Z38HsWe7yiE9HAeDNa0LzHcPi','03/20','$2y$12$6l3xBH4XdSwwzk8AzLg.gOpadAg8QUl2oUq.3v4mHQNvzJ3PFcga2',2);
insert into card(cvv,expiration_date,pan,account_id) 
	values ('$2y$12$jGE.NW7MOYRpl19vp6SOoOUviur7Z38HsWe7yiE9HAeDNa0LzHcPi','03/20','$2y$12$fEix3zg.Jr/IKrESj/UetekKd.WDVBOAZE2GO7qaG1ty902u56Yxq',3);
insert into card(cvv,expiration_date,pan,account_id)
	values ('$2y$12$jGE.NW7MOYRpl19vp6SOoOUviur7Z38HsWe7yiE9HAeDNa0LzHcPi','03/20','$2y$12$fEix3zg.Jr/IKrESj/UetekKd.WDVBOAZE2GO7qaG1ty902u56Yxq',4);

insert into currency (code,name)  values ('USD', 'United States dollar');

insert into payment_concentrator_request(id,amount, merchant_id, merchant_password,merchant_order_id, merchant_timestamp, success_url, failed_url, error_url,currency_code) 
	values (1234567891, 200, 'merchantid','merchantpass',1987654321, '2016-11-16 06:43:19.77', 'https://www.google.ba/search?q=success', 'https://www.google.com/search?q=failed', 'https://www.google.com/search?q=error','USD');
	
#1234-1334-1234-1235
#334