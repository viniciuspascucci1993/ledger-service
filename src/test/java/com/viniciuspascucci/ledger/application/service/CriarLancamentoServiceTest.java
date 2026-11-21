package com.viniciuspascucci.ledger.application.service;

import com.viniciuspascucci.ledger.application.port.in.CriarLancamentoCommand;
import com.viniciuspascucci.ledger.application.port.in.PartidaCommand;
import com.viniciuspascucci.ledger.domain.Lancamento;
import com.viniciuspascucci.ledger.domain.LancamentoDesequilibradoException;
import com.viniciuspascucci.ledger.domain.TipoPartida;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CriarLancamentoServiceTest {

    private final LancamentoRepositoryEmMemoria repository = new LancamentoRepositoryEmMemoria();
    private final CriarLancamentoService service = new CriarLancamentoService(repository);

    private final UUID caixa = UUID.randomUUID();
    private final UUID receita = UUID.randomUUID();

    @Test
    @DisplayName("Deveria criar e salvar um lançamento equilibrado")
    void deveCriarESalvarLancamentoEquilibrado() {
        var comando = new CriarLancamentoCommand(List.of(
                new PartidaCommand(caixa, TipoPartida.DEBITO, 5000),
                new PartidaCommand(receita, TipoPartida.CREDITO, 5000)));

        Lancamento criado = service.criar(comando);

        assertTrue(repository.buscarPorId(criado.getId()).isPresent());
        assertEquals(2, criado.getPartidas().size());
    }

    @Test
    @DisplayName("Não deveria salvar um lançamento desequilibrado")
    void naoDeveSalvarLancamentoDesequilibrado() {
        var comando = new CriarLancamentoCommand(List.of(
                new PartidaCommand(caixa, TipoPartida.DEBITO, 5000),
                new PartidaCommand(receita, TipoPartida.CREDITO, 4000)));

        assertThrows(LancamentoDesequilibradoException.class, () -> service.criar(comando));
        assertEquals(0, repository.total());

    }

}
