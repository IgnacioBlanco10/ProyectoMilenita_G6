DROP DATABASE IF EXISTS milenita;
CREATE DATABASE milenita;
USE milenita;

CREATE TABLE categoria (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300),
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE producto (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(500),
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    imagen VARCHAR(255),
    sabor VARCHAR(100),
    tamano VARCHAR(100),
    ingredientes VARCHAR(500),
    destacado BOOLEAN DEFAULT FALSE,
    activo BOOLEAN DEFAULT TRUE,
    id_categoria BIGINT,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE comentario (
    id_comentario BIGINT AUTO_INCREMENT PRIMARY KEY,
    contenido VARCHAR(500) NOT NULL,
    calificacion INT NOT NULL,
    fecha DATETIME NOT NULL,
    id_usuario BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,

    CONSTRAINT fk_comentario_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),

    CONSTRAINT fk_comentario_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

INSERT INTO categoria (nombre, descripcion, activo) VALUES
('Papas y Snacks', 'Snacks producidos por La Milenita', TRUE),
('Merchandising', 'Productos promocionales de la marca', TRUE);

INSERT INTO producto 
(nombre, descripcion, precio, stock, imagen, sabor, tamano, ingredientes, destacado, activo, id_categoria)
VALUES

('Papas Tostadas La Milenita (Bolsa Grande)',
'Papas tostadas clásicas de La Milenita.',
1500,
50,
'bolsa_mediana.jpeg',
'Clásico',
'Bolsa grande',
'Papa, aceite, sal',
TRUE,
TRUE,
1),

('Papas Tostadas La Milenita (Bolsa Pequeña)',
'Papas tostadas presentación pequeña.',
900,
60,
'bolsa_pequena.jpeg',
'Clásico',
'Bolsa pequeña',
'Papa, aceite, sal',
FALSE,
TRUE,
1),

('Papas Onduladas Pimienta Limón',
'Papas onduladas sabor pimienta limón.',
1600,
40,
'papas_onduladas.jpeg',
'Pimienta Limón',
'Bolsa mediana',
'Papa, aceite, especias',
TRUE,
TRUE,
1),

('Aros de Cebolla',
'Aros de cebolla crujientes.',
1400,
45,
'aros_cebolla.jpeg',
'Cebolla',
'Bolsa mediana',
'Harina, cebolla, especias',
FALSE,
TRUE,
1),

('Nachos La Milenita',
'Nachos crujientes de maíz.',
1500,
35,
'nachos_milenita.jpeg',
'Maíz',
'Bolsa mediana',
'Maíz, aceite, sal',
FALSE,
TRUE,
1),

('Palitos La Milenita',
'Palitos de maíz crujientes.',
1400,
40,
'palitos_milenita.jpeg',
'Maíz',
'Bolsa mediana',
'Maíz, aceite, sal',
FALSE,
TRUE,
1),

('Bolso La Milenita',
'Bolso promocional oficial de La Milenita.',
6500,
15,
'bolso_milenita.jpeg',
NULL,
'Único',
NULL,
FALSE,
TRUE,
2),

('Camiseta Blanca La Milenita',
'Camiseta oficial blanca de la marca.',
8000,
20,
'camiseta_blanca.jpeg',
NULL,
'Tallas variadas',
'Algodón',
FALSE,
TRUE,
2),

('Camiseta Negra Milenita Lovers',
'Camiseta negra edición Milenita Lovers.',
8500,
20,
'camiseta_negra.jpeg',
NULL,
'Tallas variadas',
'Algodón',
FALSE,
TRUE,
2);


CREATE TABLE rol (
    id_rol BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE usuario_rol (
    id_usuario BIGINT NOT NULL,
    id_rol BIGINT NOT NULL,
    PRIMARY KEY (id_usuario, id_rol),
    CONSTRAINT fk_usuario_rol_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_usuario_rol_rol
        FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

CREATE TABLE pedido (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    total DOUBLE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE pedido_detalle (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

INSERT INTO rol (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO rol (nombre) VALUES ('ROLE_USUARIO');
INSERT INTO rol (nombre) VALUES ('ROLE_VENDEDOR');

SELECT * FROM producto;
DELETE FROM producto
WHERE id_producto = 11;