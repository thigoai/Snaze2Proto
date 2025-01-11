package br.ufrn.imd.modelo;

public class Celula {
	private int x;
	private int y;
	
	private boolean filled;
	
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
}
