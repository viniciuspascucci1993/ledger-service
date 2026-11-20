package com.viniciuspascucci.ledger.adapters.in.web;

import com.viniciuspascucci.ledger.domain.LancamentoDesequilibradoException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LancamentoDesequilibradoException.class)
    ProblemDetail lancamentoDesequilibrado(LancamentoDesequilibradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(422), ex.getMessage());
        problemDetail.setTitle("Lançamento desequilibrado");
        return problemDetail;
    }
}
