package com.viniciuspascucci.ledger.domain;

import java.util.UUID;

public class Partida {

    private final UUID id;
    private final UUID contaId;
    private final TipoPartida tipoPartida;
    private final long valorEmCentavos;

    private Partida(UUID id, UUID contaId, TipoPartida tipoPartida, long valorEmCentavos) {

        if (valorEmCentavos <= 0) {
            throw new IllegalArgumentException(
                    "Valor da partida deve ser maior que zero: " + valorEmCentavos);
        }

        this.id = id;
        this.contaId = contaId;
        this.tipoPartida = tipoPartida;
        this.valorEmCentavos = valorEmCentavos;
    }

    public static Partida debito(UUID contaId, long valorEmCentavos) {
        return new Partida(UUID.randomUUID(), contaId, TipoPartida.DEBITO, valorEmCentavos);
    }

    public static Partida credito(UUID contaId, long valorEmCentavos) {
        return new Partida(UUID.randomUUID(), contaId, TipoPartida.CREDITO, valorEmCentavos);
    }

    public static Partida reconstruir(UUID id, UUID contaId, long valorEmCentavos) {
        return new Partida(id, contaId, TipoPartida.CREDITO, valorEmCentavos);
    }

    public UUID getId() {
        return id;
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
