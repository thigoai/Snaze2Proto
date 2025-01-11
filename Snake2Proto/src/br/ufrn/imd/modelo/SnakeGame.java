package br.ufrn.imd.modelo;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, 
KeyListener {
	
	int[][] cells = {
		    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};


	private SnakePlayer snake1;
	
	private SnakeBot snake2;
	
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
	
	public SnakeGame(int boardWidth, int boardHeight){
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		
		labirinto = new Labirinto();
		labirinto.gerarLabirinto(cells);
		
		posicoesValidasComida = new ArrayList<>();
		calcularPosicoesValidasComida();
		
		pontoDeNascimento1 = new Celula(5,5);
		pontoDeNascimento2 = new Celula(6,6);
		snake1 = new SnakePlayer(pontoDeNascimento1);
		snake2 = new SnakeBot(pontoDeNascimento2, labirinto);
		
		comida = new Celula(10, 10);
		proximaComida = new Celula(5, 5);
		random = new Random();
		placeFood();
		
		render = new Render(snake1, snake2, comida, proximaComida, labirinto, tileSize, boardHeight, boardWidth);
		
		gameLoopTimer = new Timer(150, this);
		gameLoopTimer.start();		
	}
	
	public int[][] getCells() {
		return cells;
	}



	public void setCells(int[][] cells) {
		this.cells = cells;
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
		if (gameOver) return;

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
						return; // Para o jogo imediatamente se atingir máximo de mortes
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
						return; // Para o jogo imediatamente se atingir máximo de mortes
					}
			}
		}
		
		// Verifica condição de vitória por tamanho
		if (snake1.getCorpo().size() >= TAMANHO_VITORIA || 
			snake2.getCorpo().size() >= TAMANHO_VITORIA) {
			gameOver = true;
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
		snake1.receberComando1(e); // Muda a direção
		if (!jogoIniciado) {
			jogoIniciado = true; // Inicia o jogo no primeiro comando
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
}
