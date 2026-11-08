package com.viniciuspascucci.ledger.adapters.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "lancamentos")
public class LancamentoEntity {

    @Id
    private UUID id;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    protected LancamentoEntity() { }

    public LancamentoEntity(UUID id, Instant criadoEm) {
        this.id = id;
        this.criadoEm = criadoEm;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }
}
