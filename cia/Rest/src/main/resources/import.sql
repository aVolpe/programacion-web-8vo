-- You can use this file to load seed data into the database using SQL statements
insert into Persona (id, nombre, ruc) values (100, 'Arturo', '4787587-9')
insert into Telefono (id, persona_id, numero) values (100, 100, '0981953876')
insert into Telefono (id, persona_id, numero) values (101, 100, '021447031')
insert into Llamada (id, telefono_id, duracion, fecha) values (100,100,45,'01/01/2012')
insert into Llamada (id, telefono_id, duracion, fecha) values (101,100,50,'01/01/2012')
insert into Llamada (id, telefono_id, duracion, fecha) values (102,101,30,'01/01/2012')
insert into Llamada (id, telefono_id, duracion, fecha) values (103,101,55,'01/01/2012')