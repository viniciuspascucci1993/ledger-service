package com.viniciuspascucci.ledger.domain;

import org.jspecify.annotations.Nullable;

import java.util.List;

public class Lancamento {

    private final List<Partida> partidas;

    public Lancamento(List<Partida> partidas) {
        this.partidas = List.copyOf(partidas);
    }

    public static @Nullable Object criar(List<Partida> partidas) {
        // ??? 1. menos de 2 partidas → LancamentoDesequilibradoException
        if (partidas.size() < 2) {
            throw new LancamentoDesequilibradoException(
                    "Lançamento precisa de pelo menos 2 partidas, recebidas: " + partidas.size());
        }

        // ??? 2. somar os valores das partidas de DEBITO
        long totalDebitos = partidas.stream()
                .filter(p -> p.getTipoPartida() == TipoPartida.DEBITO)
                .mapToLong(Partida::getValorEmCentavos)
                .sum();


        // ??? 3. somar os valores das partidas de CREDITO
        long totalCreditos = partidas.stream()
                .filter(p -> p.getTipoPartida() == TipoPartida.CREDITO)
                .mapToLong(Partida::getValorEmCentavos)
                .sum();


        // ??? 4. se as somas forem diferentes → LancamentoDesequilibradoException
        if (totalDebitos != totalCreditos) {
            throw new LancamentoDesequilibradoException(
                    "Debitos (" + totalDebitos + ") diferentes de creditos (" + totalCreditos + ")");
        }


        return new Lancamento(partidas);
    }

    public List<Partida> getPartidas() {
        return partidas;
    }
}
