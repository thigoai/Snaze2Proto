package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;

public class Snake {
	protected List<Celula> corpo;
	protected int direcaoX;
	protected int direcaoY;
	
	public Snake(int x, int y) {
		corpo = new ArrayList<>();
		corpo.add(new Celula(x, y)); // Cabeça
		direcaoX = 1; // Direção inicial para direita
		direcaoY = 0;
	}
	
	public void setDirecao(int dx, int dy) {
		this.direcaoX = dx;
		this.direcaoY = dy;
	}
	
	public Celula getCabeca() {
		return corpo.get(0);
	}
	
	public void mover() {
		Celula cabeca = getCabeca();
		Celula novaCabeca = new Celula(
			cabeca.getX() + direcaoX,
			cabeca.getY() + direcaoY
		);
		
		corpo.add(0, novaCabeca);
		corpo.remove(corpo.size() - 1);
	}
	
	public void crescer() {
		Celula cauda = corpo.get(corpo.size() - 1);
		corpo.add(new Celula(cauda.getX(), cauda.getY()));
	}
	
	public List<Celula> getCorpo() {
		return corpo;
	}
	
	public boolean colisao(Celula outra) {
		Celula cabeca = getCabeca();
		return cabeca.getX() == outra.getX() && cabeca.getY() == outra.getY();
	}
	
	public boolean colisaoComCorpo() {
		Celula cabeca = getCabeca();
		// Começa do índice 1 pois 0 é a própria cabeça
		for (int i = 1; i < corpo.size(); i++) {
			Celula parte = corpo.get(i);
			if (cabeca.getX() == parte.getX() && cabeca.getY() == parte.getY()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean colisaoComOutraSnake(Snake outraSnake) {
		Celula cabeca = getCabeca();
		for (Celula parte : outraSnake.getCorpo()) {
			if (cabeca.getX() == parte.getX() && cabeca.getY() == parte.getY()) {
				return true;
			}
		}
		return false;
	}
}
