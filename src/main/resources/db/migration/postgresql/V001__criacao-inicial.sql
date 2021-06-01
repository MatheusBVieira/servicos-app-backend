
CREATE TABLE categoria (
  id BIGSERIAL,
  categoria varchar(255) DEFAULT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE estado (
  id BIGSERIAL,
  nome varchar(255) NOT NULL UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE cidade (
  id BIGSERIAL,
  nome varchar(255) NOT NULL,
  estado_id bigint NOT NULL,

  PRIMARY KEY (id),

  CONSTRAINT fk_cidade_estado FOREIGN KEY (estado_id) REFERENCES estado (id)
);

CREATE TABLE usuario (
  id BIGSERIAL,
  cpf varchar(255) DEFAULT NULL UNIQUE,
  email varchar(255) DEFAULT NULL UNIQUE,
  endereco_bairro varchar(255) DEFAULT NULL,
  endereco_cep varchar(255) DEFAULT NULL,
  endereco_complemento varchar(255) DEFAULT NULL,
  endereco_logradouro varchar(255) DEFAULT NULL,
  endereco_numero varchar(255) DEFAULT NULL,
  midia_path varchar(255) DEFAULT NULL,
  nome_completo varchar(255) DEFAULT NULL,
  senha varchar(255) DEFAULT NULL,
  telefone varchar(255) DEFAULT NULL,
  endereco_cidade_id bigint DEFAULT NULL,

  PRIMARY KEY (id),

  CONSTRAINT fk_usuario_endereco_cidade FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id)
);

CREATE TABLE servico (
  id BIGSERIAL,
  descricao varchar(255) NOT NULL,
  titulo varchar(255) NOT NULL,
  categoria_id bigint NOT NULL,
  prestador_servico_id bigint NOT NULL,

  PRIMARY KEY (id),

  CONSTRAINT fk_servico_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id),
  CONSTRAINT fk_servico_prestador_servico FOREIGN KEY (prestador_servico_id) REFERENCES usuario (id)
);

CREATE TABLE avaliacao (
  id BIGSERIAL,
  comentario varchar(255) DEFAULT NULL,
  data TIMESTAMP DEFAULT NULL,
  nota float DEFAULT NULL,
  avaliador_id bigint DEFAULT NULL,
  servico_id bigint DEFAULT NULL,

  PRIMARY KEY (id),

  CONSTRAINT fk_avaliacao_avaliador FOREIGN KEY (avaliador_id) REFERENCES usuario (id),
  CONSTRAINT fk_avaliacao_servico FOREIGN KEY (servico_id) REFERENCES servico (id)
);

CREATE TABLE midia (
  id BIGSERIAL,
  data_de_upload date DEFAULT NULL,
  midia oid NOT NULL,
  nome varchar(255) DEFAULT NULL,
  tipo varchar(255) DEFAULT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE hibernate_sequences (
  sequence_name varchar(255) NOT NULL,
  next_val bigint DEFAULT NULL,

  PRIMARY KEY (sequence_name)
);

CREATE TABLE perfil (
  id BIGSERIAL,
  nome varchar(255) DEFAULT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE usuario_perfis (
  usuario_id bigint NOT NULL,
  perfis_id bigint NOT NULL,

  CONSTRAINT fk_usuario_perfis_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
  CONSTRAINT fk_usuario_perfis_perfis FOREIGN KEY (perfis_id) REFERENCES perfil (id)
);

CREATE TABLE servico_agendado (
  id BIGSERIAL,
  data_pedido TIMESTAMP DEFAULT NULL,
  data_visita TIMESTAMP DEFAULT NULL,
  is_orcamento bit(1) DEFAULT NULL,
  preco_orcamento decimal(19,2) DEFAULT NULL,
  preco_servico decimal(19,2) DEFAULT NULL,
  contratante_id bigint DEFAULT NULL,
  servico_id bigint DEFAULT NULL,

  PRIMARY KEY (id),

  CONSTRAINT fk_servico_agendado_contratante FOREIGN KEY (contratante_id) REFERENCES usuario (id),
  CONSTRAINT fk_servico_agendado_servico FOREIGN KEY (servico_id) REFERENCES servico (id)
);

