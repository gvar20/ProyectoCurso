-- Eliminar tablas si existen
DROP TABLE IF EXISTS notas_compartidas;
DROP TABLE IF EXISTS notas;
DROP TABLE IF EXISTS usuarios;

-- Tabla de usuarios
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    apodo VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de notas
CREATE TABLE notas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    contenido TEXT,
    usuario_id INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabla de notas compartidas
CREATE TABLE notas_compartidas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nota_id INT NOT NULL,
    usuario_origen_id INT NOT NULL,
    usuario_destino_id INT NOT NULL,
    fecha_compartido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (nota_id) REFERENCES notas(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (usuario_origen_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_destino_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

INSERT INTO usuarios (apodo, contrasena) VALUES
('admin', 'admin'),
('guillermo', 'admin'),
('guillermo1', 'admin'),
('guillermo2', 'admin'),
('guillermo3', 'admin');

INSERT INTO notas (titulo, contenido, usuario_id) VALUES
('Primera nota de Juan', 'Contenido de la primera nota de Juan', 1),
('Nota de Maria sobre proyectos', 'Contenido de la nota de Maria referente a proyectos', 2),
('Nota de Pedro sobre Python', 'Contenido sobre mi experiencia con Python', 3),
('Luisa comparte ideas de diseño', 'Contenido de la nota de Luisa sobre diseño gráfico', 4),
('Nota de Luisito sobre bases de datos', 'Contenido de la nota de Luisito sobre SQL y bases de datos', 5);

INSERT INTO notas_compartidas (nota_id, usuario_origen_id, usuario_destino_id) VALUES
(1, 1, 2),
(2, 2, 3),
(3, 3, 4),
(4, 4, 5),
(5, 5, 1);

