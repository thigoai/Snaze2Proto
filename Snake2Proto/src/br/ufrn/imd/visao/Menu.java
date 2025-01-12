package br.ufrn.imd.visao;

import javax.swing.*;

import br.ufrn.imd.modelo.SnakeGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JPanel {
    private final int largura;
    private final int altura;
    private JFrame frame;
    private boolean jogoIniciado = false;
    
    // Cores mais vibrantes e modernas
    private final Color corFundo = new Color(17, 17, 17);
    private final Color corTitulo = new Color(0, 255, 127);
    private final Color corTexto = new Color(220, 220, 220);
    private final Color corSelecao = new Color(255, 215, 0);
    private final Color corBorda = new Color(30, 30, 30);
    
    private String[] opcoes = {"AVENTURA", "1 JOGADOR", "2 JOGADORES", "SAIR"};
    private int opcaoSelecionada = 0;
    
    // Efeitos visuais
    private float alpha = 0f;
    private boolean fadeIn = true;
    private Timer animationTimer;
    
    // Atributos para animação da cobra
    private List<Point> cobraAnimada = new ArrayList<>();
    private int cobraPosX = -50; // Começa fora da tela
    private final int COBRA_TAMANHO = 5;
    private final int COBRA_VELOCIDADE = 3;
    private final Color corCobra = new Color(0, 255, 127);
    private final int COBRA_Y = 100; // Posição Y fixa da cobra
    
    public Menu(int largura, int altura, JFrame frame) {
        this.largura = largura;
        this.altura = altura;
        this.frame = frame;
        
        setPreferredSize(new Dimension(largura, altura));
        setBackground(corFundo);
        setFocusable(true);
        
        // Inicializa a cobra animada
        for (int i = 0; i < COBRA_TAMANHO; i++) {
            cobraAnimada.add(new Point(cobraPosX - (i * 20), COBRA_Y));
        }
        
        // Modifica o timer para incluir a animação da cobra
        animationTimer = new Timer(50, e -> {
            if (fadeIn) {
                alpha += 0.05f;
                if (alpha >= 1f) {
                    alpha = 1f;
                    fadeIn = false;
                }
            }
            
            // Atualiza posição da cobra
            cobraPosX += COBRA_VELOCIDADE;
            if (cobraPosX > largura + 50) { // Reset quando sair da tela
                cobraPosX = -50;
            }
            
            // Atualiza todos os segmentos da cobra
            Point cabeca = cobraAnimada.get(0);
            cabeca.x = cobraPosX;
            for (int i = cobraAnimada.size() - 1; i > 0; i--) {
                Point atual = cobraAnimada.get(i);
                Point anterior = cobraAnimada.get(i - 1);
                atual.x = anterior.x - 20;
                atual.y = anterior.y;
            }
            
            repaint();
        });
        animationTimer.start();
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        opcaoSelecionada = (opcaoSelecionada - 1 + opcoes.length) % opcoes.length;
                        repaint();
                        break;
                    case KeyEvent.VK_DOWN:
                        opcaoSelecionada = (opcaoSelecionada + 1) % opcoes.length;
                        repaint();
                        break;
                    case KeyEvent.VK_ENTER:
                        selecionarOpcao();
                        break;
                }
            }
        });
    }
    
    private void selecionarOpcao() {
        if (!jogoIniciado) {
            switch (opcoes[opcaoSelecionada]) {
                case "AVENTURA":
                    iniciarJogo(GameMode.AVENTURA);
                    break;
                case "1 JOGADOR":
                    iniciarJogo(GameMode.SINGLE_PLAYER);
                    break;
                case "2 JOGADORES":
                    iniciarJogo(GameMode.MULTIPLAYER);
                    break;
                case "SAIR":
                    int confirmacao = JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente sair do jogo?",
                        "Confirmar Saída",
                        JOptionPane.YES_NO_OPTION
                    );
                    if (confirmacao == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                    break;
            }
        }
    }
    
    private void iniciarJogo(GameMode modo) {
        jogoIniciado = true;
        animationTimer.stop();
        frame.getContentPane().removeAll();
        SnakeGame jogo = new SnakeGame(largura, altura, modo);
        frame.add(jogo);
        frame.pack();
        jogo.requestFocus();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Desenha gradiente de fundo
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(17, 17, 17),
            0, altura, new Color(25, 25, 25));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, largura, altura);
        
        // Aplica transparência para efeito fade
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        
        // Desenha a cobra animada (adicionar antes do título)
        g2d.setColor(corCobra);
        for (int i = 0; i < cobraAnimada.size(); i++) {
            Point p = cobraAnimada.get(i);
            int tamanho = 20 - i; // Cada segmento fica um pouco menor
            g2d.fillRoundRect(p.x, p.y, tamanho, tamanho, 8, 8);
        }
        
        // Desenha o título com sombra
        g2d.setFont(new Font("Monospaced", Font.BOLD, 60));
        String titulo = "SNAZE";
        FontMetrics metricas = g2d.getFontMetrics();
        int x = (largura - metricas.stringWidth(titulo)) / 2;
        
        // Sombra do título
        g2d.setColor(new Color(0, 100, 0));
        g2d.drawString(titulo, x + 3, altura/3 + 3);
        
        // Título
        g2d.setColor(corTitulo);
        g2d.drawString(titulo, x, altura/3);
        
        // Atualiza as instruções baseado no modo selecionado
        String[] instrucoes;
        switch (opcoes[opcaoSelecionada]) {
            case "AVENTURA":
                instrucoes = new String[]{
                    "Modo história com fases progressivas",
                    "Enfrente desafios cada vez mais difíceis",
                    "Desbloqueie novos labirintos",
                    "Complete objetivos especiais"
                };
                break;
            case "1 JOGADOR":
                instrucoes = new String[]{
                    "↑ ↓ ← → para controlar a cobra",
                    "Colete as frutas para crescer",
                    "Evite as paredes e a cobra do bot",
                    "Vença chegando ao tamanho 10 ou eliminando o bot 5 vezes"
                };
                break;
            case "2 JOGADORES":
                instrucoes = new String[]{
                    "Jogador 1: ↑ ↓ ← → para controlar",
                    "Jogador 2: WASD para controlar",
                    "Colete frutas e evite colisões",
                    "Vença chegando ao tamanho 10 primeiro"
                };
                break;
            default:
                instrucoes = new String[]{
                    "Selecione um modo de jogo",
                    "Use ↑ ↓ para navegar",
                    "Pressione ENTER para selecionar",
                    ""
                };
        }
        
        // Desenha as instruções
        int y = altura/2 - 30;
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
        g2d.setColor(corTexto);
        for (String instrucao : instrucoes) {
            FontMetrics metricas1 = g2d.getFontMetrics();
            int x1 = (largura - metricas1.stringWidth(instrucao)) / 2;
            g2d.drawString(instrucao, x1, y);
            y += 25;
        }
        
        // Desenha as opções do menu com efeito hover
        y = altura - 200;
        g2d.setFont(new Font("SansSerif", Font.BOLD, 30));
        
        for (int i = 0; i < opcoes.length; i++) {
            metricas = g2d.getFontMetrics();
            x = (largura - metricas.stringWidth(opcoes[i])) / 2;
            
            if (i == opcaoSelecionada) {
                // Desenha retângulo de seleção com borda arredondada
                g2d.setColor(corBorda);
                g2d.fillRoundRect(x - 30, y - 30, metricas.stringWidth(opcoes[i]) + 60, 40, 15, 15);
                g2d.setColor(corSelecao);
                g2d.drawString(opcoes[i], x, y);
            } else {
                g2d.setColor(corTexto);
                g2d.drawString(opcoes[i], x, y);
            }
            y += 50;
        }
        
     // Desenha as instruções
        y = altura - 10;
        g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2d.setColor(corTexto);
        g2d.drawString("Thiago F.", x, y);
       
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snaze");
            Menu menu = new Menu(600, 600, frame);
            frame.add(menu);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            menu.requestFocus();
        });
    }
    
    // Enum para os modos de jogo
    public enum GameMode {
        AVENTURA,
        SINGLE_PLAYER,
        MULTIPLAYER
    }
}