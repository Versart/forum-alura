package br.com.alura.forum.curso;

import br.com.alura.forum.util.CursoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class CursoControllerTest {

    @InjectMocks
    private  CursoController cursoController;

    @Mock
    private  CursoService cursoService;

    @BeforeEach
    void  setup() {
        Mockito.when(cursoService.saveCurso(ArgumentMatchers.any(CursoRequest.class)))
                .thenReturn(CursoCreator.createCourseResponse());

    }

    @Test
    @DisplayName("saveCurso returns CursoResponse when successful")
    void saveCurso_ReturnsCursoResponse_WhenSuccessful() {
        CursoRequest courseToBeSaved = CursoCreator.createCourseRequest();

        CursoResponse courseSaved = cursoController.saveCurso(courseToBeSaved).getBody();

        Assertions.assertThat(courseSaved).isNotNull();

        Assertions.assertThat(courseSaved.id()).isNotNull();
    }



}