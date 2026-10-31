package com.viniciuspascucci.ledger.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LancamentoTest {

    @Test
    @DisplayName("Deveria aceitar lançamento equilibrado")
    void deveAceitarLancamentoEquilibrado() {
        var debito = Partida.debito(UUID.randomUUID(), 100);
        var credito = Partida.credito(UUID.randomUUID(), 100);

        assertDoesNotThrow(() -> Lancamento.criar(List.of(debito, credito)));
    }

    @Test
    @DisplayName("Deveria aceitar lançamento com múltiplas partidas")
    void deveAceitarLancamentoComMultiplasPartidas() {
        var debito = Partida.debito(UUID.randomUUID(), 100);
        var credito = Partida.credito(UUID.randomUUID(), 60);
        var credito2 = Partida.credito(UUID.randomUUID(), 40);

        assertDoesNotThrow(() -> Lancamento.criar(List.of(debito, credito, credito2)));
    }


    @Test
    @DisplayName("Deveria rejeitar lançamento desequilibrado")
    void deveRejeitarLancamentoDesequilibrado() {
        var debito = Partida.debito(UUID.randomUUID(), 100);
        var credito = Partida.credito(UUID.randomUUID(), 90);

        assertThrows(LancamentoDesequilibradoException.class,
                () -> Lancamento.criar(List.of(debito, credito)));
    }

    @Test
    @DisplayName("Deveria rejeitar lançamento com uma partida apenas")
    void deveRejeitarLancamentoComUmaPartida() {
        var debito = Partida.debito(UUID.randomUUID(), 100);

        assertThrows(LancamentoDesequilibradoException.class,
                () -> Lancamento.criar(List.of(debito)));
    }

    @Test
    @DisplayName("Deveria rejeitar lançamento sem credito")
    void deveRejeitarLancamentoSemCredito() {
        var debito = Partida.debito(UUID.randomUUID(), 100);
        var debito2 = Partida.debito(UUID.randomUUID(), 100);

        assertThrows(LancamentoDesequilibradoException.class,
                () -> Lancamento.criar(List.of(debito, debito2)));
    }

    @Test
    @DisplayName("Deveria rejeitar partida com valor zerado")
    void deveRejeitarPartidaComValorZero() {
        assertThrows(IllegalArgumentException.class,
                () -> Partida.debito(UUID.randomUUID(), 0));
    }

    @Test
    @DisplayName("Deveria rejeitar partida com valor negativo")
    void deveRejeitarPartidaComValorNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> Partida.debito(UUID.randomUUID(), -100));
    }

}
