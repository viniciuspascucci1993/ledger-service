package com.viniciuspascucci.ledger.adapters.in.web.request;

import com.viniciuspascucci.ledger.domain.TipoPartida;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record PartidaRequest(
        @NotNull UUID contaId,
        @NotNull TipoPartida tipo,
        @Positive long valorEmCentavos) { }
