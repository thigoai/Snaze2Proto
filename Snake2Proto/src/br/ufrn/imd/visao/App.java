package br.ufrn.imd.visao;

import javax.swing.*;

import br.ufrn.imd.modelo.SnakeGame;
import br.ufrn.imd.util.ThreadsManeger;

public class App {
	
	static int boardWidth = 600;
	static int boardHeigth = boardWidth;
			
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game");
            Menu menu = new Menu(600, 600, frame);
            frame.add(menu);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            menu.requestFocus();
        });
    }
}

