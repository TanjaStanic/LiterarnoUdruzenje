INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated)
    VALUES ('admin', 'Admin', 'Admin', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@admin.com', 'NS', 'SRB', true);

INSERT INTO upp_db.role(name)
    VALUES ('ADMIN');

INSERT INTO upp_db.role_users(roles_id, users_id)
    VALUES (1, 1);

INSERT INTO upp_db.genre(name)
    VALUES ('Akcioni');

INSERT INTO upp_db.genre(name)
    VALUES ('Triler');

INSERT INTO upp_db.genre(name)
    VALUES ('Komedija');

INSERT INTO upp_db.genre(name)
    VALUES ('Fantastika');

INSERT INTO upp_db.genre(name)
    VALUES ('Ljubavni');
