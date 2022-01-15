package br.com.rodrigo;

import br.com.rodrigo.model.Tabuleiro;
import br.com.rodrigo.view.TabuleiroConsole;

public class Aplicacao {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);

        new TabuleiroConsole(tabuleiro);
    }
}
