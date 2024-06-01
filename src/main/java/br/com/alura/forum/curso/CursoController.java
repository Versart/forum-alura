package br.com.alura.forum.curso;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final  CursoService cursoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CursoResponse> saveCurso(@Valid @RequestBody CursoRequest cursoRequest) {
        return ResponseEntity.ok(cursoService.saveCurso(cursoRequest));
    }


}
