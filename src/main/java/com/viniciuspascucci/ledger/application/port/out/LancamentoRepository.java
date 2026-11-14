package com.viniciuspascucci.ledger.application.port.out;

import com.viniciuspascucci.ledger.domain.Lancamento;

import java.util.Optional;
import java.util.UUID;

public interface LancamentoRepository {

    void salvar(Lancamento lancamento);
    Optional<Lancamento> buscarPorId(UUID id);
}
