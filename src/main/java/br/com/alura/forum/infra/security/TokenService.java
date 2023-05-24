package br.com.alura.forum.infra.security;

import br.com.alura.forum.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("{api:security:token:secret}")
    private String senha;
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(senha);
            return JWT.create()
                    .withIssuer("API Forum Alura")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token");
            // Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    public String getSubject(String tokenJWT){
        try {
            Algorithm algorithm = Algorithm.HMAC256(senha);
            return JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer("API Forum Alura")
                    // reusable verifier instance
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido!");
            // Invalid signature/claims
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusMinutes(7).toInstant(ZoneOffset.of("-03:00"));
    }
}
