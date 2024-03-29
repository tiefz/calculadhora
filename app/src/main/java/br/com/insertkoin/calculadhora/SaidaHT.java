package br.com.insertkoin.calculadhora;


import android.app.TimePickerDialog;
import android.content.Intent;
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
import java.util.Objects;


public class SaidaHT extends Fragment {

    private EditText horaSaidaHT;
    private TextView atencao;
    private ImageView imageViewSaidaHT;
    private static final String SETTINGS = "Settings";
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private LocalTime validaHoraAnterior;
    private LocalTime validaHoraAtual;
    private Button botaoCalcHT;
    String ativaBTCalc;


    public SaidaHT() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saida_ht, container, false);

        imageViewSaidaHT = view.findViewById(R.id.imageViewSaidaHTID);
        botaoCalcHT = view.findViewById(R.id.botaoCalcHTID);
        botaoCalcHT.setVisibility(View.INVISIBLE);

        horaSaidaHT = view.findViewById(R.id.horaSaidaHTID);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
        if(sharedPreferences.contains("SaidaHT")){
            horaSaidaHT.setText(sharedPreferences.getString("SaidaHT", "00:00"));
            ativaBTCalc = sharedPreferences.getString("SaidaHT", "00:00");
        }

        if(ativaBTCalc != null) {
            botaoCalcHT.setVisibility(View.VISIBLE);
            imageViewSaidaHT.setVisibility(View.INVISIBLE);
        }

        atencao = view.findViewById(R.id.atencaoID2);
        atencao.setVisibility(View.INVISIBLE);

        horaSaidaHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if(sharedPreferences.contains("AlmocoR")){
                            horaSaidaHT.setText(String.format("%02d:%02d", hourOfDay, minute));
                            validaHoraAnterior = LocalTime.parse(sharedPreferences.getString("AlmocoR", "00:00"));
                            validaHoraAtual = LocalTime.parse(horaSaidaHT.getText().toString());
                            if(validaHoraAnterior.isAfter(validaHoraAtual)){
                                atencao.setVisibility(View.VISIBLE);
                                horaSaidaHT.setTextColor(Color.RED);
                                Toast.makeText(getActivity(),"A hora da saída não pode ser antes do intervalo de almoço!",Toast.LENGTH_LONG).show();
                            }else{
                                editor.putString("SaidaHT",String.format("%02d:%02d", hourOfDay, minute));
                                editor.commit();
                                atencao.setVisibility(View.INVISIBLE);
                                horaSaidaHT.setTextColor(Color.parseColor("#FFF5F5F5"));
                                botaoCalcHT.setVisibility(View.VISIBLE);
                                imageViewSaidaHT.setVisibility(View.INVISIBLE);
                            }
                        }else {
                            Toast.makeText(getActivity(),"Você precisa definir um horário de almoço antes!",Toast.LENGTH_LONG).show();
                            ((TabActivity)getActivity()).setCurrentItem(2, true);
                        }
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });


        imageViewSaidaHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Escolha a hora em que você encerrou o expediente",Toast.LENGTH_SHORT).show();
            }
        });

        botaoCalcHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                Intent intent = new Intent(getActivity(), HoraTotalTrabalhada.class);
                intent.putExtra("Entrada", sharedPreferences.getString("Entrada", "00:00"));
                intent.putExtra("AlmocoS", sharedPreferences.getString("AlmocoS", "00:00"));
                intent.putExtra("AlmocoR", sharedPreferences.getString("AlmocoR", "00:00"));
                intent.putExtra("SaidaHT", sharedPreferences.getString("SaidaHT", "00:00"));
                if(sharedPreferences.contains("PausasExtras")){
                    intent.putExtra("PausasExtras", sharedPreferences.getString("PausasExtras", "00:00"));
                }
                startActivity(intent);
                botaoCalcHT.setVisibility(View.INVISIBLE);
                imageViewSaidaHT.setVisibility(View.VISIBLE);
                getActivity().finish();
            }
        });

        return view;
    }

}
