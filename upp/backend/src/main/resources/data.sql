INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('admin', 'Admin', 'Admin', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@admin.com', 'NS', 'SRB', true, 'ADMIN', 0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('writer123', 'Writer1', 'Writer1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@writer.com', 'NS', 'SRB', true, 'WRITER',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('writer456', 'Writer2', 'Writer2', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email1@writer.com', 'NS', 'SRB', true, 'WRITER_FILES',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('reader123', 'Reader1', 'Reader1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@reader.com', 'NS', 'SRB', true, 'READER',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('breader123', 'Beta_Reader1', 'Beta_Reader1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@beta_reader.com', 'NS', 'SRB', true, 'BETA_READER',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
VALUES ('breader456', 'Beta_Reader1', 'Beta_Reader1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email1@beta_reader.com', 'NS', 'SRB', true, 'BETA_READER',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('lecturer123', 'Lecturer1', 'Lecturer1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email1@lecturer.com', 'NS', 'SRB', true, 'LECTURER',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('lecturer456', 'Lecturer2', 'Lecturer2', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email2@lecturer.com', 'NS', 'SRB', true, 'LECTURER',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('lecturer789', 'Lecturer3', 'Lecturer3', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email3@lecturer.com', 'NS', 'SRB', true, 'LECTURER',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('editor123', 'Editor1', 'Editor1', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@editor1.com', 'NS', 'SRB', true, 'EDITOR',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('editor456', 'Editor2', 'Editor2', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@editor2.com', 'NS', 'SRB', true, 'EDITOR',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('editor789', 'Editor3', 'Editor3', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@editor3.com', 'NS', 'SRB', true, 'EDITOR',0);

INSERT INTO upp_db.user(username, first_name, last_name, password, email, city, country, activated, role, points)
    VALUES ('editor000', 'Editor4', 'Editor4', '$2a$10$BPGCvrv4ROnM70RrwZETwuU4irNlKFAQvXwYuiUrvevEIjDmhNSXi', 'email@editor4.com', 'NS', 'SRB', true, 'EDITOR',0);


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

INSERT INTO upp_db.reader_genres(user_id, genre_id)
    VALUES (4, 1);

INSERT INTO upp_db.reader_genres(user_id, genre_id)
    VALUES (4, 2);

INSERT INTO upp_db.reader_genres(user_id, genre_id)
    VALUES (4, 3);

INSERT INTO upp_db.beta_genres(user_id, genre_id)
    VALUES (5, 4);

INSERT INTO upp_db.beta_genres(user_id, genre_id)
    VALUES (5, 5);

INSERT INTO upp_db.document(file_url)
    VALUES('http://localhost:8080/files/download/file1.pdf');

INSERT INTO upp_db.document(file_url)
    VALUES('http://localhost:8080/files/download/file2.pdf');

INSERT INTO upp_db.document(file_url)
    VALUES('http://localhost:8080/files/download/file3.pdf');

INSERT INTO upp_db.book(isbn, key_terms, pages, place_published, publisher, synopsis, title, year_published, document_id, accepted,editor_id)
    VALUES('isbn_unique_1', 'key_terms_1', 10, 'place_published_1', 'publisher_name_1', 'synopsis_1', 'Title 1', '1990', 1, false,10);

INSERT INTO upp_db.book(isbn, key_terms, pages, place_published, publisher, synopsis, title, year_published, document_id, accepted)
    VALUES('isbn_unique_2', 'key_terms_2', 11, 'place_published_2', 'publisher_name_2', 'synopsis_2', 'Title 2', '1991', 2, false);

INSERT INTO upp_db.book(isbn, key_terms, pages, place_published, publisher, synopsis, title, year_published, document_id, accepted)
    VALUES('isbn_unique_3', 'key_terms_3', 12, 'place_published_3', 'publisher_name_3', 'synopsis_3', 'Title 3', '1992', 3, false);

INSERT INTO upp_db.genre_books(genre_id, book_id)
    VALUES(1, 1);

INSERT INTO upp_db.genre_books(genre_id, book_id)
    VALUES(2, 2);

INSERT INTO upp_db.genre_books(genre_id, book_id)
    VALUES(3, 3);

INSERT INTO upp_db.genre_books(genre_id, book_id)
    VALUES(4, 1);

INSERT INTO upp_db.genre_books(genre_id, book_id)
    VALUES(5, 2);

INSERT INTO upp_db.writers_books(user_id, book_id)
    VALUES(2, 1);

INSERT INTO upp_db.writers_books(user_id, book_id)
    VALUES(3, 2);

INSERT INTO upp_db.writers_books(user_id, book_id)
    VALUES(2, 3);

 --INSERT INTO upp_db.registration_application(created_date, final_response, user_id)
  --  VALUES('08-07-17', null, 3);

--INSERT INTO upp_db.document(file_url, registration_application_id)
--    VALUES('http://localhost:8080/files/download/file1.pdf', 1);

--INSERT INTO upp_db.document(file_url, registration_application_id)
  --  VALUES('http://localhost:8080/files/download/file2.pdf', 1);

INSERT INTO upp_db.card(card_holder, card_number, cvv, expiration_date, user_id)
    VALUES('Pera Peric', '$2y$12$Q3Ipbn/H4n5mQqV6FUu7.u3iMXzfkLKBRttMofKGfIxYKkgAv2DMC', '$2y$12$Cua1FeCtFfD9Kuz514L/0.U7ulw374aENjfM.yyQoYAjaD5/hATEK', '01/23', 3);

-- card num: 1234-1234-1234-1234 cvv: 333

INSERT INTO upp_db.plagiarism_complaint(plagiarised_book_id, writers_book_id)
    VALUES(1, 2);

INSERT INTO upp_db.plagiarism_complaint_response(plagiarism_complaint_id, comment, user_id, plagiarised_response)
    VALUES(1, 'Ovo je comment.', 1, true);

INSERT INTO upp_db.plagiarism_complaint_response(plagiarism_complaint_id, comment, user_id, plagiarised_response)
    VALUES(1, 'Ovo je comment 2.', 2, false);

INSERT INTO upp_db.plagiarism_complaint_response(plagiarism_complaint_id, comment, user_id, plagiarised_response)
    VALUES(1, 'Ovo je comment 3.', 3, false);

