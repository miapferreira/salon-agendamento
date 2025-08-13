package com.salon.agendamento.repository;

import com.salon.agendamento.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para a entidade Servico
 * 
 * Esta interface fornece métodos para acessar dados de serviços no banco.
 */
@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    
    /**
     * Busca serviços ativos
     * @return lista de serviços ativos
     */
    List<Servico> findByAtivoTrue();
    
    /**
     * Busca serviços por nome (ignorando maiúsculas/minúsculas)
     * @param nome nome do serviço
     * @return lista de serviços com o nome especificado
     */
    List<Servico> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca serviços ativos ordenados por nome
     * @return lista de serviços ativos ordenados alfabeticamente
     */
    List<Servico> findByAtivoTrueOrderByNomeAsc();
    
    /**
     * Busca serviços por faixa de preço
     * @param precoMinimo preço mínimo
     * @param precoMaximo preço máximo
     * @return lista de serviços na faixa de preço especificada
     */
    List<Servico> findByPrecoBetweenAndAtivoTrue(java.math.BigDecimal precoMinimo, 
                                                 java.math.BigDecimal precoMaximo);
}
