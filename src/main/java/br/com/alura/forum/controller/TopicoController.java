package br.com.alura.forum.controller;

import br.com.alura.forum.topico.TopicoRequest;
import br.com.alura.forum.topico.TopicoResponse;
import br.com.alura.forum.topico.TopicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService topicoService;
    @PostMapping
    public ResponseEntity<TopicoResponse> saveTopico(@Valid @RequestBody TopicoRequest topicoRequest,
                                                     @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(topicoService.saveTopico(topicoRequest,token),HttpStatus.CREATED);
    }

    @GetMapping
    public String dados(
            @RequestHeader(name = "Authorization") String token
    ) {
        return token;
    }
}
