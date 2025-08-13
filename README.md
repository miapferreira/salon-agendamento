# 💇‍♀️ Sistema de Agendamento - Salão de Beleza

Um sistema completo de agendamento para salões de beleza desenvolvido em Java com Spring Boot.

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.2.0** - Framework para aplicações Java
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em memória
- **Thymeleaf** - Template engine para HTML
- **Bootstrap 5** - Framework CSS para interface
- **Maven** - Gerenciador de dependências

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- Navegador web moderno

## 🛠️ Como Executar

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd salon-agendamento
```

### 2. Execute a aplicação
```bash
mvn spring-boot:run
```

### 3. Acesse a aplicação
Abra seu navegador e acesse: `http://localhost:8080`

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/salon/agendamento/
│   │   ├── AgendamentoApplication.java     # Classe principal
│   │   ├── controller/                     # Controladores web
│   │   │   ├── HomeController.java
│   │   │   └── ClienteController.java
│   │   ├── model/                          # Entidades (modelos)
│   │   │   ├── Cliente.java
│   │   │   ├── Servico.java
│   │   │   └── Agendamento.java
│   │   ├── repository/                     # Repositórios (acesso a dados)
│   │   │   ├── ClienteRepository.java
│   │   │   ├── ServicoRepository.java
│   │   │   └── AgendamentoRepository.java
│   │   └── service/                        # Serviços (lógica de negócio)
│   │       ├── ClienteService.java
│   │       ├── ServicoService.java
│   │       └── AgendamentoService.java
│   └── resources/
│       ├── templates/                      # Templates HTML
│       │   ├── home.html
│       │   └── layout.html
│       └── application.properties          # Configurações
└── test/                                   # Testes (a implementar)
```

## 🎯 Funcionalidades

### ✅ Implementadas
- **Dashboard** - Visão geral do sistema
- **Gestão de Clientes** - CRUD completo
- **Gestão de Serviços** - CRUD completo
- **Agendamentos** - Criação e gestão
- **Validações** - Regras de negócio
- **Interface Responsiva** - Bootstrap 5

### 🔄 Em Desenvolvimento
- **AgendamentoController** - Interface para agendamentos
- **ServicoController** - Interface para serviços
- **Templates HTML** - Páginas completas
- **Testes Unitários** - Cobertura de testes

## 🗄️ Banco de Dados

O projeto usa **H2 Database** em memória, que é perfeito para desenvolvimento e aprendizado:

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:salon_db`
- **Usuário**: `sa`
- **Senha**: (deixe em branco)

## 📚 Conceitos Java/Spring Aprendidos

### 1. **Anotações Spring**
- `@SpringBootApplication` - Marca a classe principal
- `@Controller` - Define um controller web
- `@Service` - Define um serviço
- `@Repository` - Define um repositório
- `@Entity` - Marca uma classe como entidade JPA
- `@Autowired` - Injeção de dependência

### 2. **JPA/Hibernate**
- `@Table` - Define nome da tabela
- `@Column` - Define propriedades da coluna
- `@Id` - Marca chave primária
- `@GeneratedValue` - Geração automática de ID
- `@ManyToOne` - Relacionamento muitos-para-um

### 3. **Spring Data JPA**
- `JpaRepository` - Interface com métodos CRUD
- Query methods - Métodos de busca automáticos
- `@Query` - Consultas personalizadas

### 4. **Spring MVC**
- `@GetMapping` - Mapeia requisições GET
- `@PostMapping` - Mapeia requisições POST
- `@PathVariable` - Captura parâmetros da URL
- `@ModelAttribute` - Vincula dados do formulário

### 5. **Thymeleaf**
- `th:text` - Exibe texto
- `th:each` - Loop
- `th:if` - Condicional
- `th:href` - Links dinâmicos

## 🎨 Interface do Usuário

A interface foi desenvolvida com **Bootstrap 5** e inclui:

- **Sidebar** - Navegação lateral
- **Dashboard** - Estatísticas e visão geral
- **Cards** - Layout moderno
- **Tabelas** - Dados organizados
- **Formulários** - Entrada de dados
- **Alertas** - Mensagens de feedback

## 🔧 Configurações

### application.properties
```properties
# Banco de dados H2
spring.datasource.url=jdbc:h2:mem:salon_db
spring.h2.console.enabled=true

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Thymeleaf
spring.thymeleaf.cache=false

# Servidor
server.port=8080
```

## 🚀 Próximos Passos

1. **Implementar AgendamentoController**
2. **Implementar ServicoController**
3. **Criar templates HTML completos**
4. **Adicionar validações de formulário**
5. **Implementar testes unitários**
6. **Adicionar autenticação de usuários**
7. **Implementar relatórios**
8. **Adicionar notificações**

## 📖 Aprendizado

Este projeto é ideal para aprender:

- **Java 17** - Recursos modernos da linguagem
- **Spring Boot** - Framework enterprise
- **JPA/Hibernate** - Persistência de dados
- **Spring MVC** - Desenvolvimento web
- **Thymeleaf** - Templates HTML
- **Bootstrap** - Interface responsiva
- **Maven** - Gerenciamento de dependências
- **Arquitetura em Camadas** - Separação de responsabilidades

## 🤝 Contribuição

Este é um projeto educacional. Sinta-se à vontade para:

- Fazer fork do projeto
- Implementar novas funcionalidades
- Corrigir bugs
- Melhorar a documentação
- Adicionar testes

## 👨‍💻 Desenvolvedor

**Michel Ferreira**  
📧 mi.apferreira@gmail.com

Este projeto foi desenvolvido como um sistema completo de agendamento para salões de beleza, demonstrando o uso de tecnologias modernas como Spring Boot, JPA/Hibernate e Thymeleaf.

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

---
