package prati.projeto.redeSocial.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import prati.projeto.redeSocial.exception.TokenInvalidException;
import prati.projeto.redeSocial.service.CustomUserDetailsService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.tokenValido(token)) {
                String emailToken = jwtService.obterLoginUsuario(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(emailToken);

                if (userDetails == null || !emailToken.equals(userDetails.getUsername())) {
                    throw new TokenInvalidException("Token inválido ou email não corresponde ao usuário.");
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new TokenInvalidException("Token expirado ou inválido.");
            }
        }

        filterChain.doFilter(request, response);
    }
}
