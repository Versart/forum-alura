package br.com.alura.forum.topico;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<TopicoResponse>> getTopicos(Pageable pageable) {
        return ResponseEntity.ok(topicoService.getTopicos(pageable));
    }
}
