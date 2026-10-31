package com.viniciuspascucci.ledger.domain;

import java.util.UUID;

public class Partida {

    private final UUID contaId;
    private final TipoPartida tipoPartida;
    private final long valorEmCentavos;

    private Partida(UUID contaId, TipoPartida tipoPartida, long valorEmCentavos) {

        if (valorEmCentavos <= 0) {
            throw new IllegalArgumentException(
                    "Valor da partida deve ser maior que zero: " + valorEmCentavos);
        }

        this.contaId = contaId;
        this.tipoPartida = tipoPartida;
        this.valorEmCentavos = valorEmCentavos;
    }

    public static Partida debito(UUID contaId, long valorEmCentavos) {
        return new Partida(contaId, TipoPartida.DEBITO, valorEmCentavos);
    }

    public static Partida credito(UUID contaId, long valorEmCentavos) {
        return new Partida(contaId, TipoPartida.CREDITO, valorEmCentavos);
    }

    public UUID getContaId() {
        return contaId;
    }

    public TipoPartida getTipoPartida() {
        return tipoPartida;
    }

    public long getValorEmCentavos() {
        return valorEmCentavos;
    }
}
