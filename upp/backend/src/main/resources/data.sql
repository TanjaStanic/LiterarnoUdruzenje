INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role)
    VALUES ('admin', 'Admin', 'Admin', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@admin.com', 'NS', 'SRB', true, 'ADMIN');

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role)
    VALUES ('writer123', 'Writer1', 'Writer1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@writer.com', 'NS', 'SRB', true, 'WRITER');

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role)
    VALUES ('reader123', 'Reader1', 'Reader1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@reader.com', 'NS', 'SRB', true, 'READER');

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role)
    VALUES ('breader123', 'Beta_Reader1', 'Beta_Reader1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@beta_reader.com', 'NS', 'SRB', true, 'BETA_READER');

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role)
    VALUES ('lecturer123', 'Lecturer1', 'Lecturer1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@lecturer.com', 'NS', 'SRB', true, 'LECTURER');

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role)
    VALUES ('editor123', 'Editor1', 'Editor1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@editor.com', 'NS', 'SRB', true, 'EDITOR');

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
