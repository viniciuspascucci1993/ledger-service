package com.viniciuspascucci.ledger.adapters.in.web;

import com.viniciuspascucci.ledger.adapters.in.web.request.CriarLancamentoRequest;
import com.viniciuspascucci.ledger.adapters.in.web.response.LancamentoResponse;
import com.viniciuspascucci.ledger.application.exception.LancamentoNaoEncontradoException;
import com.viniciuspascucci.ledger.application.port.in.BuscarLancamentoUseCase;
import com.viniciuspascucci.ledger.application.port.in.CriarLancamentoCommand;
import com.viniciuspascucci.ledger.application.port.in.CriarLancamentoUseCase;
import com.viniciuspascucci.ledger.domain.Lancamento;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/lancamentos")
public class LancamentoController {

    private final CriarLancamentoUseCase criarLancamentoUseCase;
    private final BuscarLancamentoUseCase buscarLancamentoUseCase;

    public LancamentoController(CriarLancamentoUseCase criarLancamentoUseCase,
                                BuscarLancamentoUseCase buscarLancamentoUseCase) {
        this.criarLancamentoUseCase = criarLancamentoUseCase;
        this.buscarLancamentoUseCase = buscarLancamentoUseCase;
    }

    @PostMapping
    ResponseEntity<LancamentoResponse> criar(@Valid @RequestBody CriarLancamentoRequest request) {
        // 1. request.toCommand() → chamar o use case
        Lancamento lancamento = criarLancamentoUseCase.criar(request.toComand());

        // 2. montar a URI do Location: "/v1/lancamentos/" + id
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(lancamento.getId())
                .toUri();

        return ResponseEntity.created(location).body(LancamentoResponse.from(lancamento));
    }

    @GetMapping("/{id}")
    ResponseEntity<LancamentoResponse> buscarPorId(@PathVariable("id") UUID id) {
        Lancamento lancamento = buscarLancamentoUseCase.buscarPorId(id)
                .orElseThrow(() -> new LancamentoNaoEncontradoException(id));

        return ResponseEntity.ok(LancamentoResponse.from(lancamento));

    }
}
