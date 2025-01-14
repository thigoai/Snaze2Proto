package br.ufrn.imd.grafico;

import javax.swing.*;

import br.ufrn.imd.exception.LevelLoadException;
import br.ufrn.imd.modelo.SnakeGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MenuPrincipal extends Menu implements KeyListener {
    private static final int TAMANHO_TITULO = 60;
    private static final int TAMANHO_OPCOES = 30;
    private static final int TAMANHO_INSTRUCOES = 16;
    private static final int ESPACAMENTO_OPCOES = 50;
    private static final int MARGEM_OPCOES = 200;
    
    private static final int COBRA_TAMANHO = 5;
    private static final int COBRA_VELOCIDADE = 3;
    private static final int COBRA_Y = 100;
    private static final float FADE_VELOCIDADE = 0.05f;
    
    private static final Color COR_FUNDO = new Color(17, 17, 17);
    private static final Color COR_FUNDO_SECUNDARIA = new Color(25, 25, 25);
    private static final Color COR_TITULO = new Color(0, 255, 127);
    private static final Color COR_TEXTO = new Color(220, 220, 220);
    private static final Color COR_SELECAO = new Color(255, 215, 0);
    private static final Color COR_BORDA = new Color(30, 30, 30);
    private static final Color COR_SOMBRA_TITULO = new Color(0, 100, 0);
    private static final Color COR_COBRA = new Color(0, 255, 127);

    private final JFrame frame;
    private Timer animationTimer;
    private final List<Point> cobraAnimada;
    private int cobraPosX;
    private float alpha;
    private boolean fadeIn;

    public MenuPrincipal(int boardWidth, int boardHeight, JFrame frame) {
        super(boardWidth, boardHeight, "SNAZE",
              new String[]{"Single Player", "Multiplayer", "Arcade", "Sair"});
        
        this.frame = frame;
        this.cobraAnimada = new ArrayList<>();
        this.cobraPosX = -50;
        this.alpha = 0f;
        this.fadeIn = true;
        
        configurarUI();
        inicializarAnimacao();
    }
    
    private void configurarUI() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);
    }
    
    private void inicializarAnimacao() {
        inicializarCobra();
        inicializarTimer();
    }
    
    private void inicializarCobra() {
        for (int i = 0; i < COBRA_TAMANHO; i++) {
            cobraAnimada.add(new Point(cobraPosX - (i * 20), COBRA_Y));
        }
    }
    
    private void inicializarTimer() {
        animationTimer = new Timer();
        animationTimer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                atualizarAnimacao();
            }
        }, 0, 50);
    }
    
    private void atualizarAnimacao() {
        atualizarPosicaoCobra();
        atualizarFade();
        repaint();
    }
    
    private void atualizarPosicaoCobra() {
        cobraPosX += COBRA_VELOCIDADE;
        if (cobraPosX > boardWidth + 50) {
            cobraPosX = -50;
        }
        
        for (int i = 0; i < cobraAnimada.size(); i++) {
            cobraAnimada.get(i).x = cobraPosX - (i * 20);
        }
    }
    
    private void atualizarFade() {
        if (fadeIn) {
            alpha += FADE_VELOCIDADE;
            if (alpha >= 1.0f) {
                alpha = 1.0f;
                fadeIn = false;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = configurarGraphics2D(g);
        
        desenharFundo(g2d);
        desenharCobraAnimada(g2d);
        desenharTitulo(g2d);
        desenharInstrucoes(g2d);
        desenharOpcoes(g2d);
        desenharCreditos(g2d);
    }
    
    private Graphics2D configurarGraphics2D(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2d;
    }
    
    protected void desenharFundo(Graphics2D g2d) {
        GradientPaint gradient = new GradientPaint(0, 0, COR_FUNDO, 0, boardHeight, COR_FUNDO_SECUNDARIA);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, boardWidth, boardHeight);
    }
    
    private void desenharCobraAnimada(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(COR_COBRA);
        
        for (int i = 0; i < cobraAnimada.size(); i++) {
            Point p = cobraAnimada.get(i);
            int tamanho = 20 - i;
            g2d.fillRoundRect(p.x, p.y, tamanho, tamanho, 8, 8);
        }
    }
    
    protected void desenharTitulo(Graphics2D g2d) {
        g2d.setFont(new Font("Monospaced", Font.BOLD, TAMANHO_TITULO));
        FontMetrics metricas = g2d.getFontMetrics();
        int x = (boardWidth - metricas.stringWidth(titulo)) / 2;
        
        // Sombra
        g2d.setColor(COR_SOMBRA_TITULO);
        g2d.drawString(titulo, x + 3, boardHeight/3 + 3);
        
        // Título
        g2d.setColor(COR_TITULO);
        g2d.drawString(titulo, x, boardHeight/3);
    }
    
    private void desenharInstrucoes(Graphics2D g2d) {
        String[] instrucoes = getInstrucoesModoDeSelecionado();
        
        g2d.setFont(new Font("SansSerif", Font.PLAIN, TAMANHO_INSTRUCOES));
        g2d.setColor(COR_TEXTO);
        
        int y = boardHeight/2 - 30;
        for (String instrucao : instrucoes) {
            FontMetrics metricas = g2d.getFontMetrics();
            int x = (boardWidth - metricas.stringWidth(instrucao)) / 2;
            g2d.drawString(instrucao, x, y);
            y += 25;
        }
    }
    
    private String[] getInstrucoesModoDeSelecionado() {
        switch (opcoes[opcaoSelecionada]) {
            case "AVENTURA":
                return new String[]{
                    "Modo história com fases progressivas",
                    "Enfrente desafios cada vez mais difíceis",
                    "Desbloqueie novos labirintos",
                    "Complete objetivos especiais"
                };
            case "Single Player":
                return new String[]{
                    "↑ ↓ ← → para controlar a cobra",
                    "Colete as frutas para crescer",
                    "Evite as paredes e a cobra do bot",
                    "Vença chegando ao tamanho 10 ou eliminando o bot 5 vezes"
                };
            case "Multiplayer":
                return new String[]{
                    "Jogador 1: ↑ ↓ ← → para controlar",
                    "Jogador 2: WASD para controlar",
                    "Colete frutas e evite colisões",
                    "Vença chegando ao tamanho 10 primeiro"
                };
            default:
                return new String[]{
                    "Selecione um modo de jogo",
                    "Use ↑ ↓ para navegar",
                    "Pressione ENTER para selecionar",
                    ""
                };
        }
    }
    
    protected void desenharOpcoes(Graphics2D g2d) {
        g2d.setFont(new Font("SansSerif", Font.BOLD, TAMANHO_OPCOES));
        int y = boardHeight - MARGEM_OPCOES;
        
        for (int i = 0; i < opcoes.length; i++) {
            FontMetrics metricas = g2d.getFontMetrics();
            int x = (boardWidth - metricas.stringWidth(opcoes[i])) / 2;
            
            if (i == opcaoSelecionada) {
                desenharOpcaoSelecionada(g2d, opcoes[i], x, y);
            } else {
                g2d.setColor(COR_TEXTO);
                g2d.drawString(opcoes[i], x, y);
            }
            y += ESPACAMENTO_OPCOES;
        }
    }
    
    private void desenharOpcaoSelecionada(Graphics2D g2d, String opcao, int x, int y) {
        FontMetrics metricas = g2d.getFontMetrics();
        g2d.setColor(COR_BORDA);
        g2d.fillRoundRect(x - 30, y - 30, 
                         metricas.stringWidth(opcao) + 60, 40, 15, 15);
        g2d.setColor(COR_SELECAO);
        g2d.drawString(opcao, x, y);
    }
    
    private void desenharCreditos(Graphics2D g2d) {
        g2d.setFont(new Font("SansSerif", Font.BOLD, TAMANHO_INSTRUCOES));
        g2d.setColor(COR_TEXTO);
        g2d.drawString("Thiago F.", 10, boardHeight - 10);
    }

    @Override
    public void selecionarOpcao() {
        try {
            switch (getOpcaoSelecionada()) {
                case 0: // Single Player
                    iniciarJogo(GameMode.SINGLE_PLAYER);
                    break;
                case 1: // Multiplayer
                    iniciarJogo(GameMode.MULTIPLAYER);
                    break;
                case 2: // Aventura
                    iniciarJogo(GameMode.AVENTURA);
                    break;
                case 3: // Sair
                    System.exit(0);
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                "Erro ao iniciar jogo: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarJogo(GameMode modo) throws LevelLoadException {
        SnakeGame game = new SnakeGame(boardWidth, boardHeight, modo, this, frame);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(game);
		game.requestFocus();
		frame.revalidate();
		frame.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: navegarCima(); break;
            case KeyEvent.VK_DOWN: navegarBaixo(); break;
            case KeyEvent.VK_ENTER: selecionarOpcao(); break;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void navegarCima() {
        super.navegarCima();
    }

    @Override
    public void navegarBaixo() {
        super.navegarBaixo();
    }
}