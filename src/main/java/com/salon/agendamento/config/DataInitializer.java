package com.salon.agendamento.config;

import com.salon.agendamento.model.Cliente;
import com.salon.agendamento.model.Servico;
import com.salon.agendamento.repository.ClienteRepository;
import com.salon.agendamento.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Classe para inicializar dados de exemplo no banco de dados
 * 
 * Esta classe implementa CommandLineRunner, que é executada automaticamente
 * quando a aplicação Spring Boot inicia.
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ServicoRepository servicoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Só inicializa se não houver dados
        if (clienteRepository.count() == 0) {
            inicializarClientes();
        }
        
        if (servicoRepository.count() == 0) {
            inicializarServicos();
        }
        
        System.out.println("✅ Dados de exemplo carregados com sucesso!");
    }
    
    /**
     * Inicializa clientes de exemplo
     */
    private void inicializarClientes() {
        Cliente cliente1 = new Cliente("Maria Silva", "maria@email.com", "(11) 99999-1111", "Rua das Flores, 123");
        Cliente cliente2 = new Cliente("João Santos", "joao@email.com", "(11) 99999-2222", "Av. Principal, 456");
        Cliente cliente3 = new Cliente("Ana Costa", "ana@email.com", "(11) 99999-3333", "Rua do Comércio, 789");
        Cliente cliente4 = new Cliente("Pedro Oliveira", "pedro@email.com", "(11) 99999-4444", "Travessa das Palmeiras, 321");
        Cliente cliente5 = new Cliente("Lucia Ferreira", "lucia@email.com", "(11) 99999-5555", "Rua das Acácias, 654");
        
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        clienteRepository.save(cliente3);
        clienteRepository.save(cliente4);
        clienteRepository.save(cliente5);
        
        System.out.println("👥 5 clientes de exemplo criados");
    }
    
    /**
     * Inicializa serviços de exemplo
     */
    private void inicializarServicos() {
        Servico servico1 = new Servico("Corte Feminino", "Corte e finalização para mulheres", new BigDecimal("45.00"), 60);
        Servico servico2 = new Servico("Corte Masculino", "Corte tradicional para homens", new BigDecimal("30.00"), 30);
        Servico servico3 = new Servico("Coloração", "Coloração completa com produtos profissionais", new BigDecimal("120.00"), 120);
        Servico servico4 = new Servico("Manicure", "Cutilagem, esmaltação e hidratação", new BigDecimal("35.00"), 45);
        Servico servico5 = new Servico("Pedicure", "Cutilagem, esmaltação e hidratação dos pés", new BigDecimal("40.00"), 45);
        Servico servico6 = new Servico("Escova", "Escova progressiva ou definitiva", new BigDecimal("80.00"), 90);
        Servico servico7 = new Servico("Hidratação", "Tratamento hidratante profundo", new BigDecimal("60.00"), 60);
        Servico servico8 = new Servico("Pintura", "Pintura simples com secagem", new BigDecimal("50.00"), 45);
        
        servicoRepository.save(servico1);
        servicoRepository.save(servico2);
        servicoRepository.save(servico3);
        servicoRepository.save(servico4);
        servicoRepository.save(servico5);
        servicoRepository.save(servico6);
        servicoRepository.save(servico7);
        servicoRepository.save(servico8);
        
        System.out.println("✂️ 8 serviços de exemplo criados");
    }
}
