-- merchantid, merchantpass
insert into bitcoin_db.client (email, merchantid, merchant_password, pc_client_id, name, token)
 	values ('celiksara97@gmail.com',"SehSoEF/hE4zQQBTlPHdjQ==","dBPBssVtjCN9Pu9jcocH5w==", 1,"Pero Peric", "yvUjbNBaNDnPxgBysidnooeX926Vyy81YuPMUWf1");
-- merchantid2, merchantpass2
 insert into bitcoin_db.client (email, merchantid, merchant_password, pc_client_id, name, token)
 	values ('test2@gmail.com',"QMx0twcwUPRp4SiSuYDzyQ==","6pxDjNgvUR7Od4c5eoPGtw==", 3, "Zika Zikic", null);
 	
insert into bitcoin_db.crypto (iv, text) values ("XbZsZV94KIvgSyxU", "SehSoEF/hE4zQQBTlPHdjQ==");
insert into bitcoin_db.crypto (iv, text) values ("1XJ5EOYceqDaIwfl", "dBPBssVtjCN9Pu9jcocH5w==");

insert into bitcoin_db.crypto (iv, text) values ("uLJkbliEvRfZZJhy", "QMx0twcwUPRp4SiSuYDzyQ==");
insert into bitcoin_db.crypto (iv, text) values ("Wbis7qEjhKpqzRRB", "6pxDjNgvUR7Od4c5eoPGtw==");


insert into bitcoin_db.currency (`code`, `name`)  values ('RSD', 'Serbian dinar');
insert into bitcoin_db.currency (`code`, `name`)  values ('EUR', 'Euro');
insert into bitcoin_db.currency (`code`, `name`)  values ('BAM', 'Convertible mark');
insert into bitcoin_db.currency (`code`, `name`)  values ('USD', 'United States dollar');
insert into bitcoin_db.currency (`code`, `name`)  values ('BTC', 'Bitcoin');