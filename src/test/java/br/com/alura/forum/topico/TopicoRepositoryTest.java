package br.com.alura.forum.topico;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.forum.curso.Curso;
import br.com.alura.forum.curso.CursoRepository;
import br.com.alura.forum.usuario.Usuario;
import br.com.alura.forum.usuario.UsuarioRepository;
import br.com.alura.forum.util.CursoCreator;
import br.com.alura.forum.util.TopicoCreator;
import br.com.alura.forum.util.UsuarioCreator;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TopicoRepositoryTest {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Save creates topic when successful")
    public void save_PersistTopic_WhenSuccessful() {

       Curso courseSaved = cursoRepository.save(CursoCreator.createCourseToBeSaved());

       Usuario usuarioSaved = usuarioRepository.save(UsuarioCreator.createUserToBeSaved());

       Topico topicoToBeSaved =  TopicoCreator.createTopicToBeSaved();

       topicoToBeSaved.setAutor(usuarioSaved);
       topicoToBeSaved.setCurso(courseSaved);

       Topico topicoSaved = topicoRepository.save(topicoToBeSaved);

       Assertions.assertThat(topicoSaved).isNotNull();
       
       Assertions.assertThat(topicoSaved.getId()).isNotNull();

       Assertions.assertThat(topicoSaved.getCurso()).isEqualTo(topicoToBeSaved.getCurso());

       Assertions.assertThat(topicoSaved.getAutor()).isEqualTo(topicoToBeSaved.getAutor());
    }
}
