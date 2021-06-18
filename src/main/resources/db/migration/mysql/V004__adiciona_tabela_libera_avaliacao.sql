CREATE TABLE libera_avaliacao (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  pode_comentar bit(1) NOT NULL,
  servico_id bigint(20) DEFAULT NULL,
  usuario_id bigint(20) DEFAULT NULL,

  PRIMARY KEY (id),
  KEY fk_libera_avaliacao_servico (servico_id),
  KEY fk_libera_avaliacao_usuario (usuario_id),

  CONSTRAINT fk_libera_avaliacao_servico FOREIGN KEY (servico_id) REFERENCES servico (id),
  CONSTRAINT fk_libera_avaliacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;