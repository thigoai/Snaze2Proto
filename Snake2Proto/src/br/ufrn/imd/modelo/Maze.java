package br.ufrn.imd.modelo;

import java.util.ArrayList;

public class Maze {
	
	private ArrayList<Tile> mazeLevel;
	
	public void generateMaze(int[][] cells) {
		mazeLevel = new ArrayList<>();
		
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				if(cells[i][j] == 1) {
					mazeLevel.add(new Tile(i, j));
				}
			}
		}
	}
	
	public ArrayList<Tile> getMazeLevel(){
		return mazeLevel;
	}
}
