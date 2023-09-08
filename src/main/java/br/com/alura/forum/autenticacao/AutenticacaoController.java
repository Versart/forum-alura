package br.com.alura.forum.autenticacao;

import br.com.alura.forum.infra.security.DadosTokenJWT;
import br.com.alura.forum.infra.security.TokenService;
import br.com.alura.forum.usuario.DadosAutenticacao;
import br.com.alura.forum.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosTokenJWT> login(@Valid @RequestBody DadosAutenticacao dadosAutenticacao) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.email(),dadosAutenticacao.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
