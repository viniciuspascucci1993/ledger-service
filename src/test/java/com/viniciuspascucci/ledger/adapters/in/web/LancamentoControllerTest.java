package com.viniciuspascucci.ledger.adapters.in.web;

import com.viniciuspascucci.ledger.application.port.in.BuscarLancamentoUseCase;
import com.viniciuspascucci.ledger.application.port.in.CriarLancamentoCommand;
import com.viniciuspascucci.ledger.application.port.in.CriarLancamentoUseCase;
import com.viniciuspascucci.ledger.domain.Lancamento;
import com.viniciuspascucci.ledger.domain.LancamentoDesequilibradoException;
import com.viniciuspascucci.ledger.domain.Partida;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LancamentoController.class)
public class LancamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CriarLancamentoUseCase criarLancamentoUseCase;

    @MockitoBean
    private BuscarLancamentoUseCase buscarLancamentoUseCase;

    private static final String CORPO_VALIDO = """
            {
              "partidas": [
                { "contaId": "11111111-1111-1111-1111-111111111111", "tipo": "DEBITO",  "valorEmCentavos": 5000 },
                { "contaId": "22222222-2222-2222-2222-222222222222", "tipo": "CREDITO", "valorEmCentavos": 5000 }
              ]
            }
            """;


    @Test
    @DisplayName("POST válido deveria retornar 201 com Location e id")
    void deveRetornar201ComLocation() throws Exception {
        var lancamento = Lancamento.criar(List.of(
                Partida.debito(UUID.randomUUID(), 5000),
                Partida.credito(UUID.randomUUID(), 5000)));

        when(criarLancamentoUseCase.criar(any())).thenReturn(lancamento);

        mockMvc.perform(post("/v1/lancamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CORPO_VALIDO))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/v1/lancamentos/" + lancamento.getId())))
                .andExpect(jsonPath("$.id").value(lancamento.getId().toString()));
    }

    @Test
    @DisplayName("Lançamento desequilibrado deveria retornar 422 com ProblemDetail")
    void deveRetornar422QuandoDesequilibrado() throws Exception {
        when(criarLancamentoUseCase.criar(any())).thenThrow(
                new LancamentoDesequilibradoException("Debitos (5000) diferentes de creditos (4000)"));

        mockMvc.perform(post("/v1/lancamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CORPO_VALIDO))
                .andExpect(status().is(422))
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Lançamento desequilibrado"))
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    @DisplayName("Valor não numérico deveria retornar 400 sem chamar o caso de uso")
    void deveRetornar400QuandoValorInvalido() throws Exception {
    String corpoValido = CORPO_VALIDO.replaceFirst("5000", "\"abc\"");
        mockMvc.perform(post("/v1/lancamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corpoValido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Valor negativo deveria retornar 400 sem chamar o caso de uso")
    void deveRetornar400QuandoValorNegativo() throws Exception {
        String corpoNegativo = CORPO_VALIDO.replaceFirst("5000", "-100");

        mockMvc.perform(post("/v1/lancamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corpoNegativo))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        verify(criarLancamentoUseCase, never()).criar(any());
    }

    @Test
    @DisplayName("GET de lançamento existente deveria retornar 200 com o lançamento")
    void deveRetornar200QuandoLancamentoExiste() throws Exception {
        var lancamento = Lancamento.criar(List.of(
                Partida.debito(UUID.randomUUID(), 5000),
                Partida.credito(UUID.randomUUID(), 5000)));

        when(buscarLancamentoUseCase.buscarPorId(lancamento.getId())).thenReturn(Optional.of(lancamento));
        mockMvc.perform(get("/v1/lancamentos/{id}", lancamento.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lancamento.getId().toString()))
                .andExpect(jsonPath("$.partidas.length()").value(2));
    }

    @Test
    @DisplayName("GET de lançamento inexistente deveria retornar 404 com ProblemDetail")
    void deveRetornar404QuandoLancamentoNaoExiste() throws Exception {
        UUID idInexistente = UUID.randomUUID();
        when(buscarLancamentoUseCase.buscarPorId(idInexistente)).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/lancamentos/{id}", idInexistente))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.title").value("Lançamento não encontrado"));
    }
}
