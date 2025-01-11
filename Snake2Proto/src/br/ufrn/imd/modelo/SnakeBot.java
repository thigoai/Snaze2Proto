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
        
        // Se está indo em direção a uma parede, tenta desviar
        if (labirinto.ehParede(cabeca.getY() + direcaoY, cabeca.getX() + direcaoX)) {
            // Tenta primeiro desviar mantendo o movimento na direção do objetivo
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
            
            // Se não conseguiu desviar mantendo direção ao objetivo, tenta qualquer direção livre
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
        // Se não há parede à frente, tenta se mover em direção à comida
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
