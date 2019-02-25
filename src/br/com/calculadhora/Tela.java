package br.com.calculadhora;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;



public class Tela extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel labelTitulo;
	JLabel labelEntrada;
	JFormattedTextField campoEntrada;
	JLabel labelAlmocoS;
	JFormattedTextField campoAlmocoS;
	JLabel labelAlmocoV;
	JFormattedTextField campoAlmocoV;
	JButton addPausa;
	JLabel labelPausa;
	JLabel textoPausa;
	JFormattedTextField campoPausaS;
	JFormattedTextField campoPausaV;
	JButton botaoCalc;
	JLabel horaSaida;
	JButton sair;
	
	List<Pausa> pausas  = new ArrayList<>();

	public Tela() {
		criarComponentes();
		criarEventos();
	}
	
	private void criarComponentes() {
		setLayout(null);
		
		labelTitulo = new JLabel("Programa Calculadhora", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelEntrada = new JLabel("Horário de entrada:", JLabel.LEFT);
		campoEntrada = new JFormattedTextField();
		campoEntrada.setValue("00:00");
		try {
			MaskFormatter dateMask = new MaskFormatter("##:##");
			dateMask.install(campoEntrada);
			dateMask.setValidCharacters("0,1,2,3,4,5,6,7,8,9, ");
		}catch(ParseException ex) {
			Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
		}
		labelAlmocoS = new JLabel("Saída para almoço:", JLabel.LEFT);
		campoAlmocoS = new JFormattedTextField();
		campoAlmocoS.setValue("00:00");
		try {
			MaskFormatter dateMask = new MaskFormatter("##:##");
			dateMask.install(campoAlmocoS);
			dateMask.setValidCharacters("0,1,2,3,4,5,6,7,8,9, ");
		}catch(ParseException ex) {
			Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
		}
		labelAlmocoV = new JLabel("Volta do almoço:", JLabel.LEFT);
		campoAlmocoV = new JFormattedTextField();
		campoAlmocoV.setValue("00:00");
		try {
			MaskFormatter dateMask = new MaskFormatter("##:##");
			dateMask.install(campoAlmocoV);
			dateMask.setValidCharacters("0,1,2,3,4,5,6,7,8,9, ");
		}catch(ParseException ex) {
			Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
		}
		addPausa = new JButton("Inserir pausas");
		labelPausa = new JLabel("00:00", JLabel.RIGHT);
		labelPausa.setFont(new Font(labelPausa.getFont().getName(), Font.PLAIN, 16));
		textoPausa = new JLabel("Pausa total", JLabel.RIGHT);
		campoPausaS = new JFormattedTextField();
		campoPausaS.setValue("00:00");
		try {
			MaskFormatter dateMask = new MaskFormatter("##:##");
			dateMask.install(campoPausaS);
			dateMask.setValidCharacters("0,1,2,3,4,5,6,7,8,9, ");
		}catch(ParseException ex) {
			Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
		}
		campoPausaV = new JFormattedTextField();
		campoPausaV.setValue("00:00");
		try {
			MaskFormatter dateMask = new MaskFormatter("##:##");
			dateMask.install(campoPausaV);
			dateMask.setValidCharacters("0,1,2,3,4,5,6,7,8,9, ");
		}catch(ParseException ex) {
			Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
		}
		botaoCalc = new JButton("Calcular Saída");
		horaSaida = new JLabel("00:00", JLabel.RIGHT);
		horaSaida.setFont(new Font(horaSaida.getFont().getName(), Font.PLAIN, 24));
		sair = new JButton("Sair");
		sair.setVisible(false);
		
		labelTitulo.setBounds(80, 20, 240, 40);
		labelEntrada.setBounds(100, 100, 200, 20);
		campoEntrada.setBounds(100, 120, 200, 40);
		labelAlmocoS.setBounds(100, 180, 200, 20);
		campoAlmocoS.setBounds(100, 200, 200, 40);
		labelAlmocoV.setBounds(100, 260, 200, 20);
		campoAlmocoV.setBounds(100, 280, 200, 40);
		addPausa.setBounds(100, 340, 110, 40);
		labelPausa.setBounds(200, 330, 90, 40);
		textoPausa.setBounds(205, 355, 90, 20);
		campoPausaS.setBounds(100, 380, 100, 40);
		campoPausaV.setBounds(200, 380, 100, 40);
		botaoCalc.setBounds(100, 430, 110, 40);
		horaSaida.setBounds(200, 430, 90, 40);
		sair.setBounds(100, 480, 200, 40);
		
		add(labelTitulo);
		add(labelEntrada);
		add(campoEntrada);
		add(labelAlmocoS);
		add(campoAlmocoS);
		add(labelAlmocoV);
		add(campoAlmocoV);
		add(addPausa);
		add(labelPausa);
		add(textoPausa);
		add(campoPausaS);
		add(campoPausaV);
		add(botaoCalc);
		add(horaSaida);
		add(sair);
		
		setVisible(true);
	}
	
	private void criarEventos() {
		addPausa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pausaExtra();
			}
		});
		
		botaoCalc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calculadHora();
			}
		});
		
		sair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void calculadHora() {
		Intervalo intervalo = new Intervalo();
		intervalo.setEntrada(campoEntrada.getText());
		String almocoS = campoAlmocoS.getText();
		String almocoV = campoAlmocoV.getText();
		Pausa almoco = new Pausa(almocoS, almocoV);
		
		CalculoSaida calculaSaida = new CalculoSaida();
		
		pausas.add(almoco);
		intervalo.setPausas(pausas);
		Intervalo resultado = calculaSaida.calcularSaida(intervalo);
		
		horaSaida.setText(resultado.getSaida());
		addPausa.setEnabled(false);
		
		
		campoEntrada.setEnabled(false);
		campoAlmocoS.setEnabled(false);
		campoAlmocoV.setEnabled(false);
		campoPausaS.setEnabled(false);
		campoPausaV.setEnabled(false);
		botaoCalc.setEnabled(false);
		sair.setVisible(true);
	}
	
	private void pausaExtra() {
		Intervalo intervalo = new Intervalo();
		String pausaS = campoPausaS.getText();
		String pausaV = campoPausaV.getText();
		Pausa descanso = new Pausa(pausaS, pausaV);
		pausas.add(descanso);
		intervalo.setPausas(pausas);
		campoPausaS.setText("00:00");
		campoPausaV.setText("00:00");
		
		
		LocalTime p1 = LocalTime.of(00, 00);
		LocalTime p2 = LocalTime.of(00, 00);
		LocalTime pausaLista = LocalTime.of(00, 00);
		LocalTime pausaTotal = LocalTime.of(00, 00);
		for(int i = 0; i < intervalo.getPausas().size(); i++){
			
			p1 = LocalTime.parse(intervalo.getPausas().get(i).getInicio());
			p2 = LocalTime.parse(intervalo.getPausas().get(i).getFim());
			
			pausaLista = p2.minusHours(p1.getHour()).minusMinutes(p1.getMinute());
			pausaTotal = pausaTotal.plusHours(pausaLista.getHour()).plusMinutes(pausaLista.getMinute()); 
			labelPausa.setText(pausaTotal.toString());
		}
		
	}
	
}
