package br.ufrn.imd.grafico;

import br.ufrn.imd.modelo.SnakeGame;

public class MenuPausa extends Menu {
    //private SnakeGame jogo;

    public MenuPausa(int boardWidth, int boardHeight, SnakeGame jogo) {
        super(boardWidth, boardHeight, 
              "JOGO PAUSADO",
              new String[]{"Continuar", "Menu Principal", "Sair"});
    }

    @Override
    public void selecionarOpcao() {
        switch (opcaoSelecionada) {
            case 0:
                break;
            case 1:
                break;
            case 2: // Sair
                System.exit(0);
                break;
        }
    }
}