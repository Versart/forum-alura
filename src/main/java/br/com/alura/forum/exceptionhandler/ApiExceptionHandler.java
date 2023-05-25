package br.com.alura.forum.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Problema problema = Problema.builder()
                .messagem("Campos Inválidos")
                .statusCode(status.value())
                .dateTime(OffsetDateTime.now())
                .build();
        List<Campo> camposInvalidos = ex.getFieldErrors().stream().map(Campo::new).collect(Collectors.toList());
        problema.setCampoList(camposInvalidos);
        return handleExceptionInternal(ex, problema, headers, status, request);
    }
}
