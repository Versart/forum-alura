package br.com.alura.forum.util;

import br.com.alura.forum.curso.Curso;
import br.com.alura.forum.curso.CursoRequest;
import br.com.alura.forum.curso.CursoResponse;

public class CursoCreator {
    

    public static Curso createCourseToBeSaved() {
        
        return Curso.builder()
            .nome("Java")
            .categoria("Linguagem de Programação")
            .build();
    }

    public static Curso createValidCourse() {
         return Curso.builder()
            .id(1l)
            .nome("Java")
            .categoria("Linguagem de Programação")
            .build();
    }

    public static CursoResponse createCourseResponse() {
        return  CursoResponse.builder()
                .id(1l)
                .nome("Java")
                .categoria("Linguagem de Programação")
                .build();
    }

    public static CursoRequest createCourseRequest() {
        return CursoRequest.builder()
                .nome("Java")
                .categoria("Linguagem de Programação")
                .build();
    }
}
