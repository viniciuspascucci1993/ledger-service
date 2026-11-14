package com.viniciuspascucci.ledger.adapters.out.persistence;

import com.viniciuspascucci.ledger.application.port.out.LancamentoRepository;
import com.viniciuspascucci.ledger.domain.Lancamento;
import com.viniciuspascucci.ledger.domain.Partida;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
class LancamentoPersistenceAdapter implements LancamentoRepository {

    private final LancamentoJpaRepository lancamentoJpaRepository;
    private final PartidaJpaRepository partidaJpaRepository;

    public LancamentoPersistenceAdapter(LancamentoJpaRepository lancamentoJpaRepository,
                                        PartidaJpaRepository partidaJpaRepository) {
        this.lancamentoJpaRepository = lancamentoJpaRepository;
        this.partidaJpaRepository = partidaJpaRepository;
    }

    @Override
    @Transactional
    public void salvar(Lancamento lancamento) {
        var lancamentoEntity = new LancamentoEntity(lancamento.getId(), lancamento.getCriadoEm());
        lancamentoJpaRepository.save(lancamentoEntity);

        var partidaEntities = lancamento.getPartidas()
                .stream()
                .map(partida -> new PartidaEntity(partida.getId(), lancamento.getId(),
                        partida.getContaId(), partida.getTipoPartida(),
                        partida.getValorEmCentavos()))
                .toList();

        partidaJpaRepository.saveAll(partidaEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Lancamento> buscarPorId(UUID id) {
        return lancamentoJpaRepository.findById(id)
                .map(lancamentoEntity -> {
                    var partidas = partidaJpaRepository.findByLancamentoId(id)
                            .stream()
                            .map(partidaEntity -> Partida.reconstruir(partidaEntity.getId(), partidaEntity.getContaId(),
                                    partidaEntity.getTipo(), partidaEntity.getValorEmCentavos()))
                            .toList();

                    return Lancamento.reconstruir(lancamentoEntity.getId(),
                            lancamentoEntity.getCriadoEm(),
                            partidas);
                });
    }
}
