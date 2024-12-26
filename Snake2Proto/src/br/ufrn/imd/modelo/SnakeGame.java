package br.ufrn.imd.modelo;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener, 
KeyListener {
	
	//private Tile snakeHead;
	private ArrayList<Tile> snakeBody;
	private int velocityX;
	private int velocityY;
	
	private Tile botHead;
	private ArrayList<Tile> botBody;
	private int botVelocityX;
	private int botVelocityY;
	
	
	private Tile food;
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
		
		snakeHead = new Tile(5, 5);
		snakeBody = new ArrayList<Tile>();
		velocityX = 0;
		velocityY = 0;
		
		botHead = new Tile(5, 5);
		botBody = new ArrayList<Tile>();
		botVelocityX = 0;
		botVelocityY = 0;
		
		food = new Tile(10, 10);
		random = new Random();
		placeFood();
		
		gameLoopTimer = new Timer(100, this);
		gameLoopTimer.start();		
	}
	
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 draw(g);
	}
	
	public void drawFood(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(food.getX()*tileSize, food.getY()*tileSize, tileSize, tileSize);
	}
	
	public void drawSnake(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(snakeHead.getX()*tileSize, snakeHead.getY()*tileSize, tileSize, tileSize);
	
		for(int i = 0; i < snakeBody.size(); i++) {
			g.fillRect(snakeBody.get(i).getX()*tileSize, snakeBody.get(i).getY()*tileSize, tileSize, tileSize);
		}
	}
	
	public void drawBot(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(botHead.getX()*tileSize, botHead.getY()*tileSize, tileSize, tileSize);
		
		for(int i = 0; i < botBody.size(); i++) {
			g.fillRect(botBody.get(i).getX()*tileSize, botBody.get(i).getY()*tileSize, tileSize, tileSize);
		}
	}
	
	public void drawScore(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 19));
			if(gameOver) {
				g.setColor(Color.red);
				g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
			}else {
				g.setColor(Color.green);
				g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
			}
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i <boardWidth/tileSize; i++) {
			g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
			g.drawLine(0, i*tileSize, boardWidth, i*tileSize);	
		}
	
		drawFood(g);
		drawSnake(g);
		drawBot(g);
		drawScore(g);
		
	}
	
	public void placeFood() {
		food.setX(random.nextInt(boardWidth/tileSize));
		food.setY(random.nextInt(boardHeight/tileSize));
	}
	
	public boolean collision(Tile t1, Tile t2) {
		return t1.getX() == t2.getX() && t1.getY() == t2.getY();
	}
	
	public boolean checkGameOver() {
		for(int i = 0; i < snakeBody.size(); i++) {
			if(collision(snakeBody.get(i), snakeHead)) {
				return true;
			}
		}
		if(snakeHead.getX()*tileSize < 0 || snakeHead.getX()*tileSize > boardWidth 
				|| snakeHead.getY()*tileSize < 0 || snakeHead.getY()*tileSize > boardHeight) {
			return true;
		}else {
			return false;
		}
	}
	
	public void botControl() {
		if(botHead.getY() < food.getY() && botVelocityY != 1) {
			botVelocityX = 0;
			botVelocityY = 1;
		}
		if(botHead.getY() > food.getY() && botVelocityY != -1) {
			botVelocityX = 0;
			botVelocityY = -1;
		}
		
		if(botHead.getX() < food.getX() && botVelocityX != 1) {
			botVelocityX = 1;
			botVelocityY = 0;
		}
		if(botHead.getX() > food.getX() && botVelocityX != -1) {
			botVelocityX = -1;
			botVelocityY = 0;
		}
	}

	
	public void move() {
		if(collision(snakeHead, food)) {
			Tile snakePart = new Tile(food.getX(), food.getY());
			snakeBody.add(snakePart);
			placeFood();
		}
		
		for(int i = snakeBody.size() - 1; i >= 0; i--) {
			Tile snakePart = snakeBody.get(i);
			if(i == 0){
				snakePart.setX(snakeHead.getX());
				snakePart.setY(snakeHead.getY());
			}else {
				Tile prevSnakePart = snakeBody.get(i-1);
				snakePart.setX(prevSnakePart.getX());
				snakePart.setY(prevSnakePart.getY());
			}
		}
		
		snakeHead.updateX(velocityX);
		snakeHead.updateY(velocityY);
	}
	
	public void moveBot() {
		if(collision(botHead, food)) {
			Tile botPart = new Tile(food.getX(), food.getY());
			botBody.add(botPart);
			placeFood();
		}
		
		for(int i = botBody.size() - 1; i >= 0; i--) {
			Tile botPart = botBody.get(i);
			if(i == 0){
				botPart.setX(botHead.getX());
				botPart.setY(botHead.getY());
			}else {
				Tile prevBotPart = botBody.get(i-1);
				botPart.setX(prevBotPart.getX());
				botPart.setY(prevBotPart.getY());
			}
		}
		
		botHead.updateX(botVelocityX);
		botHead.updateY(botVelocityY);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameOver = checkGameOver();
		move();
		botControl();
		moveBot();
		repaint();
		if(gameOver) {
			gameLoopTimer.stop();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
			velocityX = 0;
			velocityY = -1;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
			velocityX = 0;
			velocityY = 1;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
			velocityX = -1;
			velocityY = 0;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
			velocityX = 1;
			velocityY = 0;
		}
		
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
