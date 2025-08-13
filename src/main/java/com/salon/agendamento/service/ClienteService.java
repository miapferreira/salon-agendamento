package com.salon.agendamento.service;

import com.salon.agendamento.model.Cliente;
import com.salon.agendamento.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar operações relacionadas a Clientes
 * 
 * Esta classe contém a lógica de negócio para clientes.
 * Ela fica entre os controllers (que recebem requisições) e os repositories (que acessam o banco).
 */
@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    /**
     * Salva um novo cliente
     * @param cliente cliente a ser salvo
     * @return cliente salvo com ID gerado
     */
    public Cliente salvar(Cliente cliente) {
        // Validações básicas
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email do cliente é obrigatório");
        }
        
        // Verifica se já existe um cliente com este email
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este email");
        }
        
        // Define a data de cadastro
        cliente.setDataCadastro(LocalDateTime.now());
        
        return clienteRepository.save(cliente);
    }
    
    /**
     * Atualiza um cliente existente
     * @param id ID do cliente
     * @param cliente dados atualizados do cliente
     * @return cliente atualizado
     */
    public Cliente atualizar(Long id, Cliente cliente) {
        Cliente clienteExistente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        
        // Validações básicas
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email do cliente é obrigatório");
        }
        
        // Verifica se o email já existe em outro cliente
        Optional<Cliente> clienteComEmail = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteComEmail.isPresent() && !clienteComEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("Já existe outro cliente cadastrado com este email");
        }
        
        // Atualiza os dados
        clienteExistente.setNome(cliente.getNome());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setTelefone(cliente.getTelefone());
        clienteExistente.setEndereco(cliente.getEndereco());
        
        return clienteRepository.save(clienteExistente);
    }
    
    /**
     * Busca um cliente por ID
     * @param id ID do cliente
     * @return Optional contendo o cliente se encontrado
     */
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    /**
     * Busca um cliente por email
     * @param email email do cliente
     * @return Optional contendo o cliente se encontrado
     */
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    /**
     * Lista todos os clientes ordenados por nome
     * @return lista de clientes
     */
    public List<Cliente> listarTodos() {
        return clienteRepository.findAllByOrderByNomeAsc();
    }
    
    /**
     * Busca clientes por nome
     * @param nome nome ou parte do nome do cliente
     * @return lista de clientes encontrados
     */
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Busca clientes por telefone
     * @param telefone telefone do cliente
     * @return lista de clientes encontrados
     */
    public List<Cliente> buscarPorTelefone(String telefone) {
        return clienteRepository.findByTelefone(telefone);
    }
    
    /**
     * Busca clientes cadastrados em um período
     * @param dataInicio data de início
     * @param dataFim data de fim
     * @return lista de clientes cadastrados no período
     */
    public List<Cliente> buscarPorPeriodoCadastro(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return clienteRepository.findByDataCadastroBetween(dataInicio, dataFim);
    }
    
    /**
     * Deleta um cliente
     * @param id ID do cliente a ser deletado
     */
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }
    
    /**
     * Verifica se um cliente existe
     * @param id ID do cliente
     * @return true se existe, false caso contrário
     */
    public boolean existe(Long id) {
        return clienteRepository.existsById(id);
    }
}
