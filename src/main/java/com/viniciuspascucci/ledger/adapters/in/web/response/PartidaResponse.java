package com.viniciuspascucci.ledger.adapters.in.web.response;

import com.viniciuspascucci.ledger.domain.Partida;
import com.viniciuspascucci.ledger.domain.TipoPartida;

import java.util.UUID;

public record PartidaResponse(UUID id, UUID contaId, TipoPartida tipo, long valorEmCentavos) {

    static PartidaResponse from(Partida partida) {
        return new PartidaResponse(
                partida.getId(),
                partida.getContaId(),
                partida.getTipoPartida(),
                partida.getValorEmCentavos()
        );
    }
}
