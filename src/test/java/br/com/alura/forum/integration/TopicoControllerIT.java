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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.forum.curso.Curso;
import br.com.alura.forum.curso.CursoRepository;
import br.com.alura.forum.infra.security.DadosTokenJWT;
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

       TopicoRequest topicoToBeSaved = TopicoCreator.createTopicRequest();
       ResponseEntity<TopicoResponse> exchange = testRestTemplate.exchange("/topicos", HttpMethod.POST,
            new HttpEntity<>(topicoToBeSaved,httpHeaders), new ParameterizedTypeReference<>() {
            });
        
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(exchange.getBody()).isNotNull();
        Assertions.assertThat(exchange.getBody().id()).isNotNull();     
    }

    @BeforeEach
    void setup() {
        usuarioRepository.save(UsuarioCreator.createUserToBeSaved());
    }
     
    @AfterEach
    void end() {
        topicoRepository.deleteAll();
        usuarioRepository.deleteAll();
    }
}
