package com.viniciuspascucci.ledger.adapters.in.web.response;

import com.viniciuspascucci.ledger.application.port.in.PartidaCommand;
import com.viniciuspascucci.ledger.domain.Lancamento;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record LancamentoResponse(UUID id, Instant criadoEm, List<PartidaResponse> partidas) {

    public static LancamentoResponse from(Lancamento lancamento) {
        return new LancamentoResponse(
                lancamento.getId(),
                lancamento.getCriadoEm(),
                lancamento.getPartidas().stream()
                        .map(PartidaResponse::from)
                        .toList());
    }
}
