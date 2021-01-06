-- merchantid, merchantpass
insert into client(email,merchantid, merchant_password, pc_client_id)
 	values ('test@gmail.com',"RBBEP5uy/qre/uG+lKP+3w==","ityKc+2hQVye+i5FV2FQWA==", 1);
-- merchantid2, merchantpass2
 insert into client (email,merchantid, merchant_password, pc_client_id)
 	values ('test2@gmail.com',"8fqDJvRIsTt/+uDfGziCNQ==","7g958ZFGlg+9me/kFN3uEQ==", 3);

insert into bank_db.crypto (iv, text) values ("Nirmw54CdIcKBIKj", "RBBEP5uy/qre/uG+lKP+3w==");
insert into bank_db.crypto (iv, text) values ("LJ2YntLnpcRJU6sX", "ityKc+2hQVye+i5FV2FQWA==");
insert into bank_db.crypto (iv, text) values ("10OpgqobEgBmDzTz", "8fqDJvRIsTt/+uDfGziCNQ==");
insert into bank_db.crypto (iv, text) values ("NkBfIiylJ8BxGm8E", "7g958ZFGlg+9me/kFN3uEQ==");


insert into currency (`code`, `name`)  values ('RSD', 'Serbian dinar');
insert into currency (`code`, `name`)  values ('EUR', 'Euro');
insert into currency (`code`, `name`)  values ('BAM', 'Convertible mark');
insert into currency (`code`, `name`)  values ('USD', 'United States dollar');