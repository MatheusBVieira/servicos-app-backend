CREATE TABLE libera_avaliacao (
  id BIGSERIAL,
  pode_comentar boolean NOT NULL,
  servico_id bigint DEFAULT NULL,
  usuario_id bigint DEFAULT NULL,

  PRIMARY KEY (id),

  CONSTRAINT fk_libera_avaliacao_servico FOREIGN KEY (servico_id) REFERENCES servico (id),
  CONSTRAINT fk_libera_avaliacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);