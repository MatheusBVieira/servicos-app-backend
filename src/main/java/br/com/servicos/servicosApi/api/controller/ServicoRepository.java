package br.com.servicos.servicosApi.api.controller;

import br.com.servicos.servicosApi.domain.model.Servico;
import br.com.servicos.servicosApi.domain.repository.CustomJpaRepository;

public interface ServicoRepository extends CustomJpaRepository<Servico, Long> {

}
