package com.viniciuspascucci.ledger.application.exception;

import java.util.UUID;

public class LancamentoNaoEncontradoException extends RuntimeException {
    public LancamentoNaoEncontradoException(UUID id) {
        super("Lançamento " + id + " não encontrado");
    }
}
