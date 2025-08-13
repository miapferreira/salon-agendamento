package com.salon.agendamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação de Agendamento para Salão de Beleza
 * 
 * Esta é a classe que inicia toda a aplicação Spring Boot.
 * A anotação @SpringBootApplication combina:
 * - @Configuration: Marca a classe como fonte de definições de beans
 * - @EnableAutoConfiguration: Diz ao Spring Boot para configurar beans automaticamente
 * - @ComponentScan: Diz ao Spring para procurar por componentes na pasta atual
 */
@SpringBootApplication
public class AgendamentoApplication {

    /**
     * Método principal que inicia a aplicação
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(AgendamentoApplication.class, args);
        System.out.println("🚀 Aplicação de Agendamento iniciada com sucesso!");
        System.out.println("📱 Acesse: http://localhost:8080");
    }
}
