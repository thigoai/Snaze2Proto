package br.ufrn.imd.exception;

public class LevelLoadException extends GameException {
    public LevelLoadException(String message) {
        super("Erro ao carregar nível: " + message);
    }
}