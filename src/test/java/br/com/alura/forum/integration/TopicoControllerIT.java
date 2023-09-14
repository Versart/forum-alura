package br.com.alura.forum.integration;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.alura.forum.curso.Curso;
import br.com.alura.forum.curso.CursoRepository;
import br.com.alura.forum.infra.security.DadosTokenJWT;
import br.com.alura.forum.topico.AlteredTopic;
import br.com.alura.forum.topico.Topico;
import br.com.alura.forum.topico.TopicoRepository;
import br.com.alura.forum.topico.TopicoRequest;
import br.com.alura.forum.topico.TopicoResponse;
import br.com.alura.forum.usuario.DadosAutenticacao;
import br.com.alura.forum.usuario.Usuario;
import br.com.alura.forum.usuario.UsuarioRepository;
import br.com.alura.forum.usuario.UsuarioRequest;
import br.com.alura.forum.util.AutenticacaoCreator;
import br.com.alura.forum.util.CursoCreator;
import br.com.alura.forum.util.TopicoCreator;
import br.com.alura.forum.util.UsuarioCreator;
import br.com.alura.forum.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TopicoControllerIT {
    
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    

    @Test
    @DisplayName("SaveTopico returns a topicoResponse when successful")
    void  saveTopico_ReturnsTopicoResponse_WhenSuccessful() {
       DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
       DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
       HttpHeaders httpHeaders = new HttpHeaders();
       httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());

       TopicoRequest topicoToBeSaved = TopicoCreator.createTopicRequestToBeSaved();
       ResponseEntity<TopicoResponse> exchange = testRestTemplate.exchange("/topicos", HttpMethod.POST,
            new HttpEntity<>(topicoToBeSaved,httpHeaders), new ParameterizedTypeReference<>() {
            });
        
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(exchange.getBody()).isNotNull();
        Assertions.assertThat(exchange.getBody().id()).isNotNull();     
    }

    @Test
    @DisplayName("saveTopico throws bad request when titulo is empty")
    void saveTopico_ThrowsBadRequest_WhenTituloIsEmpty() {
        DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
        DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());

        TopicoRequest topicoWithoutTitulo = TopicoCreator.createTopicRequestWithoutTitulo();
        ResponseEntity<Object> exchange = testRestTemplate.exchange("/topicos", HttpMethod.POST,
            new HttpEntity<>(topicoWithoutTitulo,httpHeaders), new ParameterizedTypeReference<>() {
            });
        
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(exchange.getBody().toString()).contains("Campos Inválidos");
    }

    @Test
    @DisplayName("getTopicos returns page of topicResponse when successful")
    void getTopicos_ReturnsPageOfTopicoResponse_WhenSuccessful() {
        DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
        DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());

        Curso cursoSaved = cursoRepository.findCursoByNome(CursoCreator.createCourseToBeSaved().getNome()).get();
        Usuario usuarioSaved = usuarioRepository.findByEmail(autenticacao.email());
        Topico topicoToBeSaved = TopicoCreator.createTopicToBeSaved();
        topicoToBeSaved.setCurso(cursoSaved);
        topicoToBeSaved.setAutor(usuarioSaved);
        topicoRepository.save(topicoToBeSaved);

        ResponseEntity<PageableResponse<TopicoResponse>> exchange = testRestTemplate.exchange("/topicos",
            HttpMethod.GET, new HttpEntity<>(null,httpHeaders), 
            new ParameterizedTypeReference<>() {
                
            });
        
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange.getBody()).isNotEmpty();
        Assertions.assertThat(exchange.getBody().getContent().get(0).titulo())
            .isEqualTo(topicoToBeSaved.getTitulo());
    }

    @Test
    @DisplayName("getTopicos returns empty page when there is no topico")
    void getTopicos_ReturnsEmptyPage_WhenThereIsNoTopico() {
        DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
        DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());
         
        ResponseEntity<PageableResponse<TopicoResponse>> exchange = testRestTemplate.exchange("/topicos",
            HttpMethod.GET, new HttpEntity<>(null,httpHeaders), 
            new ParameterizedTypeReference<>() {    
            });
         
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange.getBody()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("getTopicoById returns topicResponse when successful")
    void getTopicoById_ReturnsTopicResponse_WhenSuccessful() {
        DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
        DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());

        Curso cursoSaved = cursoRepository.findCursoByNome(CursoCreator.createCourseToBeSaved().getNome()).get();
        Usuario usuarioSaved = usuarioRepository.findByEmail(autenticacao.email());
        Topico topicoToBeSaved = TopicoCreator.createTopicToBeSaved();
        topicoToBeSaved.setCurso(cursoSaved);
        topicoToBeSaved.setAutor(usuarioSaved);
        Long idTopicSaved = topicoRepository.save(topicoToBeSaved).getId();

        ResponseEntity<TopicoResponse> exchange = testRestTemplate.exchange("/topicos/{id}"
        , HttpMethod.GET, new HttpEntity<>(null,httpHeaders), new ParameterizedTypeReference<>(){

        },idTopicSaved);

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange.getBody()).isNotNull();
        Assertions.assertThat(exchange.getBody().id()).isEqualTo(idTopicSaved);
    }

    @Test
    @DisplayName("getTopicoById throws not found when topic not found")
    void getTopicoById_ThrowsNotFound_WhenTopicNotFound() {
        DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
        DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());
        
        Long idNotExist = 1l;

        ResponseEntity<Object> exchange = testRestTemplate.exchange("/topicos/{id}"
        , HttpMethod.GET, new HttpEntity<>(null,httpHeaders), new ParameterizedTypeReference<>(){

        },idNotExist);

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(exchange.getBody()).isNotNull();
        Assertions.assertThat(exchange.getBody().toString()).contains("not found");

    }

    @Test
    @DisplayName("updateTopicById returns altered topicResponse when successful")
    void updateTopicoById_ReturnsAlteredTopicResponse_WhenSuccessful() {
        DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
        DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());

        Curso cursoSaved = cursoRepository.findCursoByNome(CursoCreator.createCourseToBeSaved().getNome()).get();
        Usuario usuarioSaved = usuarioRepository.findByEmail(autenticacao.email());
        Topico topicoToBeSaved = TopicoCreator.createTopicToBeSaved();
        topicoToBeSaved.setCurso(cursoSaved);
        topicoToBeSaved.setAutor(usuarioSaved);
        Long idTopicSaved = topicoRepository.save(topicoToBeSaved).getId();
        AlteredTopic alteredTopic = TopicoCreator.createTopicRequestAltered();

        ResponseEntity<TopicoResponse> exchange = testRestTemplate.exchange("/topicos/{id}"
        , HttpMethod.PUT, new HttpEntity<>(alteredTopic,httpHeaders), new ParameterizedTypeReference<>() {
        }, idTopicSaved);
       
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange.getBody().id()).isEqualTo(idTopicSaved);
        Assertions.assertThat(exchange.getBody().titulo()).isEqualTo(alteredTopic.titulo());


    }
    
    @Test
    @DisplayName("updateTopicoById throws not found when topic not found")
    void updateTopicoById_ThrowsNotFound_WhenTopicNotFound() {
        DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
        DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());

        Long idNotExist = 1l;
        AlteredTopic alteredTopic = TopicoCreator.createTopicRequestAltered();

        ResponseEntity<Object> exchange = testRestTemplate.exchange("/topicos/{id}"
        , HttpMethod.PUT, new HttpEntity<>(alteredTopic,httpHeaders), new ParameterizedTypeReference<>() {
        }, idNotExist);

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(exchange.getBody()).isNotNull();
        Assertions.assertThat(exchange.getBody().toString()).contains("not found");
    }

    @Test
    @DisplayName("deleteById removes topic when successful")
    void deleteById_DeleteTopic_WhenSuccessful() {
        DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
        DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());

        Curso cursoSaved = cursoRepository.findCursoByNome(CursoCreator.createCourseToBeSaved().getNome()).get();
        Usuario usuarioSaved = usuarioRepository.findByEmail(autenticacao.email());
        Topico topicoToBeSaved = TopicoCreator.createTopicToBeSaved();
        topicoToBeSaved.setCurso(cursoSaved);
        topicoToBeSaved.setAutor(usuarioSaved);
        Long idTopicSaved = topicoRepository.save(topicoToBeSaved).getId();

        ResponseEntity<Void> exchange = testRestTemplate.exchange("/topicos/{id}", 
        HttpMethod.DELETE, new HttpEntity<>(null,httpHeaders), new ParameterizedTypeReference<>() {
            
        }, idTopicSaved);

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("deleteById throws not found when topic not found")
    void deleteById_ThrowsNotFound_WhenTopicNotFound() {
        DadosAutenticacao autenticacao = AutenticacaoCreator.createDadosAutenticacao();
        DadosTokenJWT dadosTokenJWT = testRestTemplate.postForObject("/login", autenticacao, DadosTokenJWT.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + dadosTokenJWT.tokenJWT());

        Long idNotExist = 1l;
        
        ResponseEntity<Object> exchange = testRestTemplate.exchange("/topicos/{id}", 
        HttpMethod.DELETE, new HttpEntity<>(null,httpHeaders), new ParameterizedTypeReference<>() {
            
        }, idNotExist);

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(exchange.getBody()).isNotNull();
        Assertions.assertThat(exchange.getBody().toString()).contains("not found");
    }

    @BeforeEach
    void setup() {
        usuarioRepository.save(UsuarioCreator.createUserToBeSaved());
        cursoRepository.save(CursoCreator.createCourseToBeSaved());
    }
     
    @AfterEach
    void end() {
        topicoRepository.deleteAll();
        usuarioRepository.deleteAll();
        cursoRepository.deleteAll();
    }
}
