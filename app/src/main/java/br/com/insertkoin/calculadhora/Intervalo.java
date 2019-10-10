package br.com.insertkoin.calculadhora;

import java.util.List;

public class Intervalo {
    private String entrada;

    private List<Pausa> pausas;

    private String saida;

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public List<Pausa> getPausas() {
        return pausas;
    }

    public void setPausas(List<Pausa> pausas) {
        this.pausas = pausas;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }
}
