package br.ufrn.imd.modelo;

public class SnakeBot extends Snake {
    private Labirinto labirinto;
    
    public SnakeBot(Celula posicaoInicial, Labirinto lab) {
        super(posicaoInicial.getX(), posicaoInicial.getY());
        this.labirinto = lab;
    }
    
    public void calcularCaminho(Celula comida) {
        Celula cabeca = getCabeca();
        boolean mudouDirecao = false;
        
        if (labirinto.ehParede(cabeca.getY() + direcaoY, cabeca.getX() + direcaoX)) {
           
            if (cabeca.getX() < comida.getX() && !labirinto.ehParede(cabeca.getY(), cabeca.getX() + 1) && direcaoX != -1) {
                setDirecao(1, 0);
                mudouDirecao = true;
            } 
            else if (cabeca.getX() > comida.getX() && !labirinto.ehParede(cabeca.getY(), cabeca.getX() - 1) && direcaoX != 1) {
                setDirecao(-1, 0);
                mudouDirecao = true;
            }
            else if (cabeca.getY() < comida.getY() && !labirinto.ehParede(cabeca.getY() + 1, cabeca.getX()) && direcaoY != -1) {
                setDirecao(0, 1);
                mudouDirecao = true;
            }
            else if (cabeca.getY() > comida.getY() && !labirinto.ehParede(cabeca.getY() - 1, cabeca.getX()) && direcaoY != 1) {
                setDirecao(0, -1);
                mudouDirecao = true;
            }
            
            if (!mudouDirecao) {
                if (!labirinto.ehParede(cabeca.getY(), cabeca.getX() + 1) && direcaoX != -1) {
                    setDirecao(1, 0);
                }
                else if (!labirinto.ehParede(cabeca.getY(), cabeca.getX() - 1) && direcaoX != 1) {
                    setDirecao(-1, 0);
                }
                else if (!labirinto.ehParede(cabeca.getY() + 1, cabeca.getX()) && direcaoY != -1) {
                    setDirecao(0, 1);
                }
                else if (!labirinto.ehParede(cabeca.getY() - 1, cabeca.getX()) && direcaoY != 1) {
                    setDirecao(0, -1);
                }
            }
        }
        
        else if (!mudouDirecao) {
            if (cabeca.getX() < comida.getX() && !labirinto.ehParede(cabeca.getY(), cabeca.getX() + 1) && direcaoX != -1) {
                setDirecao(1, 0);
            }
            else if (cabeca.getX() > comida.getX() && !labirinto.ehParede(cabeca.getY(), cabeca.getX() - 1) && direcaoX != 1) {
                setDirecao(-1, 0);
            }
            else if (cabeca.getY() < comida.getY() && !labirinto.ehParede(cabeca.getY() + 1, cabeca.getX()) && direcaoY != -1) {
                setDirecao(0, 1);
            }
            else if (cabeca.getY() > comida.getY() && !labirinto.ehParede(cabeca.getY() - 1, cabeca.getX()) && direcaoY != 1) {
                setDirecao(0, -1);
            }
        }
    }
    
    public void executarMovimento() {
        mover();
    }
}
