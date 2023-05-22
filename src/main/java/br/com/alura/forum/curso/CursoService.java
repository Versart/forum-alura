package br.com.alura.forum.curso;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;


    public CursoResponse saveCurso(CursoRequest cursoRequest){
       return new CursoResponse(cursoRepository.save(new Curso(cursoRequest)));
    }
}
