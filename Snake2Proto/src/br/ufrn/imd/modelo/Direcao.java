package br.ufrn.imd.modelo;

public enum Direcao {
    UP, DOWN, LEFT, RIGHT;

    public Direcao getOposto() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: throw new IllegalStateException("Direção inválida");
        }
    }
}
