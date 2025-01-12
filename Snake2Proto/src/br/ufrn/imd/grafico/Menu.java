package br.ufrn.imd.grafico;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public abstract class Menu extends JPanel {
    protected int boardWidth;
    protected int boardHeight;
    protected int opcaoSelecionada = 0;
    protected String[] opcoes;
    protected String titulo;
    protected Font fonteOpcoes;
    protected Font fonteTitulo;
    protected Color corFundo;
    protected Color corTexto;
    protected Color corSelecao;

    public Menu(int boardWidth, int boardHeight, String titulo, String[] opcoes) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.titulo = titulo;
        this.opcoes = opcoes;
        
        // Configurações padrão
        this.fonteTitulo = new Font("Arial", Font.BOLD, 36);
        this.fonteOpcoes = new Font("Arial", Font.BOLD, 24);
        this.corFundo = new Color(0, 0, 0, 180);
        this.corTexto = Color.WHITE;
        this.corSelecao = Color.YELLOW;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Desenha o fundo
        desenharFundo(g2d);
        
        // Desenha o título
        desenharTitulo(g2d);
        
        // Desenha as opções
        desenharOpcoes(g2d);
    }

    protected void desenharFundo(Graphics2D g2d) {
        g2d.setColor(corFundo);
        g2d.fillRect(0, 0, boardWidth, boardHeight);
    }

    protected void desenharTitulo(Graphics2D g2d) {
        g2d.setFont(fonteTitulo);
        g2d.setColor(corTexto);
        FontMetrics fm = g2d.getFontMetrics();
        int x = (boardWidth - fm.stringWidth(titulo))/2;
        g2d.drawString(titulo, x, boardHeight/2 - 80);
    }

    protected void desenharOpcoes(Graphics2D g2d) {
        g2d.setFont(fonteOpcoes);
        FontMetrics fm = g2d.getFontMetrics();
        int y = boardHeight/2 - 20;
        
        for (int i = 0; i < opcoes.length; i++) {
            if (i == opcaoSelecionada) {
                g2d.setColor(corSelecao);
                String opcaoSelecionada = "> " + opcoes[i] + " <";
                g2d.drawString(opcaoSelecionada, 
                    (boardWidth - fm.stringWidth(opcaoSelecionada))/2, y);
            } else {
                g2d.setColor(corTexto);
                g2d.drawString(opcoes[i], 
                    (boardWidth - fm.stringWidth(opcoes[i]))/2, y);
            }
            y += 40;
        }
    }

    public void navegarCima() {
        opcaoSelecionada--;
        if (opcaoSelecionada < 0) {
            opcaoSelecionada = opcoes.length - 1;
        }
    }

    public void navegarBaixo() {
        opcaoSelecionada++;
        if (opcaoSelecionada >= opcoes.length) {
            opcaoSelecionada = 0;
        }
    }

    public int getOpcaoSelecionada() {
        return opcaoSelecionada;
    }

    // Método abstrato que cada menu específico deve implementar
    public abstract void selecionarOpcao();
    
    public enum GameMode {
        AVENTURA,
        SINGLE_PLAYER,
        MULTIPLAYER
    }

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}