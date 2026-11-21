package com.viniciuspascucci.ledger.application.port.in;

import com.viniciuspascucci.ledger.domain.TipoPartida;

import java.util.UUID;

public class PartidaCommand {

    private final UUID contaId;
    private final TipoPartida tipo;
    private final long valorEmCentavos;

    public PartidaCommand(UUID contaId, TipoPartida tipo, long valorEmCentavos) {
        this.contaId = contaId;
        this.tipo = tipo;
        this.valorEmCentavos = valorEmCentavos;
    }

    public UUID getContaId() {
        return contaId;
    }

    public TipoPartida getTipo() {
        return tipo;
    }

    public long getValorEmCentavos() {
        return valorEmCentavos;
    }
}
