package br.ufrn.imd.modelo;

import java.awt.event.KeyEvent;

public class SnakePlayer extends Snake{

	public SnakePlayer(Celula pontoDeNascimento) {
		super(pontoDeNascimento);
	}
	
	public void receberComando1(KeyEvent e) {
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
	
	public void receberComando2(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W && velocityY != 1) {
			velocityX = 0;
			velocityY = -1;
		}
		else if(e.getKeyCode() == KeyEvent.VK_S && velocityY != -1) {
			velocityX = 0;
			velocityY = 1;
		}
		else if(e.getKeyCode() == KeyEvent.VK_A && velocityX != 1) {
			velocityX = -1;
			velocityY = 0;
		}
		else if(e.getKeyCode() == KeyEvent.VK_D && velocityX != -1) {
			velocityX = 1;
			velocityY = 0;
		}
	}
}
