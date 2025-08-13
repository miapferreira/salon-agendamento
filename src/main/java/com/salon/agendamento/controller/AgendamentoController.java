package com.salon.agendamento.controller;

import com.salon.agendamento.model.Agendamento;
import com.salon.agendamento.service.AgendamentoService;
import com.salon.agendamento.service.ClienteService;
import com.salon.agendamento.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller para gerenciar operações de Agendamentos
 */
@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {
    
    @Autowired
    private AgendamentoService agendamentoService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ServicoService servicoService;
    
    /**
     * Lista todos os agendamentos
     */
    @GetMapping
    public String listar(Model model) {
        List<Agendamento> agendamentos = agendamentoService.listarTodos();
        model.addAttribute("agendamentos", agendamentos);
        return "agendamento/lista";
    }
    
    /**
     * Exibe formulário para novo agendamento
     */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("servicos", servicoService.listarAtivos());
        model.addAttribute("agendamento", new Agendamento());
        return "agendamento/form";
    }
    
    /**
     * Salva um novo agendamento
     */
    @PostMapping
    public String salvar(@RequestParam Long clienteId,
                        @RequestParam Long servicoId,
                        @RequestParam String dataHora,
                        @RequestParam(required = false) String observacoes,
                        RedirectAttributes redirectAttributes) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime dataHoraObj = LocalDateTime.parse(dataHora, formatter);
            
            agendamentoService.criar(clienteId, servicoId, dataHoraObj, observacoes);
            redirectAttributes.addFlashAttribute("mensagem", "Agendamento criado com sucesso!");
            return "redirect:/agendamentos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/agendamentos/novo";
        }
    }
    
    /**
     * Exibe detalhes de um agendamento
     */
    @GetMapping("/{id}")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return agendamentoService.buscarPorId(id)
                .map(agendamento -> {
                    model.addAttribute("agendamento", agendamento);
                    return "agendamento/detalhes";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Agendamento não encontrado");
                    return "redirect:/agendamentos";
                });
    }
    
    /**
     * Cancela um agendamento
     */
    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            agendamentoService.cancelar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Agendamento cancelado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/agendamentos";
    }
    
    /**
     * Confirma um agendamento
     */
    @PostMapping("/{id}/confirmar")
    public String confirmar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            agendamentoService.confirmar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Agendamento confirmado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/agendamentos";
    }
    
    /**
     * Marca agendamento como realizado
     */
    @PostMapping("/{id}/realizar")
    public String realizar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            agendamentoService.marcarComoRealizado(id);
            redirectAttributes.addFlashAttribute("mensagem", "Agendamento marcado como realizado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/agendamentos";
    }
}
