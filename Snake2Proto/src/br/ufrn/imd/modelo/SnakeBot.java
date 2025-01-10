package br.ufrn.imd.modelo;

import java.util.LinkedList;
import java.util.Queue;

public class SnakeBot extends Snake{
	
	private Queue<Direcao> caminho;
	private Direcao direcao;
	
	
	public SnakeBot(Celula pontoDeNascimento) {
		super(pontoDeNascimento);
		caminho = new LinkedList<>();
		this.direcao = Direcao.UP;
	}

	public void setDirecao(Direcao novaDirecao) {
		if (direcao == null || direcao != novaDirecao.getOposto()) {
	        this.direcao = novaDirecao;
	    }
        if (this.direcao!= novaDirecao.getOposto()) {
            this.direcao = novaDirecao;
        }
    }

	public Celula getProximaPosicao() {
	    if (direcao == Direcao.UP) {
	        return new Celula(cabeca.getX(), cabeca.getY() - 1);
	    } else if (direcao == Direcao.DOWN) {
	        return new Celula(cabeca.getX(), cabeca.getY() + 1);
	    } else if (direcao == Direcao.LEFT) {
	        return new Celula(cabeca.getX() - 1, cabeca.getY());
	    } else if (direcao == Direcao.RIGHT) {
	        return new Celula(cabeca.getX() + 1, cabeca.getY());
	    } else {
	        throw new IllegalStateException("Direção inválida");
	    }
	}

    
    public void pensar(Celula objetivo) {
        if (objetivo.getX() > cabeca.getX()) caminho.add(Direcao.RIGHT);
        else if (objetivo.getX() < cabeca.getX()) caminho.add(Direcao.LEFT);
        else if (objetivo.getY() > cabeca.getY()) caminho.add(Direcao.DOWN);
        else if (objetivo.getY() < cabeca.getY()) caminho.add(Direcao.UP);
    }

}
