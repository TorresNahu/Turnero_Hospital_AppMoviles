PRAGMA foreign_keys = on;
CREATE TABLE Especialidades (idEspecialidad INTEGER PRIMARY KEY, nombre TEXT);
CREATE TABLE Usuarios(idUsuario INTEGER PRIMARY KEY, nombreUsuario TEXT, apellidoUsuario TEXT, dni INTEGER, pass TEXT);
CREATE TABLE datosTurno (idTurno INTEGER PRIMARY KEY, fecha TEXT, id_Especialidad INTEGER, id_Usuario INTEGER, FOREIGN KEY(id_Usuario) REFERENCES Usuarios(idUsuario), FOREIGN KEY(id_Especialidad) REFERENCES Especialidades(idEspecialidad));
INSERT INTO Especialidades (idEspecialidad, nombre) VALUES (1, 'Cardiologia'), (2, 'Neurologia'), (3, 'Traumatologia'), (4, 'Pediatria'), (5, 'Ginecologia'), (6, 'Dermatologia'), (7, 'Nutricion'), (8, 'Gastroenterologia'), (9, 'Urologia'), (10, 'Oncologia'), (11, 'Psiquiatria'), (12, 'Rehabilitacion');
INSERT INTO Usuarios (idUsuario, nombreUsuario, apellidoUsuario, dni, pass) VALUES (1, 'Julian', 'De Villa Ortusar', 30123456, 'admin');