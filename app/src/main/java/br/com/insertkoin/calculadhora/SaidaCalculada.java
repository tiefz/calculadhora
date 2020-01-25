package br.com.insertkoin.calculadhora;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalTime;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaidaCalculada extends Fragment {

    private TextView resultadoSaida;
    private TextView resumoEntrada;
    private TextView resumoAlmocoS;
    private TextView resumoAlmocoR;
    private Button botaoCalc;
    private ImageButton alarme;
    private static final String SETTINGS = "Settings";
    private static final String HORA_DE_CALCULO = "08:48";
    private AlertDialog.Builder dialog;
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
        resumoEntrada = view.findViewById(R.id.resumoEntrada);
        resumoAlmocoS = view.findViewById(R.id.resumoAlmocoS);
        resumoAlmocoR = view.findViewById(R.id.resumoAlmocoR);

        botaoCalc = view.findViewById(R.id.botaoCalcID);
        botaoCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intervalo intervalo = new Intervalo();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(!sharedPreferences.contains("AlmocoR")) {
                    Toast.makeText(getActivity(),"Você precisa definir os horários antes de calcular a saída!",Toast.LENGTH_LONG).show();
                    ((TabActivity)getActivity()).setCurrentItem(0, true);
                }else{
                    intervalo.setEntrada(sharedPreferences.getString("Entrada", "00:00"));
                    String almocoS = sharedPreferences.getString("AlmocoS", "00:00");
                    String almocoR = sharedPreferences.getString("AlmocoR", "00:00");
                    if(sharedPreferences.contains("PausasExtras")){

                        String pausaExtra = sharedPreferences.getString("PausasExtras", "00:00");
                        LocalTime p1 = LocalTime.parse(pausaExtra);
                        LocalTime p2 = LocalTime.parse(almocoR);
                        LocalTime somaPausaExtra = p1.plusHours(p2.getHourOfDay()).plusMinutes(p2.getMinuteOfHour());
                        almocoR = somaPausaExtra.toString("HH:mm");

                    }
                    Pausa almoco = new Pausa(almocoS, almocoR);

                    listaPausas.add(almoco);
                    intervalo.setPausas(listaPausas);
                    Intervalo resultado = calcularSaida(intervalo);

                    resultadoSaida.setText(resultado.getSaida());
                    editor.putString("UltimaHora",resultado.getSaida());
                    editor.commit();
                    alarme.setVisibility(View.VISIBLE);
                    botaoCalc.setVisibility(View.INVISIBLE);
                    listaPausas.clear();
                    editor.remove("Entrada");
                    editor.remove("AlmocoS");
                    editor.remove("AlmocoR");
                    editor.remove("PausasExtras");
                    editor.remove("SaidaHT");
                    editor.commit();
                }


            }
        });

        alarme = view.findViewById(R.id.alarmeID);
        alarme.setVisibility(View.INVISIBLE);
        alarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Ajustar alarme");
                dialog.setMessage("Criar um alarme com o horário de saída?");
                dialog.setCancelable(false);
                dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                        String horaAlarmeString = sharedPreferences.getString("UltimaHora", "00:00");
                        String[] parts = horaAlarmeString.split(":");
                        int partHora = Integer.parseInt(parts[0]);
                        int partMinuto = Integer.parseInt(parts[1]);


                        Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                        openNewAlarm.putExtra(AlarmClock.EXTRA_HOUR, partHora);
                        openNewAlarm.putExtra(AlarmClock.EXTRA_MINUTES, partMinuto);
                        startActivity(openNewAlarm);
                    }
                });
                dialog.create();
                dialog.show();
            }
        });

        return view;
    }

    private Intervalo calcularSaida(Intervalo intervalo) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
        String HoraDeCalculo = HORA_DE_CALCULO;
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
            resumoEntrada.setText(sharedPreferences.getString("Entrada", "00:00"));
            resumoAlmocoS.setText(sharedPreferences.getString("AlmocoS", "00:00"));
            resumoAlmocoR.setText(sharedPreferences.getString("AlmocoR", "00:00"));
        }
    }


}
