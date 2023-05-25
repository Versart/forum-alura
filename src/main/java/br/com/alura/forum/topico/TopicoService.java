package br.com.alura.forum.topico;


import br.com.alura.forum.curso.Curso;
import br.com.alura.forum.curso.CursoRepository;
import br.com.alura.forum.infra.security.TokenService;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;

    private final CursoRepository cursoRepository;

    private final UsuarioRepository usuarioRepository;

    private final TokenService tokenService;

    @Transactional
    public TopicoResponse saveTopico(TopicoRequest topicoRequest, String token){
        token = token.replace("Bearer ", "");
        Curso curso = cursoRepository.findCursoByNome(topicoRequest.curso()).orElseThrow();
        var usuario = usuarioRepository.findByEmail(tokenService.getSubject(token));
        Topico topico = new Topico(topicoRequest);
        topico.setCurso(curso);
        topico.setAutor(usuario);
        topico = topicoRepository.save(topico);
        return new TopicoResponse(topico);
    }

    public Page<TopicoResponse> getTopicos(Pageable pageable) {
        Page<TopicoResponse> map = topicoRepository.findAll(pageable).map(TopicoResponse::new);
        System.out.println(map);
        return map;
    }
}
