package br.ufrn.imd.modelo;

import java.awt.event.KeyEvent;

public class SnakePlayer extends Snake {

	public SnakePlayer(Celula posicaoInicial) {
		super(posicaoInicial.getX(), posicaoInicial.getY());
	}
	
	public void receberComando1(KeyEvent e) {
		int novaX = direcaoX;
		int novaY = direcaoY;
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (direcaoY != 1) {
					novaX = 0;
					novaY = -1;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direcaoY != -1) {
					novaX = 0;
					novaY = 1;
				}
				break;
			case KeyEvent.VK_LEFT:
				if (direcaoX != 1) {
					novaX = -1;
					novaY = 0;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direcaoX != -1) {
					novaX = 1;
					novaY = 0;
				}
				break;
		}
		
		setDirecao(novaX, novaY);
	}
}
