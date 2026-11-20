package com.viniciuspascucci.ledger.adapters.in.web.request;

import com.viniciuspascucci.ledger.application.port.in.CriarLancamentoCommand;
import com.viniciuspascucci.ledger.application.port.in.PartidaCommand;

import java.util.List;

public record CriarLancamentoRequest(List<PartidaRequest> partidas) {

    public CriarLancamentoCommand toComand() {
        return new CriarLancamentoCommand(
                partidas.stream()
                        .map(p -> new PartidaCommand(p.contaId(), p.tipo(), p.valorEmCentavos()))
                        .toList());
    }
}
