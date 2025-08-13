package com.salon.agendamento.service;

import com.salon.agendamento.model.Agendamento;
import com.salon.agendamento.model.Cliente;
import com.salon.agendamento.model.Servico;
import com.salon.agendamento.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar operações relacionadas a Agendamentos
 * 
 * Esta classe contém a lógica de negócio para agendamentos,
 * incluindo validações de horários e regras de negócio.
 */
@Service
public class AgendamentoService {
    
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ServicoService servicoService;
    
    /**
     * Cria um novo agendamento
     * @param clienteId ID do cliente
     * @param servicoId ID do serviço
     * @param dataHora data e hora do agendamento
     * @param observacoes observações do agendamento
     * @return agendamento criado
     */
    public Agendamento criar(Long clienteId, Long servicoId, LocalDateTime dataHora, String observacoes) {
        // Busca cliente e serviço
        Cliente cliente = clienteService.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        
        Servico servico = servicoService.buscarPorId(servicoId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        
        // Validações de negócio
        validarHorarioAgendamento(dataHora);
        validarConflitoHorario(dataHora, servico, null);
        
        // Cria o agendamento
        Agendamento agendamento = new Agendamento(cliente, servico, dataHora);
        agendamento.setObservacoes(observacoes);
        
        return agendamentoRepository.save(agendamento);
    }
    
    /**
     * Atualiza um agendamento existente
     * @param id ID do agendamento
     * @param clienteId ID do cliente
     * @param servicoId ID do serviço
     * @param dataHora nova data e hora
     * @param observacoes novas observações
     * @return agendamento atualizado
     */
    public Agendamento atualizar(Long id, Long clienteId, Long servicoId, LocalDateTime dataHora, String observacoes) {
        Agendamento agendamento = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        
        // Busca cliente e serviço
        Cliente cliente = clienteService.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        
        Servico servico = servicoService.buscarPorId(servicoId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        
        // Validações de negócio
        validarHorarioAgendamento(dataHora);
        validarConflitoHorario(dataHora, servico, id);
        
        // Atualiza os dados
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setDataHora(dataHora);
        agendamento.setValor(servico.getPreco());
        agendamento.setObservacoes(observacoes);
        
        // Recalcula o horário de fim
        if (servico.getDuracaoMinutos() != null) {
            agendamento.setDataHoraFim(dataHora.plusMinutes(servico.getDuracaoMinutos()));
        }
        
        return agendamentoRepository.save(agendamento);
    }
    
    /**
     * Atualiza o status de um agendamento
     * @param id ID do agendamento
     * @param status novo status
     * @return agendamento atualizado
     */
    public Agendamento atualizarStatus(Long id, Agendamento.StatusAgendamento status) {
        Agendamento agendamento = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        
        agendamento.setStatus(status);
        return agendamentoRepository.save(agendamento);
    }
    
    /**
     * Busca um agendamento por ID
     * @param id ID do agendamento
     * @return Optional contendo o agendamento se encontrado
     */
    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }
    
    /**
     * Lista todos os agendamentos
     * @return lista de agendamentos
     */
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAllByOrderByDataHoraDesc();
    }
    
    /**
     * Lista agendamentos de hoje
     * @return lista de agendamentos de hoje
     */
    public List<Agendamento> listarAgendamentosDeHoje() {
        return agendamentoRepository.findAgendamentosDeHoje(LocalDateTime.now());
    }
    
    /**
     * Lista agendamentos futuros
     * @return lista de agendamentos futuros
     */
    public List<Agendamento> listarAgendamentosFuturos() {
        return agendamentoRepository.findAgendamentosFuturos(LocalDateTime.now());
    }
    
    /**
     * Lista agendamentos por cliente
     * @param clienteId ID do cliente
     * @return lista de agendamentos do cliente
     */
    public List<Agendamento> listarPorCliente(Long clienteId) {
        return agendamentoRepository.findByClienteIdOrderByDataHoraDesc(clienteId);
    }
    
    /**
     * Lista agendamentos por status
     * @param status status dos agendamentos
     * @return lista de agendamentos com o status especificado
     */
    public List<Agendamento> listarPorStatus(Agendamento.StatusAgendamento status) {
        return agendamentoRepository.findByStatusOrderByDataHoraAsc(status);
    }
    
    /**
     * Lista agendamentos em um período
     * @param dataInicio data de início
     * @param dataFim data de fim
     * @return lista de agendamentos no período
     */
    public List<Agendamento> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return agendamentoRepository.findByDataHoraBetweenOrderByDataHoraAsc(dataInicio, dataFim);
    }
    
    /**
     * Cancela um agendamento
     * @param id ID do agendamento
     * @return agendamento cancelado
     */
    public Agendamento cancelar(Long id) {
        return atualizarStatus(id, Agendamento.StatusAgendamento.CANCELADO);
    }
    
    /**
     * Confirma um agendamento
     * @param id ID do agendamento
     * @return agendamento confirmado
     */
    public Agendamento confirmar(Long id) {
        return atualizarStatus(id, Agendamento.StatusAgendamento.CONFIRMADO);
    }
    
    /**
     * Marca um agendamento como realizado
     * @param id ID do agendamento
     * @return agendamento realizado
     */
    public Agendamento marcarComoRealizado(Long id) {
        return atualizarStatus(id, Agendamento.StatusAgendamento.REALIZADO);
    }
    
    /**
     * Deleta um agendamento
     * @param id ID do agendamento
     */
    public void deletar(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new IllegalArgumentException("Agendamento não encontrado");
        }
        agendamentoRepository.deleteById(id);
    }
    
    /**
     * Valida se o horário do agendamento é válido
     * @param dataHora data e hora a ser validada
     */
    private void validarHorarioAgendamento(LocalDateTime dataHora) {
        LocalDateTime agora = LocalDateTime.now();
        
        // Não permite agendamentos no passado
        if (dataHora.isBefore(agora)) {
            throw new IllegalArgumentException("Não é possível agendar para um horário no passado");
        }
        
        // Não permite agendamentos muito no futuro (exemplo: mais de 1 ano)
        if (dataHora.isAfter(agora.plusYears(1))) {
            throw new IllegalArgumentException("Não é possível agendar para mais de 1 ano no futuro");
        }
        
        // Verifica se é um horário comercial (8h às 18h)
        int hora = dataHora.getHour();
        if (hora < 8 || hora >= 18) {
            throw new IllegalArgumentException("Horário de funcionamento: 8h às 18h");
        }
    }
    
    /**
     * Valida se há conflito de horário
     * @param dataHora data e hora do agendamento
     * @param servico serviço a ser agendado
     * @param idAgendamento ID do agendamento (para exclusão na edição)
     */
    private void validarConflitoHorario(LocalDateTime dataHora, Servico servico, Long idAgendamento) {
        LocalDateTime dataHoraFim = dataHora;
        if (servico.getDuracaoMinutos() != null) {
            dataHoraFim = dataHora.plusMinutes(servico.getDuracaoMinutos());
        }
        
        if (agendamentoRepository.existsConflitoHorario(dataHora, dataHoraFim, idAgendamento)) {
            throw new IllegalArgumentException("Já existe um agendamento neste horário");
        }
    }
}
