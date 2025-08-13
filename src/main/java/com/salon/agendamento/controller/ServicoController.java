package com.salon.agendamento.controller;

import com.salon.agendamento.model.Servico;
import com.salon.agendamento.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller para gerenciar operações de Serviços
 */
@Controller
@RequestMapping("/servicos")
public class ServicoController {
    
    @Autowired
    private ServicoService servicoService;
    
    /**
     * Lista todos os serviços
     */
    @GetMapping
    public String listar(Model model) {
        List<Servico> servicos = servicoService.listarTodos();
        model.addAttribute("servicos", servicos);
        return "servico/lista";
    }
    
    /**
     * Exibe formulário para novo serviço
     */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("servico", new Servico());
        return "servico/form";
    }
    
    /**
     * Salva um novo serviço
     */
    @PostMapping
    public String salvar(@ModelAttribute Servico servico, RedirectAttributes redirectAttributes) {
        try {
            servicoService.salvar(servico);
            redirectAttributes.addFlashAttribute("mensagem", "Serviço cadastrado com sucesso!");
            return "redirect:/servicos";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/servicos/novo";
        }
    }
    
    /**
     * Exibe formulário para editar serviço
     */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return servicoService.buscarPorId(id)
                .map(servico -> {
                    model.addAttribute("servico", servico);
                    return "servico/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Serviço não encontrado");
                    return "redirect:/servicos";
                });
    }
    
    /**
     * Atualiza um serviço existente
     */
    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Servico servico, 
                           RedirectAttributes redirectAttributes) {
        try {
            servicoService.atualizar(id, servico);
            redirectAttributes.addFlashAttribute("mensagem", "Serviço atualizado com sucesso!");
            return "redirect:/servicos";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/servicos/" + id + "/editar";
        }
    }
    
    /**
     * Exibe detalhes de um serviço
     */
    @GetMapping("/{id}")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return servicoService.buscarPorId(id)
                .map(servico -> {
                    model.addAttribute("servico", servico);
                    return "servico/detalhes";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Serviço não encontrado");
                    return "redirect:/servicos";
                });
    }
    
    /**
     * Ativa um serviço
     */
    @PostMapping("/{id}/ativar")
    public String ativar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            servicoService.ativar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Serviço ativado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/servicos";
    }
    
    /**
     * Desativa um serviço
     */
    @PostMapping("/{id}/desativar")
    public String desativar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            servicoService.desativar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Serviço desativado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/servicos";
    }
    
    /**
     * Deleta um serviço
     */
    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            servicoService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Serviço deletado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/servicos";
    }
    
    /**
     * Busca serviços por nome
     */
    @GetMapping("/buscar")
    public String buscar(@RequestParam String nome, Model model) {
        List<Servico> servicos = servicoService.buscarPorNome(nome);
        model.addAttribute("servicos", servicos);
        model.addAttribute("termoBusca", nome);
        return "servico/lista";
    }
}
