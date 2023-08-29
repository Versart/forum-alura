package br.com.alura.forum.curso;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String categoria;

    public Curso (CursoRequest cursoRequest){
        this.nome = cursoRequest.nome();
        this.categoria = cursoRequest.categoria();
    }
    
}
