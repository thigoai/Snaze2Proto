package br.ufrn.imd.modelo;

import java.util.Objects;

public class Celula {
	private int x;
	private int y;
	
	private boolean filled;
	private boolean visidada;
	
	
	
	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public boolean isVisidada() {
		return visidada;
	}

	public void setVisidada(boolean visidada) {
		this.visidada = visidada;
	}

	public Celula(int x, int y) {
		this.x = x;
		this.y = y;
		filled = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void updateX(int v) {
		this.x += v;
	}
	
	public void updateY(int v) {
		this.y += v;
	}
	
	public boolean checkFilled() {
		return filled;
	}
	
	public void fillIn() {
		filled = true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Celula outra = (Celula) obj;
		return x == outra.x && y == outra.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
