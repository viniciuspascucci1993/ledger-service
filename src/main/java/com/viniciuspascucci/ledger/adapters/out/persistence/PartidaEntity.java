package com.viniciuspascucci.ledger.adapters.out.persistence;

import com.viniciuspascucci.ledger.domain.TipoPartida;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "partidas")
public class PartidaEntity {

    @Id
    private UUID id;

    @Column(name = "lancamento_id", nullable = false)
    private UUID lancamentoId;

    @Column(name = "conta_id", nullable = false)
    private UUID contaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPartida tipo;

    @Column(name = "valor_em_centavos", nullable = false)
    private long valorEmCentavos;

    protected PartidaEntity() { }

    public PartidaEntity(UUID id, UUID lancamentoId, UUID contaId, TipoPartida tipo, long valorEmCentavos) {
        this.id = id;
        this.lancamentoId = lancamentoId;
        this.contaId = contaId;
        this.tipo = tipo;
        this.valorEmCentavos = valorEmCentavos;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getLancamentoId() {
        return lancamentoId;
    }

    public UUID getContaId() {
        return contaId;
    }

    public TipoPartida getTipo() {
        return tipo;
    }

    public long getValorEmCentavos() {
        return valorEmCentavos;
    }
}
