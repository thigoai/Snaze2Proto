package br.ufrn.imd.exception;

public class PlayerDataException extends GameException {
    public PlayerDataException(String message) {
        super("Erro nos dados do jogador: " + message);
    }
}
