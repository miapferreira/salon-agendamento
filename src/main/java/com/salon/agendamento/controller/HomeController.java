package com.salon.agendamento.controller;

import com.salon.agendamento.service.AgendamentoService;
import com.salon.agendamento.service.ClienteService;
import com.salon.agendamento.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller principal da aplicação
 * 
 * Este controller gerencia as páginas principais e o dashboard.
 * Usa Thymeleaf para renderizar as páginas HTML.
 */
@Controller
public class HomeController {
    
    @Autowired
    private AgendamentoService agendamentoService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ServicoService servicoService;
    
    /**
     * Página inicial - Dashboard
     * @param model modelo para passar dados para a view
     * @return nome da view (template HTML)
     */
    @GetMapping("/")
    public String home(Model model) {
        // Busca dados para o dashboard
        model.addAttribute("agendamentosHoje", agendamentoService.listarAgendamentosDeHoje());
        model.addAttribute("agendamentosFuturos", agendamentoService.listarAgendamentosFuturos());
        model.addAttribute("totalClientes", clienteService.listarTodos().size());
        model.addAttribute("totalServicos", servicoService.listarAtivos().size());
        
        return "home";
    }
    
    /**
     * Página sobre o sistema
     * @return nome da view
     */
    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }
}
