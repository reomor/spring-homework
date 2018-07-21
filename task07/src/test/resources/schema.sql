DROP TABLE IF EXISTS AUTHOR_BOOK;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS GENRES;

CREATE TABLE AUTHORS (
  ID SERIAL PRIMARY KEY,
  NAME VARCHAR (255),
  SERNAME VARCHAR (255),
  DATEOFBIRTH DATE,
  BIOGRAPHY VARCHAR (1024)
);

CREATE TABLE GENRES (
  ID SERIAL PRIMARY KEY,
  NAME VARCHAR (255),
  DESCRIPTION VARCHAR (1024)
);

CREATE TABLE BOOKS (
  ID SERIAL PRIMARY KEY,
  TITLE VARCHAR (255),
  ID_GENRE INT REFERENCES GENRES(ID),
  ISBN VARCHAR(255),
  DESCRIPTION VARCHAR(1024)
);

CREATE TABLE AUTHOR_BOOK (
  ID_AUTHOR INT REFERENCES AUTHORS(ID),
  ID_BOOK INT REFERENCES BOOKS(ID)
)