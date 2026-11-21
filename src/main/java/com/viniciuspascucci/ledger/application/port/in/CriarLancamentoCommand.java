package com.viniciuspascucci.ledger.application.port.in;

import java.util.List;

public class CriarLancamentoCommand {

    private final List<PartidaCommand> partidaCommands;

    public CriarLancamentoCommand(List<PartidaCommand> partidaCommands) {
        this.partidaCommands = partidaCommands;
    }

    public List<PartidaCommand> getPartidas() {
        return partidaCommands;
    }
}
