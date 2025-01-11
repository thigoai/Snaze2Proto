package br.ufrn.imd.modelo;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
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
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};


	private SnakePlayer snake1;
	

	private Snake snake2;
	
	private Labirinto labirinto;
	
	private Celula comida;
	
	private Random random;
	private Timer gameLoopTimer;
	private boolean gameOver = false;
	
	private int boardWidth;
	private int boardHeight;
	private int tileSize = 25;
	
	public SnakeGame(int boardWidth, int boardHeight){
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		
		Celula pontoDeNascimento1 = new Celula(5,5);
		snake1 = new SnakePlayer(pontoDeNascimento1);
		
		Celula pontoDeNascimento2 = new Celula(6,6);
		snake2 = new SnakePlayer(pontoDeNascimento2);
		
		labirinto = new Labirinto();
		
		comida = new Celula(10, 10);
		
		random = new Random();
		placeFood();
		
		gameLoopTimer = new Timer(100, this);
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

	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 draw(g);
	}
	
	public void drawFood(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(comida.getX()*tileSize, comida.getY()*tileSize, tileSize, tileSize);
	}
	
	public void drawSnake(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(snake1.getCabeca().getX()*tileSize, snake1.getCabeca().getY()*tileSize, tileSize, tileSize);
	
		for(int i = 0; i < snake1.getCorpo().size(); i++) {
			g.fillRect(snake1.getCorpo().get(i).getX()*tileSize, snake1.getCorpo().get(i).getY()*tileSize, tileSize, tileSize);
		}
		
	}
	
	public void drawScore(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 19));
			if(gameOver) {
				g.setColor(Color.red);
				g.drawString("Game Over: " + String.valueOf(snake1.getCorpo().size()), tileSize - 16, tileSize);
			}else {
				g.setColor(Color.green);
				g.drawString("Score: " + String.valueOf(snake1.getCorpo()), tileSize - 16, tileSize);
			}
	}
	
	public void drawMaze(Graphics g) {
		for(Celula c : labirinto.getLayout()) {
			g.setColor(Color.gray);
			g.fillRect(c.getX()*tileSize, c.getY()*tileSize, tileSize, tileSize);
		}
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i <boardWidth/tileSize; i++) {
			g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
			g.drawLine(0, i*tileSize, boardWidth, i*tileSize);	
		}
		
		drawFood(g);
		drawSnake(g);
		drawMaze(g);
		drawScore(g);
		
	}
	
	public void placeFood() {
		comida.setX(random.nextInt(boardWidth/tileSize));
		comida.setY(random.nextInt(boardHeight/tileSize));
		if (cells[comida.getX()][comida.getY()] == 1) {
	        placeFood();
	    }
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
	    long start = System.nanoTime();
	    
	    //gameOver = checkGameOver();
	    long checkGameOverTime = System.nanoTime();
//	    if (gameOver) {
//	        gameLoopTimer.stop();
//	    }
	    
	    //move();
	    snake1.mover();
	    long moveTime = System.nanoTime();
	    
	    //botControl();
	    long botControlTime = System.nanoTime();
	    
	    //moveBot();
	    long moveBotTime = System.nanoTime();
	    
	    repaint();
	    long repaintTime = System.nanoTime();
	    
	    // Log do tempo gasto em cada operação
	    System.out.println("checkGameOver: " + (checkGameOverTime - start) / 1_000_000 + " ms");
	    System.out.println("move: " + (moveTime - checkGameOverTime) / 1_000_000 + " ms");
	    System.out.println("botControl: " + (botControlTime - moveTime) / 1_000_000 + " ms");
	    System.out.println("moveBot: " + (moveBotTime - botControlTime) / 1_000_000 + " ms");
	    System.out.println("repaint: " + (repaintTime - moveBotTime) / 1_000_000 + " ms");
	}


	@Override
	public void keyPressed(KeyEvent e) {
		snake1.receberComando1(e);
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
