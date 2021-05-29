
CREATE TABLE `categoria` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `categoria` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `estado` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_estado_nome` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `cidade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `estado_id` bigint(20) NOT NULL,

  PRIMARY KEY (`id`),

  KEY `fk_cidade_estado` (`estado_id`),
  CONSTRAINT `fk_cidade_estado` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `endereco_bairro` varchar(255) DEFAULT NULL,
  `endereco_cep` varchar(255) DEFAULT NULL,
  `endereco_complemento` varchar(255) DEFAULT NULL,
  `endereco_logradouro` varchar(255) DEFAULT NULL,
  `endereco_numero` varchar(255) DEFAULT NULL,
  `midia_path` varchar(255) DEFAULT NULL,
  `nome_completo` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `endereco_cidade_id` bigint(20) DEFAULT NULL,

  PRIMARY KEY (`id`),

  UNIQUE KEY `uk_usuario_cpf` (`cpf`),
  UNIQUE KEY `uk_usuario_email` (`email`),
  UNIQUE KEY `uk_usuario_telefone` (`telefone`),

  KEY `fk_usuario_endereco_cidade` (`endereco_cidade_id`),
  CONSTRAINT `fk_usuario_endereco_cidade` FOREIGN KEY (`endereco_cidade_id`) REFERENCES `cidade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `servico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `categoria_id` bigint(20) NOT NULL,
  `prestador_servico_id` bigint(20) NOT NULL,

  PRIMARY KEY (`id`),

  KEY `fk_servico_categoria` (`categoria_id`),
  KEY `fk_servico_prestador_servico` (`prestador_servico_id`),

  CONSTRAINT `fk_servico_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`),
  CONSTRAINT `fk_servico_prestador_servico` FOREIGN KEY (`prestador_servico_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `avaliacao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comentario` varchar(255) DEFAULT NULL,
  `data` datetime(6) DEFAULT NULL,
  `nota` float DEFAULT NULL,
  `avaliador_id` bigint(20) DEFAULT NULL,
  `servico_id` bigint(20) DEFAULT NULL,

  PRIMARY KEY (`id`),

  KEY `fk_avaliacao_avaliador` (`avaliador_id`),
  KEY `fk_avaliacao_servico` (`servico_id`),

  CONSTRAINT `fk_avaliacao_avaliador` FOREIGN KEY (`avaliador_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `fk_avaliacao_servico` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `midia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_de_upload` date DEFAULT NULL,
  `midia` longblob NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) NOT NULL,
  `next_val` bigint(20) DEFAULT NULL,

  PRIMARY KEY (`sequence_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `perfil` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `usuario_perfis` (
  `usuario_id` bigint(20) NOT NULL,
  `perfis_id` bigint(20) NOT NULL,

  KEY `fk_usuario_perfis_usuario` (`usuario_id`),
  KEY `fk_usuario_perfis_perfis` (`perfis_id`),

  CONSTRAINT `fk_usuario_perfis_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `fk_usuario_perfis_perfis` FOREIGN KEY (`perfis_id`) REFERENCES `perfil` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `servico_agendado` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_pedido` datetime(6) DEFAULT NULL,
  `data_visita` datetime(6) DEFAULT NULL,
  `is_orcamento` bit(1) DEFAULT NULL,
  `preco_orcamento` decimal(19,2) DEFAULT NULL,
  `preco_servico` decimal(19,2) DEFAULT NULL,
  `contratante_id` bigint(20) DEFAULT NULL,
  `servico_id` bigint(20) DEFAULT NULL,

  PRIMARY KEY (`id`),

  KEY `fk_servico_agendado_contratante` (`contratante_id`),
  KEY `fk_servico_agendado_servico` (`servico_id`),

  CONSTRAINT `fk_servico_agendado_contratante` FOREIGN KEY (`contratante_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `fk_servico_agendado_servico` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

