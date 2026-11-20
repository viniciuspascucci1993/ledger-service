package com.viniciuspascucci.ledger.adapters.in.web.request;

import com.viniciuspascucci.ledger.domain.TipoPartida;

import java.util.UUID;

public record PartidaRequest(UUID contaId, TipoPartida tipo, long valorEmCentavos) { }
