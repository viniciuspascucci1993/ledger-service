package com.viniciuspascucci.ledger.application.service;

import com.viniciuspascucci.ledger.application.port.in.BuscarLancamentoUseCase;
import com.viniciuspascucci.ledger.application.port.out.LancamentoRepository;
import com.viniciuspascucci.ledger.domain.Lancamento;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BuscarLancamentoService implements BuscarLancamentoUseCase {

    private final LancamentoRepository lancamentoRepository;

    public BuscarLancamentoService(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    public Optional<Lancamento> buscarPorId(UUID id) {
        return lancamentoRepository.buscarPorId(id);
    }
}
