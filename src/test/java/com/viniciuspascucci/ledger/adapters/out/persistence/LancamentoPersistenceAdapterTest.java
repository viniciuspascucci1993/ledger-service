package com.viniciuspascucci.ledger.adapters.out.persistence;

import com.viniciuspascucci.ledger.TestcontainersConfiguration;
import com.viniciuspascucci.ledger.application.port.out.LancamentoRepository;
import com.viniciuspascucci.ledger.domain.Lancamento;
import com.viniciuspascucci.ledger.domain.Partida;
import com.viniciuspascucci.ledger.domain.TipoPartida;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
public class LancamentoPersistenceAdapterTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Test
    @DisplayName("Deveria salvar e recuperar lançamento com suas partidas")
    void deveSalvarERecuperarLancamento() {

        var debito = Partida.debito(UUID.randomUUID(), 100);
        var credito = Partida.credito(UUID.randomUUID(), 100);
        var lancamento = Lancamento.criar(List.of(debito, credito));

        lancamentoRepository.salvar(lancamento);
        var recuperado = lancamentoRepository.buscarPorId(lancamento.getId());

        assertTrue(recuperado.isPresent());
        assertEquals(lancamento.getId(), recuperado.get().getId());
        assertEquals(lancamento.getCriadoEm(), recuperado.get().getCriadoEm());
        assertEquals(2, recuperado.get().getPartidas().size());

        // conferir que o débito voltou como DEBITO, com valor 100
        var debitoRecuperado = recuperado.get().getPartidas().stream()
                .filter(p -> p.getId().equals(debito.getId()))
                .findFirst()
                .orElseThrow();

        assertEquals(TipoPartida.DEBITO, debitoRecuperado.getTipoPartida());
        assertEquals(100, debitoRecuperado.getValorEmCentavos());
    }

    @Test
    @DisplayName("Deveria retornar vazio ao buscar id inexistente")
    void deveRetornarVazioQuandoIdNaoExiste() {

        var resultado = lancamentoRepository.buscarPorId(UUID.randomUUID());
        assertTrue(resultado.isEmpty());

    }
}
