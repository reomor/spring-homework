DROP TABLE IF EXISTS AUTHORS;

CREATE TABLE AUTHORS(
  ID INT PRIMARY KEY,
  NAME VARCHAR (255),
  SERNAME VARCHAR (255),
  DATEOFBIRTH DATE,
  BIOGRAPHY VARCHAR (1024)
);