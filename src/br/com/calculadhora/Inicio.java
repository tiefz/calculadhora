package br.com.calculadhora;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Inicio {
	
	public static JPanel tela;
	public static JFrame frame;
	
	public static void main(String[] args) {
		criarComponentes();
	}

	private static void criarComponentes() {
		frame = new JFrame ("CalculadHora");
		frame.setSize(400, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		tela = new Tela();
		tela.setVisible(true);
		frame.add(tela);
		frame.setVisible(true);
	}

}
