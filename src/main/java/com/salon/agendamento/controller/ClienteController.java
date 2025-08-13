package com.salon.agendamento.controller;

import com.salon.agendamento.model.Cliente;
import com.salon.agendamento.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller para gerenciar operações de Clientes
 * 
 * Este controller gerencia todas as operações relacionadas a clientes:
 * - Listar clientes
 * - Cadastrar novo cliente
 * - Editar cliente
 * - Deletar cliente
 * - Buscar clientes
 */
@Controller
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    /**
     * Lista todos os clientes
     * @param model modelo para passar dados para a view
     * @return nome da view
     */
    @GetMapping
    public String listar(Model model) {
        List<Cliente> clientes = clienteService.listarTodos();
        model.addAttribute("clientes", clientes);
        return "cliente/lista";
    }
    
    /**
     * Exibe formulário para cadastrar novo cliente
     * @param model modelo para passar dados para a view
     * @return nome da view
     */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/form";
    }
    
    /**
     * Salva um novo cliente
     * @param cliente dados do cliente
     * @param redirectAttributes para mensagens de sucesso/erro
     * @return redirecionamento
     */
    @PostMapping
    public String salvar(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            clienteService.salvar(cliente);
            redirectAttributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso!");
            return "redirect:/clientes";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/clientes/novo";
        }
    }
    
    /**
     * Exibe formulário para editar cliente
     * @param id ID do cliente
     * @param model modelo para passar dados para a view
     * @param redirectAttributes para mensagens de erro
     * @return nome da view
     */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return clienteService.buscarPorId(id)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    return "cliente/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Cliente não encontrado");
                    return "redirect:/clientes";
                });
    }
    
    /**
     * Atualiza um cliente existente
     * @param id ID do cliente
     * @param cliente dados atualizados
     * @param redirectAttributes para mensagens
     * @return redirecionamento
     */
    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Cliente cliente, 
                           RedirectAttributes redirectAttributes) {
        try {
            clienteService.atualizar(id, cliente);
            redirectAttributes.addFlashAttribute("mensagem", "Cliente atualizado com sucesso!");
            return "redirect:/clientes";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/clientes/" + id + "/editar";
        }
    }
    
    /**
     * Exibe detalhes de um cliente
     * @param id ID do cliente
     * @param model modelo para passar dados para a view
     * @param redirectAttributes para mensagens de erro
     * @return nome da view
     */
    @GetMapping("/{id}")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return clienteService.buscarPorId(id)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    return "cliente/detalhes";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Cliente não encontrado");
                    return "redirect:/clientes";
                });
    }
    
    /**
     * Deleta um cliente
     * @param id ID do cliente
     * @param redirectAttributes para mensagens
     * @return redirecionamento
     */
    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Cliente deletado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/clientes";
    }
    
    /**
     * Busca clientes por nome
     * @param nome nome para buscar
     * @param model modelo para passar dados para a view
     * @return nome da view
     */
    @GetMapping("/buscar")
    public String buscar(@RequestParam String nome, Model model) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        model.addAttribute("clientes", clientes);
        model.addAttribute("termoBusca", nome);
        return "cliente/lista";
    }
}
