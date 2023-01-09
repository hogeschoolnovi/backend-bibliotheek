INSERT INTO author (id, initials, firstname, lastname, date_of_birth, gender) VALUES ('1001', 'J.K.', 'Joanne Kathleen', 'Rowling', '1965-07-31', 1);
INSERT INTO author (id, initials, firstname, lastname, date_of_birth, gender) VALUES('1002', 'R.', 'Roald', 'Dahl', '1916-09-13)', 0);

INSERT INTO book (isbn, title, subtitle, genre, language, type, publisher, author_id) VALUES ('9789076174105', 'Harry Potter', 'en de steen der wijzen', 'fiction', 'NL', 'paperback', 'uitgeverij de Harmonie', '1001');
INSERT INTO book (isbn, title, subtitle, genre, language, type, publisher, author_id) VALUES ('9781781103470', 'Harry Potter', 'en de geheime kamer', 'fiction', 'NL', 'paperback', 'Pottermore publishing', '1001');
INSERT INTO book (isbn, title, subtitle, genre, language, type, publisher, author_id) VALUES ('9789026139406', 'Mathilda', '', 'fiction', 'NL', 'paperback', 'de Fontein Jeugd', '1002');