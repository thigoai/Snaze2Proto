package br.ufrn.imd.modelo;

import br.ufrn.imd.modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Render {
    private Snake snake1;
    private Snake snake2;
    private Celula comida;
    private Celula proximaComida;
    private Labirinto labirinto;
    private int tileSize;
    private int boardHeight;
    private int boardWidth;
    private boolean snake1Morta;
    private boolean snake2Morta;
    private int piscadas;
    private int pontosSnake1;
    private int pontosSnake2;
    private boolean gameOver;
    private int mortesSnake1;
    private int mortesSnake2;
    
    public Render(Snake snake1, Snake snake2, Celula comida, Celula proximaComida, Labirinto labirinto, 
                 int tileSize, int boardHeight, int boardWidth) {
        this.snake1 = snake1;
        this.snake2 = snake2;
        this.comida = comida;
        this.proximaComida = proximaComida;
        this.labirinto = labirinto;
        this.tileSize = tileSize;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
    }
    
    public void setSnake1Morta(boolean morta) {
        this.snake1Morta = morta;
    }
    
    public void setSnake2Morta(boolean morta) {
        this.snake2Morta = morta;
    }
    
    public void setPiscadas(int piscadas) {
        this.piscadas = piscadas;
    }
    
    public void setPontos(int pontosSnake1, int pontosSnake2) {
        this.pontosSnake1 = pontosSnake1;
        this.pontosSnake2 = pontosSnake2;
    }
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public void setMortes(int mortesSnake1, int mortesSnake2) {
        this.mortesSnake1 = mortesSnake1;
        this.mortesSnake2 = mortesSnake2;
    }
    
    public void draw(Graphics g) {
    	
        // Desenha o labirinto
        drawMaze(g);
        
        // Desenha a comida
        g.setColor(Color.RED);
        g.fillRect(comida.getX() * tileSize, comida.getY() * tileSize, tileSize, tileSize);
     // Desenha a comida
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(proximaComida.getX() * tileSize, proximaComida.getY() * tileSize, tileSize, tileSize);
        
        // Desenha as cobras (piscando se estiverem mortas)
        if (!snake1Morta || piscadas % 2 == 0) {
            drawSnake(g, snake1, Color.GREEN, Color.YELLOW);
        }
        
        if (!snake2Morta || piscadas % 2 == 0) {
            drawSnake(g, snake2, Color.BLUE, Color.CYAN);
        }
        
        // Desenha os pontos
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Jogador: " + snake1.getCorpo().size() + " (Mortes: " + mortesSnake1 + ")", 10, 30);
        g.drawString("Bot: " + snake2.getCorpo().size() + " (Mortes: " + mortesSnake2 + ")", boardWidth - 200, 30);
        
        // Mostra mensagem de fim de jogo
        if (gameOver) {
            String mensagem;
            if (mortesSnake1 >= 5) {
                mensagem = "Bot Venceu! (Jogador morreu 5 vezes)";
            } else if (mortesSnake2 >= 5) {
                mensagem = "Jogador Venceu! (Bot morreu 5 vezes)";
            } else if (snake1.getCorpo().size() >= 10) {
                mensagem = "Jogador Venceu! (Tamanho 10)";
            } else {
                mensagem = "Bot Venceu! (Tamanho 10)";
            }
            
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics = g.getFontMetrics();
            int x = (boardWidth - metrics.stringWidth(mensagem)) / 2;
            int y = boardHeight / 2;
            g.drawString(mensagem, x, y);
        }
    }
    
    private void drawMaze(Graphics g) {
    	
    	for(int i = 0; i <boardWidth/tileSize; i++) {
			g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
			g.drawLine(0, i*tileSize, boardWidth, i*tileSize);	
		}
        for (int row = 0; row < boardHeight/tileSize; row++) {
            for (int col = 0; col < boardWidth/tileSize; col++) {
                if (labirinto.ehParede(row, col)) {
                    g.setColor(Color.GRAY);
                    g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                }
            }
        }
    }
    
    private void drawSnake(Graphics g, Snake snake, Color corCabeca, Color corCorpo) {
        // Desenha o corpo
        g.setColor(corCorpo);
        for (int i = 1; i < snake.getCorpo().size(); i++) {
            Celula parte = snake.getCorpo().get(i);
            g.fillRect(parte.getX() * tileSize, parte.getY() * tileSize, tileSize, tileSize);
        }
        
        // Desenha a cabeça
        g.setColor(corCabeca);
        Celula cabeca = snake.getCabeca();
        g.fillRect(cabeca.getX() * tileSize, cabeca.getY() * tileSize, tileSize, tileSize);
    }
}