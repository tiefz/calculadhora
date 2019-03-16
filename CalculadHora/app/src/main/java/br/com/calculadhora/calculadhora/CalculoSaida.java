package br.com.calculadhora.calculadhora;

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
        for(int i = 0; i < intervalo.getPausas().size(); i++){

            p1 = LocalTime.parse(intervalo.getPausas().get(i).getInicio());
            p2 = LocalTime.parse(intervalo.getPausas().get(i).getFim());

            pausaLista = p2.minusHours(p1.getHour()).minusMinutes(p1.getMinute());
            somaEntrada = somaEntrada.plusHours(pausaLista.getHour()).plusMinutes(pausaLista.getMinute());
            pausaLista = LocalTime.of(00, 00);

        }


        intervalo.setSaida(somaEntrada.toString());

        return intervalo;

    }

}
