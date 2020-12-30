-- SuperJakaLozinka1!
insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date, user_id)
 values ("123", "123", "test@gmail.com", "Pero Peric", "$2a$10$g4CW8HjzjezXXW8z5jowD.ITgaES5wF/mijmoWJ//bpntP1oczjC6", true, true, null, 1);

 insert into client (tax_identification_number, company_registration_number, email, name, password, active, enabled, last_password_reset_date, user_id)
  values ("12345", "12345", "sb-zx3ys4123984@business.example.com", "Mico Micic", "$2a$10$g4CW8HjzjezXXW8z5jowD.ITgaES5wF/mijmoWJ//bpntP1oczjC6", true, true, null, 2);

insert into roles (name) values ("ADMIN");
insert into roles (name) values ("READER");
insert into roles (name) values ("BETA_READER");
insert into roles (name) values ("WRITER");
insert into roles (name) values ("EDITOR");
insert into roles (name) values ("BOARD_MEMBER");
-- SuperJakaLozinka1!
insert into users (email, name, password, city, country, enabled, role_id) values ("admin@example.com", "John Doe", "$2a$10$g4CW8HjzjezXXW8z5jowD.ITgaES5wF/mijmoWJ//bpntP1oczjC6", "Novi Sad", "Serbia", true, 1);
insert into users (email, name, password, city, country, enabled, role_id) values ("jezurka.jezic@example.com", "Jezurka Jezic", "$2a$10$g4CW8HjzjezXXW8z5jowD.ITgaES5wF/mijmoWJ//bpntP1oczjC6", "Novi Sad", "Serbia", true, 2);
--insert into users (email, name, password, enabled, role_id) values ();

insert into books (name, description, authors, genres, price) values("Pride and Prejudice", "Lorem ipsum", "Jane Austen", "Romance", "999");
insert into books (name, description, authors, genres, price) values("Faust", "Lorem ipsum", "Johann Wolfgang von Goethe", "Tragedy", "899");
insert into books (name, description, authors, genres, price) values("Hamlet", "Lorem ipsum", "William Shakespeare", "Drama, Tragedy", "799");
insert into books (name, description, authors, genres, price) values("The Hobbit", "Lorem ipsum", "J.R.R. Tolkien", "Epic", "1999");
insert into books (name, description, authors, genres, price) values("Grimms’ Fairy Tales", "Lorem ipsum", "Jacob Grimm, Wilhelm Grimm", "Fairy tale, Folklore", "999");