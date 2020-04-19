



INSERT INTO USERS (username,salt,password,firstname,lastname)
VALUES ('ealvarez','SALT','$2a$10$d262hb9nkGCBgMG5c6y3V.USZfMXCPRe7Z.tgtXvf01eErbvogSnW', 'Eduardo', 'Alvarez');

INSERT INTO ROLES (name, userid)
VALUES ('ADMIN', 1);



INSERT INTO NOTES (notetitle, notedescription, userid)
values ('Titulo 1', 'Descrip 1',1),
('Titulo 2', 'Descrip 2',1),
('Titulo 3', 'Descrip 3',1),
('Titulo 4', 'Descrip 4',1);

INSERT INTO CREDENTIALS (url, username, key, password, userid)
values ('https://www.gmail.com', 'micorreo@gmail.com','THISTHESECRETKEY','H5QcJjvWIMCEta9Ajf4zSA==',1),
 ('https://www.yahoo.com', 'micorreo@gmail.com','THISTHESECRETKEY','H5QcJjvWIMCEta9Ajf4zSA==',1),
  ('https://www.outlook.com', 'micorreo@gmail.com','THISTHESECRETKEY','H5QcJjvWIMCEta9Ajf4zSA==',1),
   ('https://www.otro.com', 'micorreo@gmail.com','THISTHESECRETKEY','H5QcJjvWIMCEta9Ajf4zSA==',1);




