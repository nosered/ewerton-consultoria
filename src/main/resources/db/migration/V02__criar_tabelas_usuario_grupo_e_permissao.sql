CREATE TABLE seguranca.usuario(
	id BIGSERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(300) NOT NULL,
	email VARCHAR(300) NOT NULL,
	senha VARCHAR(100) NOT NULL,
	ativo BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE seguranca.grupo(
	id SERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(50)
);

CREATE TABLE seguranca.permissao(
	id SERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(100)
);

CREATE TABLE seguranca.permissoes_grupo(
	id BIGSERIAL PRIMARY KEY NOT NULL,
	grupo_id INTEGER REFERENCES seguranca.grupo(id),
	permissao_id INTEGER REFERENCES seguranca.permissao(id)
);

CREATE TABLE seguranca.grupos_usuario(
	id BIGSERIAL PRIMARY KEY NOT NULL,
	usuario_id BIGINT REFERENCES seguranca.usuario(id),
	grupo_id INTEGER REFERENCES seguranca.grupo(id)
);

INSERT INTO seguranca.usuario(nome, email, senha, ativo) VALUES('Administrador do Sistema', 'admin@sys', '{bcrypt}$2a$10$RT41C3mPeGgRYFozh1cq1uLVpj8ULU4vu2JBRBzbI76uojyEGpsqm', true);
INSERT INTO seguranca.grupo(nome) VALUES('Administrador');
INSERT INTO seguranca.permissao(nome) VALUES('ACESSO_TOTAL');
INSERT INTO seguranca.permissoes_grupo(grupo_id, permissao_id) VALUES(1,1);
INSERT INTO seguranca.grupos_usuario(usuario_id, grupo_id) VALUES(1,1);