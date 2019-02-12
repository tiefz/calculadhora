package br.com.calculadhora;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CalculadHora {


	public static void main(String[] args) {
	
		String entrada = JOptionPane.showInputDialog("Digite a hora de entrada");
		String almocoS = JOptionPane.showInputDialog("Digite a hora de saída para o almoço");
		String almocoV = JOptionPane.showInputDialog("Digite a hora de retorno do almoço");
		
	    Object opcoes[] = { "Sim", "Não" };
	    int resposta = JOptionPane.showOptionDialog(null, "Você fez pausa extra?", "Pausa Extra",
	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes,
	        opcoes[0]);
		
		String pausaS = "00:00";
		String pausaV = "00:00";
		
		Pausa descanso = new Pausa(pausaS, pausaV);
	    
	    if (resposta == 0) {
	    
			pausaS = JOptionPane.showInputDialog("Digite a hora da saída de pausa");
			pausaV = JOptionPane.showInputDialog("Digite a hora de retorno de pausa");
			
			descanso = new Pausa(pausaS, pausaV);
		
	    }
	    
		CalculoSaida calculaSaida = new CalculoSaida();
		
		Intervalo intervalo = new Intervalo();
		intervalo.setEntrada(entrada);
		Pausa almoco = new Pausa(almocoS, almocoV);
		
		List<Pausa> pausas  = new ArrayList<>();
		pausas.add(almoco);
		pausas.add(descanso);
		
		intervalo.setPausas(pausas);
		
		Intervalo resultado = calculaSaida.calcularSaida(intervalo);
		
		JOptionPane.showMessageDialog(null, "Você precisa sair às: " + resultado.getSaida());		
		
	}

	
}
