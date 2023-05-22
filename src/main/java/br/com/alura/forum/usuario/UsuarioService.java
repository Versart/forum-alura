package br.com.alura.forum.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponse saveUsuario(UsuarioRequest usuarioRequest) {
        return new UsuarioResponse(usuarioRepository.save(new Usuario(usuarioRequest)));
    }
}
