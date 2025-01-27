package prati.projeto.redeSocial.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.LivrosApplication;
import prati.projeto.redeSocial.modal.entity.Usuario;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.expiration}")
    private Duration expiracao;

    @Value("${jwt.secret}")
    private String chaveAssinatura;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(chaveAssinatura.getBytes());
    }

    public String gerarToken(Usuario usuario) {
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plus(expiracao);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setExpiration(data)
                .signWith(getSigningKey())
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LivrosApplication.class);
        JwtService service = context.getBean(JwtService.class);

        Usuario usuario = Usuario.builder().username("fulano").build();
        String token = service.gerarToken(usuario);
        System.out.println("Token: " + token);

        boolean isTokenValido = service.tokenValido(token);
        System.out.println("O token está válido? " + isTokenValido);
        System.out.println("Usuário do token: " + service.obterLoginUsuario(token));
    }
}
