package br.ufrn.imd.grafico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import br.ufrn.imd.dao.PlayerData;
import br.ufrn.imd.modelo.SnakeGame;

public class MenuSelecaoJogador extends Menu implements KeyListener {
    // Constantes de UI (mantendo consistência com MenuPrincipal)
    private static final int TAMANHO_TITULO = 60;
    private static final int TAMANHO_OPCOES = 24;
    private static final int TAMANHO_INSTRUCOES = 16;
    private static final int ESPACAMENTO_OPCOES = 40;
    private static final int MARGEM_LATERAL = 100;
    private static final int MARGEM_OPCOES = 350;
    
    // Cores do tema (mesmas do MenuPrincipal)
    private static final Color COR_FUNDO = new Color(17, 17, 17);
    private static final Color COR_FUNDO_SECUNDARIA = new Color(25, 25, 25);
    private static final Color COR_TITULO = new Color(0, 255, 127);
    private static final Color COR_TEXTO = new Color(220, 220, 220);
    private static final Color COR_SELECAO = new Color(255, 215, 0);
    private static final Color COR_BORDA = new Color(30, 30, 30);
    private static final Color COR_SOMBRA_TITULO = new Color(0, 100, 0);

    private final JFrame frame;
    private final SnakeGame game;
    private List<PlayerData> jogadores;

    public MenuSelecaoJogador(int boardWidth, int boardHeight, JFrame frame, SnakeGame game) {
        super(boardWidth, boardHeight, "SELECIONE O JOGADOR", new String[]{});
        this.frame = frame;
        this.game = game;
        
        configurarUI();
        carregarJogadores();
    }
    
    private void configurarUI() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);
    }
    
    private void carregarJogadores() {
        PlayerData loader = new PlayerData();
        jogadores = loader.loadAllPlayers("../Snake2Proto/resources/playersData/playersData.txt");
        jogadores.add(0, new PlayerData("Novo Jogo", 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = configurarGraphics2D(g);
        
        desenharFundo(g2d);
        desenharJogadores(g2d);
        desenharInstrucoes(g2d);
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
    
    
    private void desenharJogadores(Graphics2D g2d) {
        g2d.setFont(new Font("SansSerif", Font.BOLD, TAMANHO_OPCOES));
        int y = (boardHeight/2);
        
        for (int i = 0; i < jogadores.size(); i++) {
            PlayerData jogador = jogadores.get(i);
            String texto = jogador.getPlayerName();
            if (!texto.equals("Novo Jogo")) {
                texto += " - " + jogador.getPlayerScore() + " pontos";
            }
            
            int x = MARGEM_LATERAL;
            
            if (i == opcaoSelecionada) {
                desenharOpcaoSelecionada(g2d, texto, x, y);
            } else {
                g2d.setColor(COR_TEXTO);
                g2d.drawString(texto, x, y);
            }
            y += ESPACAMENTO_OPCOES;
        }
    }
    
    private void desenharOpcaoSelecionada(Graphics2D g2d, String opcao, int x, int y) {
        FontMetrics metricas = g2d.getFontMetrics();
        g2d.setColor(COR_BORDA);
        g2d.fillRoundRect(x - 15, y - 25, 
                         metricas.stringWidth(opcao) + 30, 35, 15, 15);
        g2d.setColor(COR_SELECAO);
        g2d.drawString(opcao, x, y);
    }
    
    private void desenharInstrucoes(Graphics2D g2d) {
        g2d.setFont(new Font("SansSerif", Font.PLAIN, TAMANHO_INSTRUCOES));
        g2d.setColor(COR_TEXTO);
        String instrucao = "Use ↑↓ para navegar e ENTER para selecionar";
        FontMetrics metricas = g2d.getFontMetrics();
        int x = (boardWidth - metricas.stringWidth(instrucao)) / 2;
        g2d.drawString(instrucao, x, boardHeight - 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                opcaoSelecionada = Math.max(0, opcaoSelecionada - 1);
                break;
            case KeyEvent.VK_DOWN:
                opcaoSelecionada = Math.min(jogadores.size() - 1, opcaoSelecionada + 1);
                break;
            case KeyEvent.VK_ENTER:
                selecionarJogador();
                break;
        }
        repaint();
    }
    
    private void selecionarJogador() {
        if (opcaoSelecionada == 0) {
            String nome = JOptionPane.showInputDialog(frame, "Digite seu nome:");
            if (nome != null && !nome.trim().isEmpty()) {
                PlayerData novoJogador = new PlayerData(nome, 0);
                novoJogador.savePlayerData("../Snake2Proto/resources/playersData/playersData.txt");
                game.iniciarJogoAventura(novoJogador);
            }
        } else {
            game.iniciarJogoAventura(jogadores.get(opcaoSelecionada));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

	@Override
	public void selecionarOpcao() {
		// TODO Auto-generated method stub
		
	}
}