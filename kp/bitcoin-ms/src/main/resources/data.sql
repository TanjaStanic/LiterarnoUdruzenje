
insert into bitcoin_db.client (email, pc_client_id, name, token)
 	values ('sb-zx3ys4123984@business.example.com', 2,'Pero Peric', 'nXCxO6PKrN9EuCdygWN9SO0azTxLZyVdg+fnAo6p06QcTr7OgjXsmDXxFddiRU60');
 insert into bitcoin_db.client (email, pc_client_id, name, token)
 	values ('test2@gmail.com', 3, 'Zika Zikic', 'nXCxO6PKrN9EuCdygWN9SO0azTxLZyVdg+fnAo6p06QcTr7OgjXsmDXxFddiRU60');

--insert into bitcoin_db.crypto (iv, text) values ("XbZsZV94KIvgSyxU", "SehSoEF/hE4zQQBTlPHdjQ==");
--insert into bitcoin_db.crypto (iv, text) values ("1XJ5EOYceqDaIwfl", "dBPBssVtjCN9Pu9jcocH5w==");

--insert into bitcoin_db.crypto (iv, text) values ("uLJkbliEvRfZZJhy", "QMx0twcwUPRp4SiSuYDzyQ==");
--insert into bitcoin_db.crypto (iv, text) values ("Wbis7qEjhKpqzRRB", "6pxDjNgvUR7Od4c5eoPGtw==");
insert into bitcoin_db.crypto (`iv`, `text`) values ('yffXgb9oH5NdUErv', 'nXCxO6PKrN9EuCdygWN9SO0azTxLZyVdg+fnAo6p06QcTr7OgjXsmDXxFddiRU60');

insert into bitcoin_db.currency (`code`, `name`)  values ('RSD', 'Serbian dinar');
insert into bitcoin_db.currency (`code`, `name`)  values ('EUR', 'Euro');
insert into bitcoin_db.currency (`code`, `name`)  values ('BAM', 'Convertible mark');
insert into bitcoin_db.currency (`code`, `name`)  values ('USD', 'United States dollar');
insert into bitcoin_db.currency (`code`, `name`)  values ('BTC', 'Bitcoin');