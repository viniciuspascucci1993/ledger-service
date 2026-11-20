package com.viniciuspascucci.ledger.application.port.in;

import com.viniciuspascucci.ledger.domain.Lancamento;

public interface CriarLancamentoUseCase {

    Lancamento criar(CriarLancamentoCommand criarLancamentoComando);
}
