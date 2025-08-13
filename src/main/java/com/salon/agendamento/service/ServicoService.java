package com.salon.agendamento.service;

import com.salon.agendamento.model.Servico;
import com.salon.agendamento.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar operações relacionadas a Serviços
 * 
 * Esta classe contém a lógica de negócio para serviços.
 */
@Service
public class ServicoService {
    
    @Autowired
    private ServicoRepository servicoRepository;
    
    /**
     * Salva um novo serviço
     * @param servico serviço a ser salvo
     * @return serviço salvo com ID gerado
     */
    public Servico salvar(Servico servico) {
        // Validações básicas
        if (servico.getNome() == null || servico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do serviço é obrigatório");
        }
        
        if (servico.getPreco() == null || servico.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço do serviço deve ser maior que zero");
        }
        
        return servicoRepository.save(servico);
    }
    
    /**
     * Atualiza um serviço existente
     * @param id ID do serviço
     * @param servico dados atualizados do serviço
     * @return serviço atualizado
     */
    public Servico atualizar(Long id, Servico servico) {
        Servico servicoExistente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        
        // Validações básicas
        if (servico.getNome() == null || servico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do serviço é obrigatório");
        }
        
        if (servico.getPreco() == null || servico.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço do serviço deve ser maior que zero");
        }
        
        // Atualiza os dados
        servicoExistente.setNome(servico.getNome());
        servicoExistente.setDescricao(servico.getDescricao());
        servicoExistente.setPreco(servico.getPreco());
        servicoExistente.setDuracaoMinutos(servico.getDuracaoMinutos());
        servicoExistente.setAtivo(servico.getAtivo());
        
        return servicoRepository.save(servicoExistente);
    }
    
    /**
     * Busca um serviço por ID
     * @param id ID do serviço
     * @return Optional contendo o serviço se encontrado
     */
    public Optional<Servico> buscarPorId(Long id) {
        return servicoRepository.findById(id);
    }
    
    /**
     * Lista todos os serviços ativos
     * @return lista de serviços ativos
     */
    public List<Servico> listarAtivos() {
        return servicoRepository.findByAtivoTrueOrderByNomeAsc();
    }
    
    /**
     * Lista todos os serviços
     * @return lista de todos os serviços
     */
    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }
    
    /**
     * Busca serviços por nome
     * @param nome nome ou parte do nome do serviço
     * @return lista de serviços encontrados
     */
    public List<Servico> buscarPorNome(String nome) {
        return servicoRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Busca serviços por faixa de preço
     * @param precoMinimo preço mínimo
     * @param precoMaximo preço máximo
     * @return lista de serviços na faixa de preço
     */
    public List<Servico> buscarPorFaixaPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        return servicoRepository.findByPrecoBetweenAndAtivoTrue(precoMinimo, precoMaximo);
    }
    
    /**
     * Ativa um serviço
     * @param id ID do serviço
     * @return serviço ativado
     */
    public Servico ativar(Long id) {
        Servico servico = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        
        servico.setAtivo(true);
        return servicoRepository.save(servico);
    }
    
    /**
     * Desativa um serviço
     * @param id ID do serviço
     * @return serviço desativado
     */
    public Servico desativar(Long id) {
        Servico servico = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        
        servico.setAtivo(false);
        return servicoRepository.save(servico);
    }
    
    /**
     * Deleta um serviço
     * @param id ID do serviço a ser deletado
     */
    public void deletar(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Serviço não encontrado");
        }
        servicoRepository.deleteById(id);
    }
    
    /**
     * Verifica se um serviço existe
     * @param id ID do serviço
     * @return true se existe, false caso contrário
     */
    public boolean existe(Long id) {
        return servicoRepository.existsById(id);
    }
}
