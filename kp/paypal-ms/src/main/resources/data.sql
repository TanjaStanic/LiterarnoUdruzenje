insert into paypal_db.currency (`code`, `name`)  values ('RSD', 'Serbian dinar');
insert into paypal_db.currency (`code`, `name`)  values ('EUR', 'Euro');
insert into paypal_db.currency (`code`, `name`)  values ('BAM', 'Convertible mark');
insert into paypal_db.currency (`code`, `name`)  values ('USD', 'United States dollar');

-- Literarno udruzenje
insert into paypal_db.client (`client_id`, `client_secret`, `email`)
 values ('fYa7wChWdp7cFDBevRLpl8wfN6LXmn6XQCA0RQohAnUHhInkE0VqS7qMCuEbVRQwIJ60r3PgnwiSQSxnDmUTU6rsIS1+LosKbaGcGsXXiJW6jruJiRgld806foS1nnrr', 'C+Gb1rOyrUYi8kW+ryDpsTVZZJBN31uKYIHMVhHAv0WoXuLPxc8oqX4+Mi2uOA3xsPfjuZwpkiF8IfQblQLle4enAiU+1Krs77/hV1uxu/AMbixlOONk41SEeNZwCoy2', 'sb-zx3ys4123984@business.example.com');

insert into crypto (iv, text) values ("utnsQAGFOzUdQ3eE", "fYa7wChWdp7cFDBevRLpl8wfN6LXmn6XQCA0RQohAnUHhInkE0VqS7qMCuEbVRQwIJ60r3PgnwiSQSxnDmUTU6rsIS1+LosKbaGcGsXXiJW6jruJiRgld806foS1nnrr");
insert into crypto (iv, text) values ("FSgzSBHGBud5OIBS", "C+Gb1rOyrUYi8kW+ryDpsTVZZJBN31uKYIHMVhHAv0WoXuLPxc8oqX4+Mi2uOA3xsPfjuZwpkiF8IfQblQLle4enAiU+1Krs77/hV1uxu/AMbixlOONk41SEeNZwCoy2");

insert into subscription_type (name ) values ("FIXED");
insert into subscription_type (name ) values ("INFINITE");

insert into subscription_frequency(name) values ("MONTH");
insert into subscription_frequency(name) values ("YEAR");

insert  into subscription_plan (cycles_number, frequency_id, seller_id, type_id) values (3, 1, 1, 1);
insert  into subscription_plan (cycles_number, frequency_id, seller_id, type_id) values (6, 1, 1, 1);
insert  into subscription_plan (cycles_number, frequency_id, seller_id, type_id) values (12, 1, 1, 1);