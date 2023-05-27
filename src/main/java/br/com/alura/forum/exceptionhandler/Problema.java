package br.com.alura.forum.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problema {

    private String messagem;

    private int statusCode;

    private OffsetDateTime dateTime = OffsetDateTime.now();

    private List<Campo> campoList = new ArrayList<>();
}
