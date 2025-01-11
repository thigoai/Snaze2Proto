package br.ufrn.imd.modelo;

import java.util.ArrayList;


public abstract class Snake {
	
	protected Celula cabeca;
	protected ArrayList<Celula> corpo;
	protected int velocityX;
	protected int velocityY;
	protected boolean estaViva;
	
	public Snake(Celula pontoDeNascimento) {
		cabeca = pontoDeNascimento;
		corpo = new ArrayList<Celula>();
		velocityX = velocityY = 0;
		estaViva = true;
	}

	public Celula getCabeca() {
		return cabeca;
	}

	public void setCabeca(Celula cabeca) {
		this.cabeca = cabeca;
	}

	public ArrayList<Celula> getCorpo() {
		return corpo;
	}

	public void setBody(ArrayList<Celula> corpo) {
		this.corpo = corpo;
	}

	public int getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

	public boolean isEstaViva() {
		return estaViva;
	}

	public void setEstaViva(boolean estaViva) {
		this.estaViva = estaViva;
	}
	
	public boolean colisao(Celula c) {
		return cabeca.getX() == c.getX() && cabeca.getY() == c.getY();
	}
	
	public void mover() {
		for(int i = corpo.size() - 1; i >= 0; i--) {
			Celula parte = corpo.get(i);
			if(i == 0){
				parte.setX(cabeca.getX());
				parte.setY(cabeca.getY());
			}else {
				Celula parteAntes= corpo.get(i-1);
				parte.setX(parteAntes.getX());
				parte.setY(parteAntes.getY());
			}
		}
		
		cabeca.updateX(velocityX);
		cabeca.updateY(velocityY);
	}
	
	public void crescer() {
		Celula rabo = corpo.get(corpo.size() - 1);
        corpo.add(rabo);
	}


}
