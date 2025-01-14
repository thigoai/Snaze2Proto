package br.ufrn.imd.modelo;

public class GameState {
    private boolean gameOver = false;
    private boolean pausado = false;
    private boolean jogoIniciado = false;
    private boolean snake1Morta = false;
    private boolean snake2Morta = false;
    private int piscadas = 0;
    private int pontosSnake1 = 0;
    private int pontosSnake2 = 0;
    private int mortesSnake1 = 0;
    private int mortesSnake2 = 0;
    
    public static final int MAX_PISCADAS = 6;
    public static final int TAMANHO_VITORIA = 20;
    public static final int MAX_MORTES = 5;
    
    // Getters, setters e métodos auxiliares
    public void incrementarPiscadas() {
        piscadas++;
    }
    
    public void incrementarMortesSnake1() {
        mortesSnake1++;
    }
    
    public void incrementarMortesSnake2() {
        mortesSnake2++;
    }
    
    public void resetPiscadas() {
        piscadas = 0;
    }
}