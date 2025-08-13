package com.salon.agendamento.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um Agendamento no salão
 * 
 * Esta é a entidade principal que conecta Cliente, Serviço e horário.
 * Cada agendamento representa uma reserva de horário para um serviço.
 */
@Entity
@Table(name = "agendamentos")
public class Agendamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;
    
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
    
    @Column(name = "data_hora_fim")
    private LocalDateTime dataHoraFim;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusAgendamento status = StatusAgendamento.AGENDADO;
    
    @Column(length = 500)
    private String observacoes;
    
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    
    // Enum para status do agendamento
    public enum StatusAgendamento {
        AGENDADO("Agendado"),
        CONFIRMADO("Confirmado"),
        CANCELADO("Cancelado"),
        REALIZADO("Realizado"),
        NAO_COMPARECEU("Não Compareceu");
        
        private final String descricao;
        
        StatusAgendamento(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Agendamento() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    public Agendamento(Cliente cliente, Servico servico, LocalDateTime dataHora) {
        this();
        this.cliente = cliente;
        this.servico = servico;
        this.dataHora = dataHora;
        this.valor = servico.getPreco();
        
        // Calcula o horário de fim baseado na duração do serviço
        if (servico.getDuracaoMinutos() != null) {
            this.dataHoraFim = dataHora.plusMinutes(servico.getDuracaoMinutos());
        }
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Servico getServico() {
        return servico;
    }
    
    public void setServico(Servico servico) {
        this.servico = servico;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    
    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }
    
    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
    
    public BigDecimal getValor() {
        return valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public StatusAgendamento getStatus() {
        return status;
    }
    
    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    @Override
    public String toString() {
        return "Agendamento{" +
                "id=" + id +
                ", cliente=" + cliente.getNome() +
                ", servico=" + servico.getNome() +
                ", dataHora=" + dataHora +
                ", status=" + status +
                '}';
    }
}
