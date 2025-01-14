package br.ufrn.imd.modelo;

public class Labirinto {
    private int[][] cells;

    public Labirinto() {
    }

    public void gerarLabirinto(int[][] newCells) {
        this.cells = newCells;
    }

    public boolean ehParede(int linha, int coluna) {
        if (linha < 0 || linha >= cells.length || coluna < 0 || coluna >= cells[0].length) {
            return true;
        }
        return cells[linha][coluna] == 1;
    }

    public int[][] getCells() {
        return cells;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }
}