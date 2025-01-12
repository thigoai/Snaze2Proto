package br.ufrn.imd.visao;


import java.awt.Component;

import javax.swing.*;

import br.ufrn.imd.grafico.MenuPrincipal;
import br.ufrn.imd.modelo.SnakeGame;

public class App {
	
	static int boardWidth = 600;
	static int boardHeigth = boardWidth;
			
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game");
            MenuPrincipal menuPrinciapal = new MenuPrincipal(600, 600, frame);
            frame.add(menuPrinciapal);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            menuPrinciapal.requestFocus();
        });
    }
}

