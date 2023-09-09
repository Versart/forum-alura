package br.com.alura.forum.util;

import br.com.alura.forum.curso.Curso;

public class CursoCreator {
    

    public static Curso createCourseToBeSaved() {
        
        return Curso.builder()
            .nome("Java")
            .categoria("Linguagem de Programação")
            .build();
    }
}
