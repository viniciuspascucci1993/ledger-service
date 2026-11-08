package com.viniciuspascucci.ledger.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartidaTest {

    @ParameterizedTest
    @EnumSource(TipoPartida.class)
    @DisplayName("Deveria reconstruir partida preservando o tipo")
    void deveReconstruirPartidaPreservandoTipo( TipoPartida tipoPartida ) {
        var partida = Partida.reconstruir(UUID.randomUUID(), UUID.randomUUID(),
                tipoPartida, 100);

        assertEquals(tipoPartida, partida.getTipoPartida());
    }
}
