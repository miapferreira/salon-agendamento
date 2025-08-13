package com.salon.agendamento.repository;

import com.salon.agendamento.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para a entidade Agendamento
 * 
 * Esta interface fornece métodos para acessar dados de agendamentos no banco.
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    /**
     * Busca agendamentos por cliente
     * @param clienteId ID do cliente
     * @return lista de agendamentos do cliente
     */
    List<Agendamento> findByClienteIdOrderByDataHoraDesc(Long clienteId);
    
    /**
     * Busca agendamentos por status
     * @param status status do agendamento
     * @return lista de agendamentos com o status especificado
     */
    List<Agendamento> findByStatusOrderByDataHoraAsc(Agendamento.StatusAgendamento status);
    
    /**
     * Busca agendamentos em um período específico
     * @param dataInicio data de início
     * @param dataFim data de fim
     * @return lista de agendamentos no período
     */
    List<Agendamento> findByDataHoraBetweenOrderByDataHoraAsc(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    /**
     * Busca agendamentos de hoje
     * @param hoje data de hoje
     * @return lista de agendamentos de hoje
     */
    @Query("SELECT a FROM Agendamento a WHERE CAST(a.dataHora AS date) = CAST(:hoje AS date) ORDER BY a.dataHora ASC")
    List<Agendamento> findAgendamentosDeHoje(@Param("hoje") LocalDateTime hoje);
    
    /**
     * Busca agendamentos futuros
     * @param agora momento atual
     * @return lista de agendamentos futuros
     */
    @Query("SELECT a FROM Agendamento a WHERE a.dataHora > :agora ORDER BY a.dataHora ASC")
    List<Agendamento> findAgendamentosFuturos(@Param("agora") LocalDateTime agora);
    
    /**
     * Verifica se existe conflito de horário
     * @param dataHora data e hora do agendamento
     * @param dataHoraFim data e hora de fim do agendamento
     * @param idAgendamento ID do agendamento (para excluir na verificação de edição)
     * @return true se há conflito, false caso contrário
     */
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE " +
           "((a.dataHora BETWEEN :dataHora AND :dataHoraFim) OR " +
           "(a.dataHoraFim BETWEEN :dataHora AND :dataHoraFim) OR " +
           "(:dataHora BETWEEN a.dataHora AND a.dataHoraFim)) AND " +
           "a.status NOT IN ('CANCELADO', 'NAO_COMPARECEU') AND " +
           "(:idAgendamento IS NULL OR a.id != :idAgendamento)")
    boolean existsConflitoHorario(@Param("dataHora") LocalDateTime dataHora,
                                  @Param("dataHoraFim") LocalDateTime dataHoraFim,
                                  @Param("idAgendamento") Long idAgendamento);
    
    /**
     * Busca agendamentos por serviço
     * @param servicoId ID do serviço
     * @return lista de agendamentos do serviço
     */
    List<Agendamento> findByServicoIdOrderByDataHoraDesc(Long servicoId);
    
    /**
     * Busca agendamentos ordenados por data/hora (mais recentes primeiro)
     * @return lista de agendamentos ordenados
     */
    List<Agendamento> findAllByOrderByDataHoraDesc();
}
