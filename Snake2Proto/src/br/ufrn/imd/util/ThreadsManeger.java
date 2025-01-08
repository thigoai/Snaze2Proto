package br.ufrn.imd.util;

import br.ufrn.imd.modelo.SnakeGame;
import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadsManeger {
    private ScheduledExecutorService executorService;
    private SnakeGame snakeGame;
    static int boardWidth = 600;
	static int boardHeigth = boardWidth;

    public ThreadsManeger(SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
        executorService = Executors.newScheduledThreadPool(3);

        executorService.scheduleAtFixedRate(this::gameLoop, 0, 150, TimeUnit.MILLISECONDS);
        executorService.scheduleAtFixedRate(this::botControl, 0, 150, TimeUnit.MILLISECONDS);
        executorService.scheduleAtFixedRate(this::checkCollisions, 0, 150, TimeUnit.MILLISECONDS);
    }

    private void gameLoop() {
        if (!snakeGame.checkGameOver()) {
            snakeGame.move();
            snakeGame.repaint();
        }
    }

    private void botControl() {
        if (!snakeGame.isGameOver()) {
           
        }
    }

    private void checkCollisions() {
        if (snakeGame.isGameOver()) {
            snakeGame.setGameOver(true); 
            executorService.shutdown(); 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game with Threads");
            SnakeGame game = new SnakeGame(boardHeigth, boardWidth); 
            new ThreadsManeger(game);        
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
