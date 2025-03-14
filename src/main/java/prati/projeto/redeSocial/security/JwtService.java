package prati.projeto.redeSocial.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.LivrosApplication;
import prati.projeto.redeSocial.modal.entity.CustomOAuth2User;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.rest.dto.TokenDTO;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private RsaKeyProvider rsaKeyProvider;

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    @Value("${jwt.expiration}")
    private Duration expiracao;

    @Value("${jwt.refresh.expiration}")
    private Duration refreshExpiracao;

    public String gerarToken(Usuario usuario) {
        return criarToken(usuario.getEmail(), expiracao);
    }

    public String gerarRefreshToken(Usuario usuario) {
        return criarToken(usuario.getEmail(), refreshExpiracao);
    }

    public String gerarTokenO2auth(CustomOAuth2User oauth2User) {
        return criarToken(oauth2User.getUsername(), expiracao);
    }

    public String gerarRefreshTokenO2auth(CustomOAuth2User oauth2User) {
        return criarToken(oauth2User.getUsername(), refreshExpiracao);
    }

    private String criarToken(String subject, Duration duracao) {
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plus(duracao);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(data)
                .signWith(rsaKeyProvider.getPrivateKey())
                .compact();
    }

    public String criarTokenEmail(String subject, Duration duracao) {
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plus(duracao);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(data)
                .signWith(rsaKeyProvider.getPrivateKey())
                .compact();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            logger.info("Claims do token: " + claims);
            return !LocalDateTime.now().isAfter(obterDataExpiracao(token)) &&
                    claims.getSubject() != null;
        } catch (Exception e) {
            logger.error("Erro ao validar token: " + e.getMessage(), e);
            return false;
        }
    }

    public String obterLoginUsuario(String token) {
        return obterClaims(token).getSubject();
    }

    public TokenDTO gerarTokensParaUsuario(Usuario usuario) {
        String accessToken = gerarToken(usuario);
        String refreshToken = gerarRefreshToken(usuario);
        return new TokenDTO(usuario.getEmail(), accessToken, refreshToken);
    }

    private LocalDateTime obterDataExpiracao(String token) {
        Date dataExpiracao = obterClaims(token).getExpiration();
        return dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private Claims obterClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(rsaKeyProvider.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LivrosApplication.class);
        try {
            RsaKeyProvider rsaKeyProvider = new RsaKeyProvider();  // Instanciando manualmente para testar
            System.out.println("Chaves carregadas com sucesso!");
            System.out.println("Chave Privada: " + rsaKeyProvider.getPrivateKey());
            System.out.println("Chave Pública: " + rsaKeyProvider.getPublicKey());
        } catch (Exception e) {
            System.err.println("Erro ao carregar as chaves: " + e.getMessage());
            e.printStackTrace();
        }

        JwtService service = context.getBean(JwtService.class);

        Usuario usuario = Usuario.builder().email("fulano@gmail.com").build();
        String token = service.gerarToken(usuario);
        System.out.println("Token: " + token);

        boolean isTokenValido = service.tokenValido(token);
        System.out.println("O token está válido? " + isTokenValido);
        System.out.println("Usuário do token: " + service.obterLoginUsuario(token));
    }
}
