INSERT INTO AUTHORS (ID, NAME, SERNAME, DATEOFBIRTH, BIOGRAPHY) VALUES
  (1, 'Mark', 'Twain', DATE '1830-11-30', 'Tom Sawyers creator.'),
  (2, 'Rudyard', 'Kipling', DATE '1860-12-30', 'Maugly creator.'),
  (3, 'George', 'Lucas', DATE '1970-05-07', 'Creator of a universe.');

INSERT INTO GENRES (ID, NAME, DESCRIPTION) VALUES
  (1, 'detective', 'detective descr'),
  (2, 'romance', 'romance descr'),
  (3, 'horror', 'horror descr'),
  (4, 'tale', 'tale descr');

INSERT INTO BOOKS (ID, TITLE, ID_GENRE, ISBN, DESCRIPTION) VALUES
  (1, 'The Adventures of Tom Sawyer', 1, '978-1-49-523895-6', 'some'),
  (2, 'The Jungle Book', 2, '978-1-50-333254-6', 'some'),
  (3, 'Star Wars', 4, '978-0-34-526079-6', 'some'),
  (4, 'The Mysterious Stranger', 3, '978-0-52-024695-9', 'some');

INSERT INTO AUTHOR_BOOK (ID_AUTHOR, ID_BOOK) VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (1, 4);