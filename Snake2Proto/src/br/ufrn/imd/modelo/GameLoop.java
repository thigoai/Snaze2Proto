package br.ufrn.imd.modelo;

public class GameLoop implements Runnable {
    private SnakeGame game;
    private boolean running;
    private final int FPS = 10;
    private final long OPTIMAL_TIME = 1000000000 / FPS;
    
    public GameLoop(SnakeGame game) {
        this.game = game;
    }
    
    public void start() {
        running = true;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void stop() {
        running = false;
    }
    
    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        
        while (running) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            
            // Atualiza o estado do jogo
            game.update(null);
            
            // Calcula o tempo de espera para manter o FPS constante
            long sleepTime = (OPTIMAL_TIME - updateLength) / 1000000;
            
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}