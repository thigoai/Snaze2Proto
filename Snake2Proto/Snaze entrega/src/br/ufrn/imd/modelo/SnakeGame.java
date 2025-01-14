package br.ufrn.imd.modelo;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import br.ufrn.imd.dao.LevelLoader;
import br.ufrn.imd.dao.PlayerData;
import br.ufrn.imd.exception.LevelLoadException;
import br.ufrn.imd.grafico.Menu;
import br.ufrn.imd.grafico.MenuPrincipal;
import br.ufrn.imd.grafico.MenuSelecaoJogador;
import br.ufrn.imd.grafico.Render;
import br.ufrn.imd.grafico.Menu.GameMode;

public class SnakeGame extends JPanel implements ActionListener, 
KeyListener {

	private GameMode gameMode;
	private Menu menu;
	private PlayerData playerData;
	private Snake snake1;
	private Snake snake2;
	private Labirinto labirinto;
	private Celula comida;
	private Celula proximaComida;
	private Random random;
	private Timer gameLoopTimer;
	private boolean gameOver = false;
	private int boardWidth;
	private int boardHeight;
	private int tileSize = 25;
	private Render render;
	private boolean jogoIniciado = false;
	private List<Celula> posicoesValidasComida;
	private boolean snake1Morta = false;
	private boolean snake2Morta = false;
	private int piscadas = 0;
	private final int MAX_PISCADAS = 6;
	private Celula pontoDeNascimento1;
	private Celula pontoDeNascimento2;
	
	private int pontosSnake1 = 0;
	private int pontosSnake2 = 0;
	private int mortesSnake1 = 0;
	private int mortesSnake2 = 0;
	private final int TAMANHO_VITORIA = 20;
	private final int MAX_MORTES = 5;
	
	private boolean pausado = false;
	
	private Menu menuPausa;
	private MenuPrincipal menuPrincipal;
	private JFrame frame;
	
	public SnakeGame(int boardWidth, int boardHeight, GameMode gameMode, MenuPrincipal menuPrincipal, JFrame frame) throws LevelLoadException {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.gameMode = gameMode;
		this.menuPrincipal = menuPrincipal;
		this.frame = frame;
		
		// Cria o menu de pausa
		this.menuPausa = new Menu(boardWidth, boardHeight, "JOGO PAUSADO", 
			new String[]{"Continuar", "Menu Principal", "Sair"}) {
			@Override
			public void selecionarOpcao() {
				switch (getOpcaoSelecionada()) {
					case 0: // Continuar
						pausarJogo();
						break;
					case 1: // Menu Principal
						voltarMenuPrincipal();
						break;
					case 2: // Sair
						System.exit(0);
						break;
				}
			}
		};
		
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		
		switch (gameMode) {
			case AVENTURA:
				inicializarModoAventura();
				break;
			case SINGLE_PLAYER:
				inicializarModoSinglePlayer();
				break;
			case MULTIPLAYER:
				inicializarModoMultiplayer();
				break;
		}
		
		render = new Render(snake1, snake2, comida, proximaComida, labirinto, 
						  tileSize, boardHeight, boardWidth);
		
		gameLoopTimer = new Timer(150, this);
		gameLoopTimer.start();
	}
	
	private void inicializarModoAventura() {
		MenuSelecaoJogador menuSelecao = new MenuSelecaoJogador(boardWidth, boardHeight, frame, this);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(menuSelecao);
		menuSelecao.requestFocus();
		frame.revalidate();
		frame.repaint();
		gameLoopTimer.stop(); 
	}
	
	private void inicializarModoSinglePlayer() throws LevelLoadException {
		int[][] layout = LevelLoader.loadLevel("../Snake2Proto/resources/levels/level1.txt");
		
		labirinto = new Labirinto();
		labirinto.gerarLabirinto(layout);
		
		pontoDeNascimento1 = new Celula(5, 5);
		pontoDeNascimento2 = new Celula(18, 5);
		snake1 = new SnakePlayer(pontoDeNascimento1);
		snake2 = new SnakeBot(pontoDeNascimento2, labirinto);
		
		inicializarComida();
	}
	
	private void inicializarModoMultiplayer() throws LevelLoadException {
		int[][] layout = LevelLoader.loadLevel("../Snake2Proto/resources/levels/level1.txt");
		
		labirinto = new Labirinto();
		labirinto.gerarLabirinto(layout);
		
		pontoDeNascimento1 = new Celula(5, 5);
		pontoDeNascimento2 = new Celula(18, 5);
		snake1 = new SnakePlayer(pontoDeNascimento1);
		snake2 = new SnakePlayer(pontoDeNascimento2);
		
		inicializarComida();
	}
	
	private void inicializarComida() {
		posicoesValidasComida = new ArrayList<>();
		calcularPosicoesValidasComida();
		
		comida = new Celula(20, 20);
		proximaComida = new Celula(5, 5);
		random = new Random();
		placeFood();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameOver || pausado) return;

		switch (gameMode) {
			case AVENTURA:
				//atualizarModoAventura();
				break;
			case SINGLE_PLAYER:
				atualizarModoSinglePlayer();
				break;
			case MULTIPLAYER:
				atualizarModoMultiplayer();
				break;
		}
	}

	private boolean verificarColisaoParede(Snake snake) {
		Celula cabeca = snake.getCabeca();
		// Verifica colisão com as bordas do mapa
		if (cabeca.getX() < 0 || cabeca.getX() >= boardWidth/tileSize ||
			cabeca.getY() < 0 || cabeca.getY() >= boardHeight/tileSize) {
			return true;
		}
		// Verifica colisão com paredes do labirinto
		return labirinto.ehParede(cabeca.getY(), cabeca.getX());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			pausarJogo();
			return;
		}

		if (pausado) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					menuPausa.navegarCima();
					break;
				case KeyEvent.VK_DOWN:
					menuPausa.navegarBaixo();
					break;
				case KeyEvent.VK_ENTER:
					menuPausa.selecionarOpcao();
					break;
			}
			repaint();
			return;
		}

		if (!jogoIniciado) {
			jogoIniciado = true;
		}

		if (gameMode == GameMode.MULTIPLAYER) {
			snake1.receberComando1(e);
			if (snake2 instanceof SnakePlayer) {
				((SnakePlayer)snake2).receberComando2(e);
			}
		} else {
			snake1.receberComando1(e);
		}
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render.draw(g);

		if (pausado) {
			menuPausa.draw(g);
		}
	}
	
	private void calcularPosicoesValidasComida() {
		posicoesValidasComida.clear();
		for (int y = 1; y < boardHeight/tileSize - 1; y++) {
			for (int x = 1; x < boardWidth/tileSize - 1; x++) {
				if (!labirinto.ehParede(y, x)) {
					posicoesValidasComida.add(new Celula(x, y));
				}
			}
		}
	}
	
	public void placeFood() {
		if (posicoesValidasComida.isEmpty()) {
			calcularPosicoesValidasComida();
		}
		
		if (!posicoesValidasComida.isEmpty()) {
			comida.setX(proximaComida.getX());
			comida.setY(proximaComida.getY());
			
			int index = random.nextInt(posicoesValidasComida.size());
			Celula novaPos = posicoesValidasComida.get(index);
			proximaComida.setX(novaPos.getX());
			proximaComida.setY(novaPos.getY());
		}
	}
	
	public List<Celula> getPosicoesValidasComida() {
		return posicoesValidasComida;
	}

	public Celula getProximaComida() {
		return proximaComida;
	}

	private void reiniciarSnake(Snake snake, Celula pontoNascimento) {
		snake.getCorpo().clear();
		snake.getCorpo().add(new Celula(pontoNascimento.getX(), pontoNascimento.getY()));
		if (snake instanceof SnakePlayer) {
			((SnakePlayer)snake).setDirecao(1, 0);
		} else if (snake instanceof SnakeBot) {
			((SnakeBot)snake).setDirecao(1, 0);
		}
	}

	private void atualizarModoSinglePlayer() {
		if (jogoIniciado && !snake1Morta) {
			snake1.mover();
			
			if (verificarColisaoParede(snake1) || 
				snake1.colisaoComCorpo() || 
				snake1.colisaoComOutraSnake(snake2)) {
					snake1Morta = true;
					piscadas = 0;
					mortesSnake1++;
					if (mortesSnake1 >= MAX_MORTES) {
						gameOver = true;
						return;
					}
			}
		}
		
		if (!snake2Morta) {
			snake2.calcularCaminho(comida);
			snake2.executarMovimento();
			
			if (verificarColisaoParede(snake2) || 
				snake2.colisaoComCorpo() || 
				snake2.colisaoComOutraSnake(snake1)) {
					snake2Morta = true;
					piscadas = 0;
					mortesSnake2++;
					if (mortesSnake2 >= MAX_MORTES) {
						gameOver = true;
						return;
					}
			}
		}
		
		if ((snake1Morta || snake2Morta) && !gameOver) {
			piscadas++;
			if (piscadas >= MAX_PISCADAS) {
				if (snake1Morta) {
					reiniciarSnake(snake1, pontoDeNascimento1);
					snake1Morta = false;
				}
				if (snake2Morta) {
					reiniciarSnake(snake2, pontoDeNascimento2);
					snake2Morta = false;
				}
				piscadas = 0;
			}
		}
		
		if (!snake1Morta && snake1.colisao(comida)) {
			snake1.crescer();
			placeFood();
		}
		
		if (!snake2Morta && snake2.colisao(comida)) {
			snake2.crescer();
			placeFood();
		}
		
		render.setSnake1Morta(snake1Morta);
		render.setSnake2Morta(snake2Morta);
		render.setPiscadas(piscadas);
		render.setGameOver(gameOver);
		render.setMortes(mortesSnake1, mortesSnake2);
		
		repaint();
	}

	private void atualizarModoMultiplayer() {
		if (jogoIniciado && !snake1Morta) {
			snake1.mover();
			
			if (verificarColisaoParede(snake1) || 
				snake1.colisaoComCorpo() || 
				snake1.colisaoComOutraSnake(snake2)) {
					snake1Morta = true;
					piscadas = 0;
					mortesSnake1++;
					pontosSnake2++;
					if (mortesSnake1 >= MAX_MORTES) {
						gameOver = true;
						return;
					}
			}
		}
		
		if (!snake2Morta) {
			snake2.mover();
			
			if (verificarColisaoParede(snake2) || 
				snake2.colisaoComCorpo() || 
				snake2.colisaoComOutraSnake(snake1)) {
					snake2Morta = true;
					piscadas = 0;
					mortesSnake2++;
					pontosSnake1++; 
					if (mortesSnake2 >= MAX_MORTES) {
						gameOver = true;
						return;
					}
			}
		}
		
		if ((snake1Morta || snake2Morta) && !gameOver) {
			piscadas++;
			if (piscadas >= MAX_PISCADAS) {
				if (snake1Morta) {
					reiniciarSnake(snake1, pontoDeNascimento1);
					snake1Morta = false;
				}
				if (snake2Morta) {
					reiniciarSnake(snake2, pontoDeNascimento2);
					snake2Morta = false;
				}
				piscadas = 0;
			}
		}
		
		if (!snake1Morta && snake1.colisao(comida)) {
			snake1.crescer();
			pontosSnake1++;
			placeFood();
		}
		
		if (!snake2Morta && snake2.colisao(comida)) {
			snake2.crescer();
			pontosSnake2++;
			placeFood();
		}
		
		if (pontosSnake1 >= TAMANHO_VITORIA || pontosSnake2 >= TAMANHO_VITORIA) {
			gameOver = true;
			return;
		}
		
		render.setSnake1Morta(snake1Morta);
		render.setSnake2Morta(snake2Morta);
		render.setPiscadas(piscadas);
		render.setGameOver(gameOver);
		render.setMortes(mortesSnake1, mortesSnake2);
		render.setPontos(pontosSnake1, pontosSnake2);
		
		repaint();
	}

	private void pausarJogo() {
		pausado = !pausado;
		if (pausado) {
			gameLoopTimer.stop();
		} else {
			gameLoopTimer.start();
		}
		repaint();
	}

	public void voltarMenuPrincipal() {
		gameLoopTimer.stop();
		
		pausado = false;
		gameOver = false;
		jogoIniciado = false;
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(menuPrincipal);
		menuPrincipal.requestFocus();
		frame.revalidate();
		frame.repaint();
	}

	public void iniciarJogoAventura(PlayerData jogador) throws LevelLoadException {
		this.playerData = jogador;
		
		int[][] layout = LevelLoader.loadLevel("../Snake2Proto/resources/levels/level1.txt");
		labirinto = new Labirinto();
		labirinto.gerarLabirinto(layout);
		
		pontoDeNascimento1 = new Celula(5, 5);
		snake1 = new SnakePlayer(pontoDeNascimento1);
		
		inicializarComida();
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(this);
		this.requestFocus();
		frame.revalidate();
		frame.repaint();
		gameLoopTimer.start();
	}
}