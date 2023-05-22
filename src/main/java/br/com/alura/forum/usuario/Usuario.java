package br.com.alura.forum.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    public Usuario(UsuarioRequest usuarioRequest){
        this.email = usuarioRequest.email();
        this.nome = usuarioRequest.nome();
        this.senha = usuarioRequest.senha();
    }
}
