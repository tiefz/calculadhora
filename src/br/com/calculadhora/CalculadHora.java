package br.com.calculadhora;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CalculadHora {


	public static void main(String[] args) {
	
		String entrada = JOptionPane.showInputDialog("Digite a hora de entrada");
		String almocoS = JOptionPane.showInputDialog("Digite a hora de saída para o almoço");
		String almocoV = JOptionPane.showInputDialog("Digite a hora de retorno do almoço");
		
		Intervalo intervalo = new Intervalo();
		intervalo.setEntrada(entrada);
		Pausa almoco = new Pausa(almocoS, almocoV);
				
	    Object opcoes[] = { "Sim", "Não" };
	    int resposta = JOptionPane.showOptionDialog(null, "Você fez pausa extra?", "Pausa Extra",
	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes,
	        opcoes[0]);
		
		String pausaS = "00:00";
		String pausaV = "00:00";
		
		Pausa descanso = new Pausa(pausaS, pausaV);
	    
		List<Pausa> pausas  = new ArrayList<>();
		pausas.add(almoco);
		pausas.add(descanso);

		
	    while (resposta == 0) {
	    
			pausaS = JOptionPane.showInputDialog("Digite a hora da saída de pausa");
			pausaV = JOptionPane.showInputDialog("Digite a hora de retorno de pausa");
			
			descanso = new Pausa(pausaS, pausaV);
			
			pausas.add(descanso);
			
			resposta = JOptionPane.showOptionDialog(null, "Você fez mais alguma pausa?", "Pausa Extra",
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes,
			        opcoes[0]);
		
	    }
	    
		CalculoSaida calculaSaida = new CalculoSaida();
		
		intervalo.setPausas(pausas);
		
		Intervalo resultado = calculaSaida.calcularSaida(intervalo);
		
		JOptionPane.showMessageDialog(null, "Você precisa sair às: " + resultado.getSaida());		
		
	}

	
}
