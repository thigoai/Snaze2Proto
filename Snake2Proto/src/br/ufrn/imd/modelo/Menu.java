package br.ufrn.imd.modelo;

import javax.swing.*;

import br.ufrn.imd.util.ThreadsManeger;

import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel {
    private boolean jogoIniciado = false;
    private final int largura;
    private final int altura;
    private JFrame frame;
    
    public Menu(int largura, int altura, JFrame frame) {
        this.largura = largura;
        this.altura = altura;
        this.frame = frame;
        
        setPreferredSize(new Dimension(largura, altura));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        // Adiciona listener para tecla ENTER
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !jogoIniciado) {
                    iniciarJogo();
                }
            }
        });
    }
    
    private void iniciarJogo() {
        jogoIniciado = true;
        frame.getContentPane().removeAll();
        SnakeGame jogo = new SnakeGame(largura, altura);
        frame.add(jogo);
        frame.pack();
        jogo.requestFocus();
        ThreadsManeger threadsManager = new ThreadsManeger(jogo);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Desenha o título
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String titulo = "Snake Game";
        FontMetrics metricas = g.getFontMetrics();
        int x = (largura - metricas.stringWidth(titulo)) / 2;
        g.drawString(titulo, x, altura / 3);
        
        // Desenha as instruções
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String[] instrucoes = {
            "Use as setas para controlar a cobra verde",
            "Colete a comida vermelha para crescer",
            "Evite as paredes e não colida com a cobra azul",
            "Vença chegando ao tamanho 10 ou fazendo o bot morrer 5 vezes",
            "",
            "Pressione ENTER para começar"
        };
        
        int y = altura / 2;
        for (String instrucao : instrucoes) {
            metricas = g.getFontMetrics();
            x = (largura - metricas.stringWidth(instrucao)) / 2;
            g.drawString(instrucao, x, y);
            y += 30;
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        Menu menu = new Menu(800, 600, frame);
        frame.add(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        menu.requestFocus();
    }
}