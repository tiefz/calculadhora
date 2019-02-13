package br.com.calculadhora;

import java.time.LocalTime;

public class CalculoSaida {

	public Intervalo calcularSaida(Intervalo intervalo) {

		LocalTime entrada = LocalTime.parse(intervalo.getEntrada());
		LocalTime saidaAlmoco = LocalTime.parse(intervalo.getPausas().get(0).getInicio());
		LocalTime entradaAlmoco = LocalTime.parse(intervalo.getPausas().get(0).getFim());
		LocalTime p1 = LocalTime.parse(intervalo.getPausas().get(1).getInicio());
		LocalTime p2 = LocalTime.parse(intervalo.getPausas().get(1).getFim());
		
		System.out.println(intervalo.getPausas().get(0).getInicio());
		System.out.println(intervalo.getPausas().get(1).getInicio());
		System.out.println(intervalo.getPausas().get(2).getInicio());
		System.out.println(intervalo.getPausas().get(3).getInicio());

		
		LocalTime horaFixa = LocalTime.of(8, 48);

		// Calculo das horas de entrada, saída e pausas

		LocalTime somaEntrada = entrada.plusHours(horaFixa.getHour()).plusMinutes(horaFixa.getMinute());

		LocalTime pausaAlmoco = entradaAlmoco.minusHours(saidaAlmoco.getHour()).minusMinutes(saidaAlmoco.getMinute());

		LocalTime pausaP1 = p2.minusHours(p1.getHour()).minusMinutes(p1.getMinute());

		LocalTime somaAlmoco = somaEntrada.plusHours(pausaAlmoco.getHour()).plusMinutes(pausaAlmoco.getMinute());

		LocalTime resultHoraFinal = somaAlmoco.plusHours(pausaP1.getHour()).plusMinutes(pausaP1.getMinute());
		
		intervalo.setSaida(resultHoraFinal.toString());

		return intervalo;

	}

}