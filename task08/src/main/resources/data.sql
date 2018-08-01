INSERT INTO GENRE (NAME, DESCRIPTION) VALUES
  ('detective', 'detective descr'),
  ('romance', 'romance descr'),
  ('horror', 'horror descr'),
  ('tale', 'tale descr'),
  ('test', 'test descr');

INSERT INTO AUTHOR (NAME, SERNAME, DATE_OF_BIRTH, BIOGRAPHY) VALUES
  ('Mark', 'Twain', DATE '1830-11-30', 'Tom Sawyers creator.'),
  ('Rudyard', 'Kipling', DATE '1860-12-30', 'Maugly creator.'),
  ('George', 'Lucas', DATE '1970-05-07', 'Creator of a universe.'),
  ('George', 'сo-author', DATE '1970-05-07', 'Creator of a universe.'),
  ('George', 'deleted', DATE '1970-05-07', 'Creator of a universe.');

INSERT INTO BOOK (TITLE, GENRE_ID, ISBN, DESCRIPTION) VALUES
  ('The Adventures of Tom Sawyer', 1, '978-1-49-523895-6', 'some'),
  ('The Jungle Book', 2, '978-1-50-333254-6', 'some'),
  ('Star Wars', 4, '978-0-34-526079-6', 'some'),
  ('The Mysterious Stranger', 3, '978-0-52-024695-9', 'some'),
  ('To delete', 3, '978-0-52-024695-9', 'some');

INSERT INTO AUTHOR_BOOK (BOOK_ID, AUTHOR_ID) VALUES
  (1, 1),
  (2, 2),
  (2, 4),
  (3, 3),
  (4, 1);

INSERT INTO COMMENT (BODY, DATE, BOOK_ID) VALUES
  ('Comment 1', '2018-07-22 15:00:00', 1),
  ('Comment 2', '2018-07-22 17:00:00', 1),
  ('Comment 3', '2018-07-22 19:00:00', 1),
  ('Comment 4', '2018-07-22 19:00:00', 2);