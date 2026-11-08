package com.viniciuspascucci.ledger.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Lancamento {

    private final UUID id;
    private final Instant criadoEm;
    private final List<Partida> partidas;

    private Lancamento(UUID id, Instant criadoEm, List<Partida> partidas) {
        this.id = id;
        this.criadoEm = criadoEm;
        this.partidas = List.copyOf(partidas);
    }

    public static Lancamento criar(List<Partida> partidas) {
        if (partidas.size() < 2) {
            throw new LancamentoDesequilibradoException(
                    "Lançamento precisa de pelo menos 2 partidas, recebidas: " + partidas.size());
        }

        long totalDebitos = partidas.stream()
                .filter(p -> p.getTipoPartida() == TipoPartida.DEBITO)
                .mapToLong(Partida::getValorEmCentavos)
                .sum();

        long totalCreditos = partidas.stream()
                .filter(p -> p.getTipoPartida() == TipoPartida.CREDITO)
                .mapToLong(Partida::getValorEmCentavos)
                .sum();

        if (totalDebitos != totalCreditos) {
            throw new LancamentoDesequilibradoException(
                    "Debitos (" + totalDebitos + ") diferentes de creditos (" + totalCreditos + ")");
        }


        return new Lancamento(UUID.randomUUID(), Instant.now(), partidas);
    }

    // Recria um lançamento que JÁ EXISTE no banco
    public static Lancamento reconstruir(UUID id, Instant criadoEm, List<Partida> partidas) {
        return new Lancamento(id, criadoEm, partidas);
    }

    public UUID getId() {
        return id;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }
}
