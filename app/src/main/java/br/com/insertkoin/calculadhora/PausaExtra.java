package br.com.insertkoin.calculadhora;


import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.LocalTime;

import java.util.Calendar;


public class PausaExtra extends Fragment {

    private EditText pausaRetorno, pausaSaida;
    private TextView maisPausas, textTotalPausa, totalPausaDisplay;
    private ImageView limparPausas, coffe;
    private Button proximo, botaoAddPausa;
    private static final String SETTINGS = "Settings";
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour, currentMinute;
    private LocalTime validaHoraAnterior, validaHoraAtual;


    public PausaExtra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pausa_extra, container, false);

        maisPausas = view.findViewById(R.id.maisPausasID);
        maisPausas.setVisibility(View.INVISIBLE);
        textTotalPausa = view.findViewById(R.id.textTotalPausaID);
        textTotalPausa.setVisibility(View.INVISIBLE);
        totalPausaDisplay = view.findViewById(R.id.totalPausaID);
        totalPausaDisplay.setVisibility(View.INVISIBLE);
        proximo = view.findViewById(R.id.proximoID4);
        proximo.setVisibility(View.INVISIBLE);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabActivity)getActivity()).setCurrentItem(4, true);

            }
        });
        pausaSaida = view.findViewById(R.id.pausaSaidaID);
        pausaRetorno = view.findViewById(R.id.pausaRetornoID);
        pausaSaida.setText(R.string.zerohora);
        pausaRetorno.setText(R.string.zerohora);
        pausaSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        pausaSaida.setText(String.format("%02d:%02d", hourOfDay, minute));
                        validaHoraAnterior = LocalTime.parse(pausaSaida.getText().toString());

                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });

        pausaRetorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        pausaRetorno.setText(String.format("%02d:%02d", hourOfDay, minute));
                        validaHoraAtual = LocalTime.parse(pausaRetorno.getText().toString());
                        if(validaHoraAnterior.isAfter(validaHoraAtual)){
                            pausaRetorno.setTextColor(Color.RED);
                            Toast.makeText(getActivity(),"A volta da pausa não pode ser menor que a saída",Toast.LENGTH_LONG).show();
                        }else {
                            pausaRetorno.setTextColor(Color.parseColor("#FFF5F5F5"));
                            textTotalPausa.setVisibility(View.INVISIBLE);
                            totalPausaDisplay.setVisibility(View.INVISIBLE);
                            botaoAddPausa.setVisibility(View.VISIBLE);
                        }
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });

        botaoAddPausa = view.findViewById(R.id.botaoAddPausaID);
        botaoAddPausa.setVisibility(View.INVISIBLE);
        botaoAddPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalTime pausaExtra = LocalTime.parse("00:00");
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String pausaS = pausaSaida.getText().toString();
                String pausaR = pausaRetorno.getText().toString();
                LocalTime p1 = LocalTime.parse(pausaS);
                LocalTime p2 = LocalTime.parse(pausaR);
                LocalTime calculoDePausa = p2.minusHours(p1.getHourOfDay()).minusMinutes(p1.getMinuteOfHour());

                if(sharedPreferences.contains("PausasExtras")){
                    pausaExtra = LocalTime.parse(sharedPreferences.getString("PausasExtras", "00:00"));
                    calculoDePausa = calculoDePausa.plusHours(pausaExtra.getHourOfDay()).plusMinutes(pausaExtra.getMinuteOfHour());
                }

                editor.putString("PausasExtras",calculoDePausa.toString("HH:mm"));
                editor.commit();
                botaoAddPausa.setText("Pausa adicionada");
                Toast.makeText(getActivity(),"Pausa adicionada ;)",Toast.LENGTH_SHORT).show();
                pausaSaida.setText(R.string.zerohora);
                pausaRetorno.setText(R.string.zerohora);
                maisPausas.setVisibility(View.VISIBLE);
                limparPausas.setVisibility(View.VISIBLE);
                botaoAddPausa.setVisibility(View.INVISIBLE);
                textTotalPausa.setVisibility(View.VISIBLE);
                totalPausaDisplay.setVisibility(View.VISIBLE);
                totalPausaDisplay.setText(calculoDePausa.toString("HH:mm"));
                proximo.setVisibility(View.VISIBLE);

            }
        });


        limparPausas = view.findViewById(R.id.limparPausasID);
        limparPausas.setVisibility(View.INVISIBLE);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
        if(sharedPreferences.contains("PausasExtras")){
            limparPausas.setVisibility(View.VISIBLE);
            maisPausas.setVisibility(View.VISIBLE);
            textTotalPausa.setVisibility(View.VISIBLE);
            totalPausaDisplay.setVisibility(View.VISIBLE);
            totalPausaDisplay.setText(sharedPreferences.getString("PausasExtras", "00:00"));
            proximo.setVisibility(View.VISIBLE);
        }
        limparPausas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("PausasExtras");
                editor.commit();
                botaoAddPausa.setEnabled(true);
                botaoAddPausa.setText(R.string.bt1);
                pausaSaida.setText(R.string.zerohora);
                pausaRetorno.setText(R.string.zerohora);
                limparPausas.setVisibility(View.INVISIBLE);
                maisPausas.setVisibility(View.INVISIBLE);
                textTotalPausa.setVisibility(View.INVISIBLE);
                totalPausaDisplay.setVisibility(View.INVISIBLE);
                proximo.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(),"Todas as pausas foram excluídas!",Toast.LENGTH_SHORT).show();
            }
        });

        coffe = view.findViewById(R.id.coffeID);
        coffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Você pode adicionar quantas pausas quiser, clicando no sinal de + pausas",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
