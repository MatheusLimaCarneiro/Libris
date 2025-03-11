# Libris - Rede Social Literária (Back-End)

## Descrição 

API para gerenciamento de uma rede social focada em literatura, desenvolvida como Projeto Final do Curso de Desenvolvedor FullStack do +PraTI. Oferece recursos para:  

🔐 **Autenticação de usuários** via JWT e OAuth2 (Google)  
📚 **Gerenciamento de perfis** personalizados com status de leitura  
💬 **Interações sociais** como comentários, fóruns e resenhas  
📌 **Compartilhamento** de atividades literárias e favoritos  

> **Front-End:** [[Link do Front adicionado]](https://github.com/Almile/librisFront)  

## Descrição do Motivo  
Projeto desenvolvido para conclusão do curso, demonstrando habilidades em desenvolvimento back-end com Java e Spring Boot. O foco é criar uma base escalável para uma comunidade de leitores, com ênfase em boas práticas de segurança e arquitetura limpa.

## Tecnologias  
- **Java 17**  
- **Spring Boot 3**  
- **Spring Security**  
- **JWT**  
- **OAuth2**  
- **Swagger** (Documentação)  

## Funcionalidades  
- Autenticação com JWT e OAuth2  
- CRUD completo para livros, perfis e interações  
- Sistema de notificações em tempo real  
- Fóruns com comentários hierárquicos  
- Controle de status de leitura  
- Sistema de seguidores e relacionamentos  

## ⚙ Instalação  

### Clonagem  
```bash
git clone https://github.com/MatheusLimaCarneiro/Libris-Back.git
cd Libris-Back
````

### Configuração

1. Configure as variáveis de ambiente no ```application.properties```:
- Banco de dados
- Chaves JWT
- Credenciais OAuth2

2. Execute com Maven:
```bash
     mvn spring-boot:run
````

3. Acesse a documentação:
   ````bash
   http://localhost:8080/swagger-ui.html
   ````

## 🤝 Contribuições
Siga os passos abaixo para contribuir:

1. Fork do projeto
   
```https://github.com/MatheusLimaCarneiro/Libris-Back.git```

2. Clone seu fork
   
```git clone https://github.com/MatheusLimaCarneiro/Libris-Back.git```

3. Crie uma branch

```git checkout -b feature/nova-feature```

4. Commit das modificações

```git commit -m "Descrição da feature"```

5. Push
   
```git push origin feature/nova-feature```

7. Abra um **Pull Request**

## Autores e Reconhecimento

- **Matheus Lima**  
  - **GitHub:** [MatheusLimaCarneiro](https://github.com/MatheusLimaCarneiro)  
