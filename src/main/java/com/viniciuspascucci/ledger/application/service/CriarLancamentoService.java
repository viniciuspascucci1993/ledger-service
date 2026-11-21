package com.viniciuspascucci.ledger.application.service;

import com.viniciuspascucci.ledger.application.port.in.CriarLancamentoCommand;
import com.viniciuspascucci.ledger.application.port.in.CriarLancamentoUseCase;
import com.viniciuspascucci.ledger.application.port.in.PartidaCommand;
import com.viniciuspascucci.ledger.application.port.out.LancamentoRepository;
import com.viniciuspascucci.ledger.domain.Lancamento;
import com.viniciuspascucci.ledger.domain.Partida;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriarLancamentoService implements CriarLancamentoUseCase {

    private final LancamentoRepository lancamentoRepository;

    public CriarLancamentoService(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    public Lancamento criar(CriarLancamentoCommand comando) {
        List<Partida> partidas = comando.getPartidas().stream()
                .map(this::toPartida)
                .toList();

        Lancamento lancamento = Lancamento.criar(partidas);
        lancamentoRepository.salvar(lancamento);
        return lancamento;

    }

    private Partida toPartida(PartidaCommand command) {
        return switch (command.getTipo()) {
            case DEBITO -> Partida.debito(command.getContaId(), command.getValorEmCentavos());
            case CREDITO  -> Partida.credito(command.getContaId(), command.getValorEmCentavos());
        };
    }
}
