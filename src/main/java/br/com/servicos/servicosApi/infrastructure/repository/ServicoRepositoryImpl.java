package br.com.servicos.servicosApi.infrastructure.repository;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.Servico;
import br.com.servicos.servicosApi.domain.repository.ServicoRepositoryQueries;
import lombok.var;

@Repository
public class ServicoRepositoryImpl implements ServicoRepositoryQueries {
	
	private static final String CALCULA_DISTANCIA_KM = "111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(s.prestadorServico.endereco.latitude)) * COS(RADIANS(:latitude)) * COS(RADIANS(s.prestadorServico.endereco.longitude - :longitude)) + SIN(RADIANS(s.prestadorServico.endereco.latitude)) * SIN(RADIANS(:latitude)))))";
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Servico> find(Categoria categoria, Double latitude, Double longitude, Pageable paginacao) {
		var jpql = new StringBuilder();
		jpql.append("SELECT new Servico(s, COALESCE(avg(a.nota),0) as notaMedia");
		
		var parametros = new HashMap<String, Object>();
		
		if (verificaLatitudeELongitude(latitude, longitude)) {
			jpql.append(", " + CALCULA_DISTANCIA_KM + " AS distanciaKM");
			parametros.put("latitude", latitude);
			parametros.put("longitude", longitude);
		}
		
		jpql.append(") from Servico as s left join Avaliacao a on s.categoria=:categoria where s.categoria=:categoria group by s.id, s.prestadorServico.endereco.latitude, s.prestadorServico.endereco.longitude, s.distanciaMaxima");
		
		if (verificaLatitudeELongitude(latitude, longitude)) {
			jpql.append(" having (" + CALCULA_DISTANCIA_KM + ") < s.distanciaMaxima");
		}
		
		parametros.put("categoria", categoria);
		
		TypedQuery<Servico> query = manager.createQuery(jpql.toString(), Servico.class).setMaxResults(paginacao.getPageSize()).setFirstResult((int) paginacao.getOffset());
		
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
		
		return query.getResultList();
	}

	private boolean verificaLatitudeELongitude(Double latitude, Double longitude) {
		return latitude != null && longitude != null;
	}

}
