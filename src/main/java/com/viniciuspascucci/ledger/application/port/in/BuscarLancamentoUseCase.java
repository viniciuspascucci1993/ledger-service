package com.viniciuspascucci.ledger.application.port.in;

import com.viniciuspascucci.ledger.domain.Lancamento;

import java.util.Optional;
import java.util.UUID;

public interface BuscarLancamentoUseCase {
    Optional<Lancamento> buscarPorId(UUID id);
}
