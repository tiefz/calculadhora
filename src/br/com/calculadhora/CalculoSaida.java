package br.com.calculadhora;

import java.time.LocalTime;

public class CalculoSaida {

	public Intervalo calcularSaida(Intervalo intervalo) {

		LocalTime entrada = LocalTime.parse(intervalo.getEntrada());
		LocalTime horaFixa = LocalTime.of(8, 48);
		LocalTime p1 = LocalTime.of(00, 00);
		LocalTime p2 = LocalTime.of(00, 00);
		LocalTime pausaLista = LocalTime.of(00, 00);

		LocalTime somaEntrada = entrada.plusHours(horaFixa.getHour()).plusMinutes(horaFixa.getMinute());


		//Loop para setar as pausas
		int c = 0;
		for(int i = 0; i < intervalo.getPausas().size(); i++){
			
			p1 = LocalTime.parse(intervalo.getPausas().get(c).getInicio());
			p2 = LocalTime.parse(intervalo.getPausas().get(c).getFim());
			
			pausaLista = p2.minusHours(p1.getHour()).minusMinutes(p1.getMinute());
			System.out.println(pausaLista.toString());
			somaEntrada = somaEntrada.plusHours(pausaLista.getHour()).plusMinutes(pausaLista.getMinute());
			System.out.println(somaEntrada.toString());
			System.out.println(i + " e " + intervalo.getPausas().get(i).getInicio() + " e " + intervalo.getPausas().get(i).getFim());
			pausaLista = LocalTime.of(00, 00);
			
			c++;


		}
		
		
		intervalo.setSaida(somaEntrada.toString());

		return intervalo;

	}

}