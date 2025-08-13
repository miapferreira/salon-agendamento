package com.salon.agendamento.repository;

import com.salon.agendamento.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para a entidade Cliente
 * 
 * Esta interface estende JpaRepository, que fornece métodos básicos de CRUD:
 * - save(): salvar/atualizar
 * - findById(): buscar por ID
 * - findAll(): buscar todos
 * - delete(): deletar
 * - etc.
 * 
 * O Spring Data JPA implementa automaticamente estes métodos.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Busca cliente por email
     * @param email email do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Busca clientes por nome (ignorando maiúsculas/minúsculas)
     * @param nome nome do cliente
     * @return lista de clientes com o nome especificado
     */
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca clientes por telefone
     * @param telefone telefone do cliente
     * @return lista de clientes com o telefone especificado
     */
    List<Cliente> findByTelefone(String telefone);
    
    /**
     * Busca clientes ordenados por nome
     * @return lista de clientes ordenados alfabeticamente
     */
    List<Cliente> findAllByOrderByNomeAsc();
    
    /**
     * Busca clientes cadastrados em um período específico
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de clientes cadastrados no período
     */
    @Query("SELECT c FROM Cliente c WHERE c.dataCadastro BETWEEN :dataInicio AND :dataFim")
    List<Cliente> findByDataCadastroBetween(@Param("dataInicio") java.time.LocalDateTime dataInicio,
                                           @Param("dataFim") java.time.LocalDateTime dataFim);
    
    /**
     * Verifica se existe um cliente com o email especificado
     * @param email email a ser verificado
     * @return true se o email já existe, false caso contrário
     */
    boolean existsByEmail(String email);
}
