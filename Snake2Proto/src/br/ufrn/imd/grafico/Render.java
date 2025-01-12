package br.ufrn.imd.grafico;

import br.ufrn.imd.modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Render {
    private Snake snake1;
    private Snake snake2;
    private Celula comida;
    private volatile Celula proximaComida;
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
    
    // Cores mais modernas
    private final Color WALL_COLOR = new Color(40, 44, 52);
    private final Color GRID_COLOR = new Color(30, 33, 40, 50);
    private final Color FOOD_COLOR = new Color(255, 85, 85);
    private final Color NEXT_FOOD_COLOR = new Color(255, 85, 85, 128);
    private final Color SNAKE1_HEAD = new Color(98, 255, 134);
    private final Color SNAKE1_BODY = new Color(73, 190, 100);
    private final Color SNAKE2_HEAD = new Color(82, 139, 255);
    private final Color SNAKE2_BODY = new Color(61, 103, 190);
    
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
    
    public void setProximaComida(Celula proximaComida) {
        this.proximaComida = proximaComida;
    }
    
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Desenha o fundo com gradiente
        GradientPaint backgroundGradient = new GradientPaint(
            0, 0, new Color(17, 17, 17),
            0, boardHeight, new Color(25, 25, 25)
        );
        g2d.setPaint(backgroundGradient);
        g2d.fillRect(0, 0, boardWidth, boardHeight);
        
        // Desenha grid sutil
        drawGrid(g2d);
        
        // Desenha o labirinto com efeito de sombra
        drawMaze(g2d);
        
        // Desenha comidas com brilho
        drawFood(g2d);
        
        // Desenha as cobras com efeito de gradiente
        if (!snake1Morta || piscadas % 2 == 0) {
            drawSnake(g2d, snake1, SNAKE1_HEAD, SNAKE1_BODY);
        }
        
        if (!snake2Morta || piscadas % 2 == 0) {
            drawSnake(g2d, snake2, SNAKE2_HEAD, SNAKE2_BODY);
        }
        
        // Desenha interface com estilo moderno
        drawInterface(g2d);
        
        // Desenha game over com efeito de fade
        if (gameOver) {
            drawGameOver(g2d);
        }
    }
    
    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(GRID_COLOR);
        for (int i = 0; i < boardWidth/tileSize; i++) {
            g2d.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g2d.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }
    }
    
    private void drawMaze(Graphics2D g2d) {
        for (int row = 0; row < boardHeight/tileSize; row++) {
            for (int col = 0; col < boardWidth/tileSize; col++) {
                if (labirinto.ehParede(row, col)) {
                    // Efeito de sombra nas paredes
                    g2d.setColor(WALL_COLOR.darker());
                    g2d.fillRoundRect(col * tileSize + 2, row * tileSize + 2, 
                                    tileSize - 2, tileSize - 2, 8, 8);
                    
                    g2d.setColor(WALL_COLOR);
                    g2d.fillRoundRect(col * tileSize, row * tileSize, 
                                    tileSize - 2, tileSize - 2, 8, 8);
                }
            }
        }
    }
    
    private void drawFood(Graphics2D g2d) {
        // Efeito de brilho na comida
        int glow = 10;
        g2d.setColor(new Color(255, 85, 85, 50));
        g2d.fillOval(comida.getX() * tileSize - glow/2, 
                     comida.getY() * tileSize - glow/2,
                     tileSize + glow, tileSize + glow);
                     
        g2d.setColor(FOOD_COLOR);
        g2d.fillOval(comida.getX() * tileSize, comida.getY() * tileSize,
                     tileSize - 2, tileSize - 2);
                     
        // Próxima comida com transparência
        g2d.setColor(NEXT_FOOD_COLOR);
        g2d.fillOval(proximaComida.getX() * tileSize,
                     proximaComida.getY() * tileSize,
                     tileSize - 2, tileSize - 2);
    }
    
    private void drawSnake(Graphics2D g2d, Snake snake, Color headColor, Color bodyColor) {
        // Desenha o corpo com gradiente
        for (int i = 1; i < snake.getCorpo().size(); i++) {
            Celula parte = snake.getCorpo().get(i);
            g2d.setColor(bodyColor);
            g2d.fillRoundRect(parte.getX() * tileSize, parte.getY() * tileSize,
                            tileSize - 2, tileSize - 2, 8, 8);
        }
        
        // Desenha a cabeça com efeito
        Celula cabeca = snake.getCabeca();
        g2d.setColor(headColor);
        g2d.fillRoundRect(cabeca.getX() * tileSize, cabeca.getY() * tileSize,
                         tileSize - 2, tileSize - 2, 8, 8);
        g2d.fillRoundRect(cabeca.getX() * tileSize, cabeca.getY() * tileSize,
                tileSize - 2, tileSize - 2, 8, 8);
    }
    
    private void drawInterface(Graphics2D g2d) {
        // Configura qualidade de renderização
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Background semi-transparente para a barra de status
        int barHeight = tileSize * 2; // Altura da barra baseada no tileSize
        int barY = boardHeight - barHeight;
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, barY, boardWidth, barHeight);
        
        // Linha separadora sutil
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.drawLine(0, barY, boardWidth, barY);
        
        // Cálculos proporcionais
        int padding = tileSize;
        int iconSize = (int)(tileSize * 1.2);
        int textY = barY + (barHeight * 2/3);
        int fontSize = tileSize * 4/5; // Fonte proporcional ao tileSize
        
        // Informações do Jogador 1 (lado esquerdo)
        // Ícone de cobra para Jogador 1
        g2d.setColor(SNAKE1_HEAD);
        g2d.fillRoundRect(padding, barY + (barHeight - iconSize)/2, 
                         iconSize, iconSize, tileSize/3, tileSize/3);
        
        // Texto do Jogador 1 com gradiente
        GradientPaint gradient1 = new GradientPaint(
            0, textY - fontSize, new Color(98, 255, 134),
            0, textY + 5, new Color(73, 190, 100)
        );
        g2d.setPaint(gradient1);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        String player1Text = "Snake 1: " + snake1.getCorpo().size();
        g2d.drawString(player1Text, padding + iconSize + tileSize/2, textY);
        
        // Contador de mortes do Jogador 1
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, fontSize * 2/3));
        g2d.setColor(new Color(255, 100, 100));
        String deaths1 = "x " + mortesSnake1;
        g2d.drawString(deaths1, padding + iconSize + tileSize*5, textY);
        
        // Informações do Bot (lado direito)
        int rightPadding = boardWidth - padding;
        
        // Ícone de cobra para Bot
        g2d.setColor(SNAKE2_HEAD);
        g2d.fillRoundRect(rightPadding - iconSize - tileSize*6, 
                         barY + (barHeight - iconSize)/2, 
                         iconSize, iconSize, tileSize/3, tileSize/3);
        
        // Texto do Bot com gradiente
        GradientPaint gradient2 = new GradientPaint(
            0, textY - fontSize, new Color(82, 139, 255),
            0, textY + 5, new Color(61, 103, 190)
        );
        g2d.setPaint(gradient2);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        String player2Text = "Snake 2: " + snake2.getCorpo().size();
        g2d.drawString(player2Text, rightPadding - tileSize*6, textY);
        
        // Contador de mortes do Bot
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, fontSize * 2/3));
        g2d.setColor(new Color(255, 100, 100));
        String deaths2 = "x " + mortesSnake2;
        g2d.drawString(deaths2, rightPadding - tileSize*2, textY);
        
        // Barras de progresso
        int maxSize = 10;
        int barWidth = tileSize * 4;
        int progressHeight = tileSize/8;
        int progressY = barY + barHeight - progressHeight - tileSize/4;
        
        // Barra de progresso Jogador 1
        drawProgressBar(g2d, padding + iconSize + tileSize/2, progressY, 
                       barWidth, progressHeight, 
                       snake1.getCorpo().size(), maxSize, SNAKE1_BODY);
        
        // Barra de progresso Bot
        drawProgressBar(g2d, rightPadding - tileSize*6, progressY, 
                       barWidth, progressHeight, 
                       snake2.getCorpo().size(), maxSize, SNAKE2_BODY);
    }
    
    private void drawProgressBar(Graphics2D g2d, int x, int y, int width, int height, 
                               int current, int max, Color color) {
        // Fundo da barra
        g2d.setColor(new Color(255, 255, 255, 30));
        g2d.fillRect(x, y, width, height);
        
        // Progresso
        int progress = (int)((float)current/max * width);
        g2d.setColor(color);
        g2d.fillRect(x, y, progress, height);
    }
    
    private void drawGameOver(Graphics2D g2d) {
        // Overlay semi-transparente
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, boardWidth, boardHeight);
        
        String mensagem;
        if (mortesSnake1 >= 5) {
            mensagem = "Bot Venceu!";
        } else if (mortesSnake2 >= 5) {
            mensagem = "Jogador Venceu!";
        } else if (snake1.getCorpo().size() >= 10) {
            mensagem = "Jogador Venceu!";
        } else {
            mensagem = "Bot Venceu!";
        }
        
        // Sombra do texto
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 50));
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (boardWidth - metrics.stringWidth(mensagem)) / 2;
        int y = boardHeight / 2;
        
        g2d.setColor(Color.BLACK);
        g2d.drawString(mensagem, x + 2, y + 2);
        
        // Texto principal com gradiente
        GradientPaint textGradient = new GradientPaint(
            x, y - 30, Color.WHITE,
            x, y + 10, new Color(255, 215, 0)
        );
        g2d.setPaint(textGradient);
        g2d.drawString(mensagem, x, y);
    }
}