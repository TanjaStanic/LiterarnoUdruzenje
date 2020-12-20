-- merchantid, merchantpass
insert into client(email,merchantid, merchant_password,name)
 	values ('test@gmail.com',"R8uXnFPez8ilQlToCABQ8w==","HoeVKkr2cjg3o5vw+fda6Q==","Pero Peric");
-- merchantid2, merchantpass2
 insert into client (email,merchantid, merchant_password,name)
 	values ('test2@gmail.com',"0IpdPLyw1IEZzJjSPHFrTg==","F6EvLrq3T9hzrzqRkKyS8A==","Marko Markovic");

insert into currency (`code`, `name`)  values ('RSD', 'Serbian dinar');
insert into currency (`code`, `name`)  values ('EUR', 'Euro');
insert into currency (`code`, `name`)  values ('BAM', 'Convertible mark');
insert into currency (`code`, `name`)  values ('USD', 'United States dollar');