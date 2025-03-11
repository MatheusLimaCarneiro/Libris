![image](https://github.com/user-attachments/assets/81d116b5-dfdf-4fdb-8957-743e21516142)
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
- **Front-end**: ReactJS
- **Java 17**  
- **Spring Boot 3.4.1**  
- **Spring Security**  
- **JWT**  
- **OAuth2**
- **Banco de Dados**: MySQL
- **Swagger** (Documentação)  

## Funcionalidades  
- Autenticação com JWT e OAuth2  
- CRUD completo para livros, perfis e interações  
- Sistema de notificações em tempo real  
- Fóruns com comentários hierárquicos  
- Controle de status de leitura  
- Sistema de seguidores e relacionamentos  

## Estrutura do Projeto
```bash
src/
└── main/
    └── java/
        └── prati/
            └── projeto/
                └── redeSocial/
                    ├── config/
                    │   ├── CorsConfiguration.java
                    │   ├── PasswordConfig.java
                    │   ├── SecurityConfig.java
                    │   └── SwaggerConfig.java
                    │
                    ├── exception/
                    │   ├── LivroException.java
                    │   ├── RegraNegocioException.java
                    │   └── TokenInvalidException.java
                    │
                    ├── model/
                    │   ├── entity/
                    │   │   ├── AtividadePerfil.java
                    │   │   ├── Avaliacao.java
                    │   │   ├── Comentario.java
                    │   │   ├── ComentarioForum.java
                    │   │   ├── ComentarioResposta.java
                    │   │   ├── CustomOAuth2User.java
                    │   │   ├── CustomUserDetails.java
                    │   │   ├── Favoritos.java
                    │   │   ├── Livro.java
                    │   │   ├── Notificacao.java
                    │   │   ├── Perfil.java
                    │   │   ├── PostForum.java
                    │   │   ├── RelacionamentoSeguidores.java
                    │   │   ├── Resenha.java
                    │   │   ├── ResetPasswordRequest.java
                    │   │   ├── RespostaForum.java
                    │   │   ├── StatusLeitura.java
                    │   │   └── Usuario.java
                    │   │
                    │   └── enums/
                    │       ├── GeneroLiterario.java
                    │       └── StatusLeituraEnum.java
                    │
                    ├── repository/
                    │   ├── AtividadePerfilRepository.java
                    │   ├── AvaliacaoRepository.java
                    │   ├── ComentarioRepository.java
                    │   ├── ComentarioForumRepository.java
                    │   ├── ComentarioRespostaRepository.java
                    │   ├── FavoritosRepository.java
                    │   ├── LivroRepository.java
                    │   ├── NotificacaoRepository.java
                    │   ├── PerfilRepository.java
                    │   ├── PostForumRepository.java
                    │   ├── RelacionamentoSeguidoresRepository.java
                    │   ├── ResenhaRepository.java
                    │   ├── RespostaForumRepository.java
                    │   ├── StatusLeituraRepository.java
                    │   └── UsuarioRepository.java
                    │
                    ├── rest/
                    │   ├── controller/
                    │   │   ├── AtividadePerfilController.java
                    │   │   ├── AvaliacaoController.java
                    │   │   ├── ComentarioController.java
                    │   │   ├── ComentarioForumController.java
                    │   │   ├── ComentarioRespostaController.java
                    │   │   ├── CurtidaController.java
                    │   │   ├── FavoritosController.java
                    │   │   ├── LivroController.java
                    │   │   ├── NotificacaoController.java
                    │   │   ├── OAuth2Controller.java
                    │   │   ├── PerfilController.java
                    │   │   ├── PostForumController.java
                    │   │   ├── RelacionamentoSeguidoresController.java
                    │   │   ├── ResenhaController.java
                    │   │   ├── RespostaForumController.java
                    │   │   ├── StatusLeituraController.java
                    │   │   └── UsuarioController.java
                    │   │
                    │   └── dto/
                    │       ├── AtividadePerfilDTO.java
                    │       ├── AvaliacaoDTO.java
                    │       ├── ComentarioDTO.java
                    │       ├── ComentarioForumRequestDTO.java
                    │       ├── ComentarioForumResponseDTO.java
                    │       ├── ComentarioRequestDTO.java
                    │       ├── CredenciaisDTO.java
                    │       ├── FavoritoRequestDTO.java
                    │       ├── FavoritoResponseDTO.java
                    │       ├── LivroResponseDTO.java
                    │       ├── LivroResumidoDTO.java
                    │       ├── NotificacaoDTO.java
                    │       ├── PerfilDTO.java
                    │       ├── PerfilRequestDTO.java
                    │       ├── PerfilResumidoDTO.java
                    │       ├── PostForumRequestDTO.java
                    │       ├── PostForumResponseDTO.java
                    │       ├── ResenhaDTO.java
                    │       ├── ResenhaViewDTO.java
                    │       ├── ResetPasswordDTO.java
                    │       ├── RespostaDTO.java
                    │       ├── RespostaForumRequestDTO.java
                    │       ├── RespostaForumResponseDTO.java
                    │       ├── StatusLeituraDTO.java
                    │       ├── TokenDTO.java
                    │       └── UsuarioResumidoDTO.java
                    │
                    ├── response/
                    │   ├── ApiErrors.java
                    │   ├── ApplicationControllerAdvice.java
                    │   └── ServiceResponse.java
                    │
                    ├── security/
                    │   ├── JwtAuthenticationFilter.java
                    │   ├── JwtService.java
                    │   └── RsaKeyProvider.java
                    │
                    └── service/
                        ├── AtividadePerfilService.java
                        ├── AvaliacaoService.java
                        ├── ComentarioForumService.java
                        ├── ComentarioRespostaService.java
                        ├── ComentarioService.java
                        ├── CurtidaService.java
                        ├── FavoritoService.java
                        ├── LivroService.java
                        ├── NotificacaoService.java
                        ├── PerfilService.java
                        ├── PostForumService.java
                        ├── RelacionamentoSeguidoresService.java
                        ├── ResenhaService.java
                        ├── RespostaForumService.java
                        ├── StatusLeituraService.java
                        └── UsuarioService.java
                        │
                        ├── auth/
                        │   ├── CustomOAuth2UserService.java
                        │   └── CustomUserDetailsService.java
                        │
                        ├── email/
                        │   └── EmailService.java
                        │
                        └── impl/
                            ├── AtividadePerfilServiceImpl.java
                            ├── AvaliacaoServiceImpl.java
                            ├── ComentarioForumServiceImpl.java
                            ├── ComentarioRespostaServiceImpl.java
                            ├── ComentarioServiceImpl.java
                            ├── CurtidaServiceImpl.java
                            ├── FavoritoServiceImpl.java
                            ├── LivroServiceImpl.java
                            ├── NotificacaoServiceImpl.java
                            ├── PerfilServiceImpl.java
                            ├── PostForumServiceImpl.java
                            ├── RelacionamentoSeguidoresServiceImpl.java
                            ├── ResenhaServiceImpl.java
                            ├── RespostaForumServiceImpl.java
                            ├── StatusLeituraServiceImpl.java
                            └── UsuarioServiceImpl.java

```


## Pré-requisitos

Certifique-se de ter instalado:

- Node.js e npm (para o front-end)
- Java 17+ e Maven (ou outra versão de sua escolha para o back-end)
- MySQL (para o banco de dados)

## ⚙ Instalação  

### Clonagem  
```bash
git clone https://github.com/MatheusLimaCarneiro/Libris-Back.git
cd Libris-Back
````

## Configuração

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

## Contribuidores

[![Luan](https://img.shields.io/badge/Luan-009c9d?logo=github&logoColor=f4f4f4&style=flat)](https://github.com/luan-42)
[![Tainara](https://img.shields.io/badge/Tainara-f4f4f4?logo=github&logoColor=009c9d&style=flat)](https://github.com/tain4ra)
[![Matheus Lima](https://img.shields.io/badge/Matheus_Lima-009c9d?logo=github&logoColor=f4f4f4&style=flat)](https://github.com/MatheusLimaCarneiro)
[![Felipe_Roufman](https://img.shields.io/badge/Felipe_Roufman-f4f4f4?logo=github&logoColor=009c9d&style=flat)](https://github.com/FelipeRoufman)
[![Arthur Gausmann](https://img.shields.io/badge/Arthur_Gausmann-009c9d?logo=github&logoColor=f4f4f4&style=flat)](https://github.com/ArthurGausmann)
[![Jeffson Garreto](https://img.shields.io/badge/Jeffson_Garreto-f4f4f4?logo=github&logoColor=009c9d&style=flat)](https://github.com/garreto9)
[![João Fontes](https://img.shields.io/badge/João_Fontes-009c9d?logo=github&logoColor=f4f4f4&style=flat)](https://github.com/JoaoFontes-debug)
[![Milene Almeida](https://img.shields.io/badge/Milene_Almeida-f4f4f4?logo=github&logoColor=009c9d&style=flat)](https://github.com/Almile)

