CREATE TABLE blog.categoria (
	id SERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(50)
);

INSERT INTO blog.categoria(nome) VALUES('Alimentação');

CREATE TABLE blog.artigo(
	id BIGSERIAL PRIMARY KEY NOT NULL,
	titulo VARCHAR(200) NOT NULL,
	data_publicacao TIMESTAMP NOT NULL,
	data_revisao TIMESTAMP,
	thumbnail VARCHAR(200) NOT NULL,
	slug VARCHAR(200) NOT NULL,
	texto TEXT NOT NULL,
	usuario_id BIGINT REFERENCES seguranca.usuario(id),
	categoria_id INTEGER REFERENCES blog.categoria(id)
);