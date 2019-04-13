package br.com.insertkoin.calculadhora;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.LocalTime;

import java.lang.reflect.Type;
//import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaidaCalculada extends Fragment {

    private TextView resultadoSaida;
    private Button botaoCalc;
    private Button botaoLimpar;
    private static final String SETTINGS = "Settings";
    ArrayList<Pausa> listaPausas = new ArrayList<>();



    public SaidaCalculada() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saida, container, false);

        resultadoSaida = view.findViewById(R.id.resultadoSaidaID);
        botaoCalc = view.findViewById(R.id.botaoCalcID);
        botaoCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intervalo intervalo = new Intervalo();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                intervalo.setEntrada(sharedPreferences.getString("Entrada", "00:00"));
                String almocoS = sharedPreferences.getString("AlmocoS", "00:00");
                String almocoR = sharedPreferences.getString("AlmocoR", "00:00");
                Pausa almoco = new Pausa(almocoS, almocoR);

                if(sharedPreferences.contains("PausasExtras")){
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("PausasExtras", null);
                    Type type = new TypeToken<ArrayList<Pausa>>() {}.getType();
                    listaPausas = gson.fromJson(json, type);
                }

                listaPausas.add(almoco);
                intervalo.setPausas(listaPausas);
                Intervalo resultado = calcularSaida(intervalo);

                resultadoSaida.setText(resultado.getSaida());
                editor.putString("UltimaHora",resultado.getSaida());
                editor.commit();
                botaoLimpar.setVisibility(View.VISIBLE);
                botaoCalc.setVisibility(View.INVISIBLE);
            }
        });

        botaoLimpar = view.findViewById(R.id.botaoLimparID);
        botaoLimpar.setVisibility(View.INVISIBLE);
        botaoLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                listaPausas.clear();
                editor.remove("PausasExtras");
                editor.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private Intervalo calcularSaida(Intervalo intervalo) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
        String HoraDeCalculo = "08:48";
        LocalTime horaFixa;
        horaFixa = LocalTime.parse(HoraDeCalculo);

        if(sharedPreferences.contains("HoraDeCalculo")){
            HoraDeCalculo = sharedPreferences.getString("HoraDeCalculo", "08:48");
            horaFixa = LocalTime.parse(HoraDeCalculo);
        }

        LocalTime entrada = LocalTime.parse(intervalo.getEntrada());
        LocalTime p1 = LocalTime.parse("00:00");
        LocalTime p2 = LocalTime.parse("00:00");
        LocalTime pausaLista = LocalTime.parse("00:00");

        LocalTime somaEntrada = entrada.plusHours(horaFixa.getHourOfDay()).plusMinutes(horaFixa.getMinuteOfHour());


        //Loop para setar as pausas
        for(int i = 0; i < intervalo.getPausas().size(); i++){

            p1 = LocalTime.parse(intervalo.getPausas().get(i).getInicio());
            p2 = LocalTime.parse(intervalo.getPausas().get(i).getFim());

            pausaLista = p2.minusHours(p1.getHourOfDay()).minusMinutes(p1.getMinuteOfHour());
            somaEntrada = somaEntrada.plusHours(pausaLista.getHourOfDay()).plusMinutes(pausaLista.getMinuteOfHour());
            pausaLista = LocalTime.parse("00:00");

        }


        intervalo.setSaida(somaEntrada.toString("HH:mm"));

        return intervalo;

    }

}
