package br.ufrn.imd.modelo;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import br.ufrn.imd.dao.LevelLoader;
import br.ufrn.imd.dao.PlayerData;
import br.ufrn.imd.grafico.Menu;
import br.ufrn.imd.grafico.MenuPrincipal;
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
	
	public SnakeGame(int boardWidth, int boardHeight, GameMode gameMode, MenuPrincipal menuPrincipal, JFrame frame) {
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
		
		// Inicialização baseada no modo de jogo
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
		playerData = new PlayerData();
		playerData.setPlayerName("Thiago");
		playerData.setPlayerScore(500);
		
		playerData.loadPlayerData("/Snake2Proto/resources/playersData");
		
	}
	
	private void inicializarModoSinglePlayer() {
		int[][] layout = LevelLoader.loadLevel("../Snake2Proto/resources/levels/level1.txt");
		
		// Inicializa o labirinto com o layout carregado
		labirinto = new Labirinto();
		labirinto.gerarLabirinto(layout);
		
		// Inicializa as cobras em posições válidas
		pontoDeNascimento1 = new Celula(5, 5);
		pontoDeNascimento2 = new Celula(18, 5);
		snake1 = new SnakePlayer(pontoDeNascimento1);
		snake2 = new SnakeBot(pontoDeNascimento2, labirinto);
		
		inicializarComida();
	}
	
	private void inicializarModoMultiplayer() {
		int[][] layout = LevelLoader.loadLevel("../Snake2Proto/resources/levels/level1.txt");
		
		// Inicializa o labirinto com o layout carregado
		labirinto = new Labirinto();
		labirinto.gerarLabirinto(layout);
		
		// Inicializa as cobras em posições válidas
		pontoDeNascimento1 = new Celula(5, 5);
		pontoDeNascimento2 = new Celula(18, 5);
		snake1 = new SnakePlayer(pontoDeNascimento1);
		snake2 = new SnakePlayer(pontoDeNascimento2);
		
		inicializarComida();
	}
	
	// Método auxiliar para inicializar a comida
	private void inicializarComida() {
		posicoesValidasComida = new ArrayList<>();
		calcularPosicoesValidasComida();
		
		comida = new Celula(20, 20);
		proximaComida = new Celula(5, 5);
		random = new Random();
		placeFood();
	}
	
	
	public Labirinto getLabirinto() {
		return labirinto;
	}
	
	public void setLabirinto(Labirinto labirinto) {
		this.labirinto = labirinto;
	}

	public Celula getComida() {
		return comida;
	}
	
	public void setComida(Celula comida) {
		this.comida = comida;
	}


	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Timer getGameLoopTimer() {
		return gameLoopTimer;
	}

	public void setGameLoopTimer(Timer gameLoopTimer) {
		this.gameLoopTimer = gameLoopTimer;
	}



	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}


	public int getBoardWidth() {
		return boardWidth;
	}



	public void setBoardWidth(int boardWidth) {
		this.boardWidth = boardWidth;
	}



	public int getBoardHeight() {
		return boardHeight;
	}



	public void setBoardHeight(int boardHeight) {
		this.boardHeight = boardHeight;
	}



	public int getTileSize() {
		return tileSize;
	}



	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
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

//	private void atualizarModoAventura() {
//		if (jogoIniciado && !snake1Morta) {
//			snake1.mover();
//			
//			if (verificarColisaoParede(snake1) || snake1.colisaoComCorpo()) {
//				snake1Morta = true;
//				piscadas = 0;
//				mortesSnake1++;
//				
//				if (mortesSnake1 >= MAX_MORTES) {
//					gameOver = true;
//					return;
//				}
//			}
//		}
//		
//		// Verifica se completou o nível atual
//		if (snake1.getCorpo().size() >= TAMANHO_VITORIA) {
//			if (currentLevel < levels.size() - 1) {
//				currentLevel++;
//				carregarProximoNivel();
//			} else {
//				gameOver = true; // Completou todos os níveis
//			}
//		}
//	}
	
//	private void carregarProximoNivel() {
//		LevelLoader.Level level = levels.get(currentLevel);
//		labirinto.gerarLabirinto(level.getLayout());
//		reiniciarSnake(snake1, level.getStartPoint());
//		calcularPosicoesValidasComida();
//		placeFood();
//	}

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
		// Limpa todo o corpo e reinicia com tamanho 1
		snake.getCorpo().clear();
		snake.getCorpo().add(new Celula(pontoNascimento.getX(), pontoNascimento.getY()));
		if (snake instanceof SnakePlayer) {
			((SnakePlayer)snake).setDirecao(1, 0);
		} else if (snake instanceof SnakeBot) {
			((SnakeBot)snake).setDirecao(1, 0);
		}
	}

	private void atualizarModoSinglePlayer() {
		// Movimento do jogador
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
		
		// Movimento do bot
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
		
		// Gerencia piscadas e reinicialização
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
		
		// Verificar colisões com comida
		if (!snake1Morta && snake1.colisao(comida)) {
			snake1.crescer();
			placeFood();
		}
		
		if (!snake2Morta && snake2.colisao(comida)) {
			snake2.crescer();
			placeFood();
		}
		
		// Atualiza o render
		render.setSnake1Morta(snake1Morta);
		render.setSnake2Morta(snake2Morta);
		render.setPiscadas(piscadas);
		render.setGameOver(gameOver);
		render.setMortes(mortesSnake1, mortesSnake2);
		
		repaint();
	}

	private void atualizarModoMultiplayer() {
		// Movimento do jogador 1
		if (jogoIniciado && !snake1Morta) {
			snake1.mover();
			
			if (verificarColisaoParede(snake1) || 
				snake1.colisaoComCorpo() || 
				snake1.colisaoComOutraSnake(snake2)) {
					snake1Morta = true;
					piscadas = 0;
					mortesSnake1++;
					pontosSnake2++; // Incrementa pontos do jogador 2 quando jogador 1 morre
					if (mortesSnake1 >= MAX_MORTES) {
						gameOver = true;
						return;
					}
			}
		}
		
		// Movimento do jogador 2
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
		
		// Gerencia piscadas e reinicialização
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
		
		// Verificar colisões com comida
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
		
		// Verifica condição de vitória por pontos
		if (pontosSnake1 >= TAMANHO_VITORIA || pontosSnake2 >= TAMANHO_VITORIA) {
			gameOver = true;
			return;
		}
		
		// Atualiza o render
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
}