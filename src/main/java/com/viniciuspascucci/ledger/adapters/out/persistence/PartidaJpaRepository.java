package com.viniciuspascucci.ledger.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface PartidaJpaRepository extends JpaRepository<PartidaEntity, UUID> {
    List<PartidaEntity> findByLancamentoId(UUID lancamentoId);
}
