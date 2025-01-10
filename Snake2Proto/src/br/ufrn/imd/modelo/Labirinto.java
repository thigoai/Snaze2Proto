package br.ufrn.imd.modelo;

import java.util.ArrayList;

public class Labirinto {
	
	private ArrayList<Celula> layout;
	
	public Labirinto() {
		layout = new ArrayList<Celula>();
	}
	
	public void gerarLabirinto(int[][] celulas) {
		layout= new ArrayList<>();
		
		for(int i = 0; i < celulas.length; i++) {
			for(int j = 0; j < celulas[i].length; j++) {
				if(celulas[i][j] == 1) {
					layout.add(new Celula(i, j));
				}
			}
		}
	}
	
	public ArrayList<Celula> getLayout(){
		return layout;
	}
	
	public boolean ehParede(int x, int y) {
	    Celula parede = new Celula(x, y);
	    for (Celula c : layout) {
	        if (c.equals(parede)) {
	            return true;
	        }
	    }
	    return false; 
	}

}
