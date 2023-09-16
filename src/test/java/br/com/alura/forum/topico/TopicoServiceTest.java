package br.com.alura.forum.topico;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.forum.curso.CursoRepository;
import br.com.alura.forum.infra.security.TokenService;
import br.com.alura.forum.usuario.UsuarioRepository;
import br.com.alura.forum.util.CursoCreator;
import br.com.alura.forum.util.TopicoCreator;
import br.com.alura.forum.util.UsuarioCreator;

@ExtendWith(SpringExtension.class)
public class TopicoServiceTest {

    @InjectMocks
    TopicoService topicoService;

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private SecurityContextHolder securityContextHolder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setup() {
        Page<Topico> pageTopico = new PageImpl<>(List.of(TopicoCreator.createValidTopic()));
        BDDMockito.when(topicoRepository.save(ArgumentMatchers.any(Topico.class)))
            .thenReturn(TopicoCreator.createValidTopic());
        BDDMockito.when(cursoRepository.findCursoByNome(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(CursoCreator.createValidCourse()));
        BDDMockito.when(usuarioRepository.findByEmail(ArgumentMatchers.anyString()))
            .thenReturn(UsuarioCreator.createValidUser());
        BDDMockito.when(authentication.getName()).thenReturn("maria@gmail.com");
        BDDMockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContextHolder.setContext(securityContext);
        BDDMockito.when(topicoRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
            .thenReturn(pageTopico);
        BDDMockito.when(topicoRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(TopicoCreator.createValidTopic()));
        BDDMockito.doNothing().when(topicoRepository).deleteById(ArgumentMatchers.anyLong());
          
    }

    @Test
    @DisplayName("saveTopico returns topic response when successful")
    void saveTopico_ReturnsTopicResponse_WhenSuccessful () {
          TopicoResponse topicoResponse =  topicoService.saveTopico(TopicoCreator.createTopicRequestToBeSaved());
          Assertions.assertThat(topicoResponse).isNotNull();
          Assertions.assertThat(topicoResponse.id()).isNotNull();
          Assertions.assertThat(topicoResponse.autor()).isNotNull();
    }

    @Test
    @DisplayName("getTopicos returns page of topicoResponse when successful")
    void getTopicos_ReturnsPageOfTopicoRsponse_WhenSuccessful() {
        String ExpectedAuthorName = TopicoCreator.createValidTopic().getAutor().getNome();
        Page<TopicoResponse> page = topicoService.getTopicos(PageRequest.of(1, 1));
        Assertions.assertThat(page).isNotNull()
            .isNotEmpty()
            .hasSize(1);
        
        Assertions.assertThat(page.getContent().get(0).autor()).contains(ExpectedAuthorName);
    }

    @Test
    @DisplayName("getTopicoById returns topicResponse when successful")
    void getTopicoById_ReturnsTopicResponse_WhenSuccessful() {
        Long idExpected = TopicoCreator.createValidTopic().getId();
        TopicoResponse topicoResponse = topicoService.getTopicoById(idExpected);
        
        Assertions.assertThat(topicoResponse).isNotNull();
        Assertions.assertThat(topicoResponse.id()).isEqualTo(idExpected);
    }

    @Test
    @DisplayName("updateTopicoById returns altered topic when successful")
    void updateTopicoById_ReturnsAlteredTopic_WhenSuccessful() {
        Long idExpected = TopicoCreator.createValidTopic().getId();
        TopicoResponse alteredTopic = topicoService.updateTopicoById(idExpected, TopicoCreator.createTopicRequestAltered());
        
        Assertions.assertThat(alteredTopic).isNotNull();
        Assertions.assertThat(alteredTopic.id()).isEqualTo(idExpected);
    }

    @Test
    @DisplayName("deletById removes topic when successful")
    void deleteById_RemoveTopic_WhenSuccessful() {
        Assertions.assertThatCode(() -> topicoService.deleteById(1l))
            .doesNotThrowAnyException();   
    }


   
}
