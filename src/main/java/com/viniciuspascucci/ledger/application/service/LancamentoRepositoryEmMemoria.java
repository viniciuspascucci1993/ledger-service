package com.viniciuspascucci.ledger.application.service;

import com.viniciuspascucci.ledger.application.port.out.LancamentoRepository;
import com.viniciuspascucci.ledger.domain.Lancamento;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class LancamentoRepositoryEmMemoria implements LancamentoRepository {

    private final Map<UUID, Lancamento> lancamentos = new HashMap<>();

    @Override
    public void salvar(Lancamento lancamento) {
        lancamentos.put(lancamento.getId(), lancamento);

    }

    @Override
    public Optional<Lancamento> buscarPorId(UUID id) {
        return Optional.ofNullable(lancamentos.get(id));
    }

    int total() {
        return lancamentos.size();
    }
}
