package br.com.insertkoin.calculadhora;

public class FAQ {

    private String pergunta;
    private String resposta;

    public FAQ(String pergunta, String resposta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public String getResposta() {
        return resposta;
    }

}
