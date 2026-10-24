# ADR-001: Representação monetária

## Status

Aceita

## Contexto

O ledger precisa armazenar e somar valores monetários sem perda de precisão.
Float/double estão fora de cogitação: são armazenados em binário e não
representam a maioria das frações decimais (ex.: 0,10) de forma exata, o que
gera erros de arredondamento que se acumulam nas somas.

## Decisão

Valores monetários serão representados como `long` na menor unidade da moeda
(centavos para BRL): R$ 12,34 é armazenado como `1234`.
Todo valor é acompanhado do código da moeda no padrão ISO 4217 (ex.: `BRL`),
em campo separado.
No banco, o valor usa coluna `BIGINT` e a moeda `CHAR(3)`.

## Alternativas consideradas

### long em centavos (escolhida)

- Prós:
  - Soma e subtração exatas, sem arredondamento
  - Tipo primitivo: rápido, leve, mapeia direto para BIGINT
- Contras:
  - Risco de confundir reais com centavos
  - Cálculos com percentual exigem arredondamento explícito

### BigDecimal

- Prós:
  - Multiplicação e divisão com casas decimais nativas (percentuais, juros, rateio)
  - Controle explícito de escala e arredondamento (`setScale` + `RoundingMode`)
- Contras:
  - Objeto imutável: mais lento e mais memória que um primitivo
  - Armadilhas de uso: `new BigDecimal(0.1)` herda o erro do double, e `equals` compara a escala (`2.0` ≠ `2.00`)

## Consequências

- Todo campo monetário no código é do tipo `long`; `float` e `double` são proibidos.
- A conversão entre centavos e reais acontece apenas na borda da API; o domínio trabalha somente com centavos.
- O valor de uma partida deve ser maior que zero; o lado (débito ou crédito) indica a direção.
- Ao calcular percentuais, o resultado é arredondado com `HALF_UP`, de forma explícita.
