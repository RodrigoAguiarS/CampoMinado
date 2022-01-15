package br.com.rodrigo.model;

import br.com.rodrigo.exception.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarOsVizinhos();
        sortearMinas();
    }
    public void abrir(int linha, int coluna) {
        try {
            campos.parallelStream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst().ifPresent(c -> c.abrir());

        }catch (ExplosaoException explosaoException){
            campos.forEach(c -> c.setAberto(true));
            throw explosaoException;

        }
    }
    public void alternarMarcacao(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst().ifPresent(c -> c.altenarMarcacao());

    }
    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();
        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        }while (minasArmadas < minas);
    }

    private void associarOsVizinhos() {
        for (Campo c1: campos){
            for(Campo c2: campos){
                c1.adicionarVizinho(c2);
            }
        }
    }
    private void gerarCampos() {
        for (int i = 0; i < linhas; i++){
            for (int j = 0; j < colunas; j++){
                campos.add(new Campo(i, j));

            }
        }
    }
    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(campo -> campo.objetivoAlcancado());
    }
    public void reiniciar(){
       campos.stream().forEach(c -> c.reiniciar());
       sortearMinas();
    }
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        for (int c = 0; c < colunas; c++){
            stringBuilder.append(" ");
            stringBuilder.append(c);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");
        int i = 0;
        for (int l = 0; l < linhas; l++){
            stringBuilder.append(l);
            stringBuilder.append(" ");
            for (int c = 0; c < colunas; c++){
                stringBuilder.append(" ");
                stringBuilder.append(campos.get(i));
                stringBuilder.append(" ");
                i++;
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
