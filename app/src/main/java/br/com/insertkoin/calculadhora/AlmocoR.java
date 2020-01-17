package br.com.insertkoin.calculadhora;


import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.LocalTime;

import java.util.Calendar;


public class AlmocoR extends Fragment {

    private EditText horaRetornoAlmoco;
    private TextView atencao;
    private ImageView imageViewRetornoAlmoco;
    private static final String SETTINGS = "Settings";
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private LocalTime validaHoraAnterior;
    private LocalTime validaHoraAtual;


    public AlmocoR() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_almoco_r, container, false);

        horaRetornoAlmoco = view.findViewById(R.id.horaRetornoAlmocoID);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
        if(sharedPreferences.contains("AlmocoR")){
            horaRetornoAlmoco.setText(sharedPreferences.getString("AlmocoR", "00:00"));
        }

        atencao = view.findViewById(R.id.atencaoID2);
        atencao.setVisibility(View.INVISIBLE);

        horaRetornoAlmoco.setOnClickListener(new View.OnClickListener() {
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
                        if(sharedPreferences.contains("AlmocoS")){
                            horaRetornoAlmoco.setText(String.format("%02d:%02d", hourOfDay, minute));
                            validaHoraAnterior = LocalTime.parse(sharedPreferences.getString("AlmocoS", "00:00"));
                            validaHoraAtual = LocalTime.parse(horaRetornoAlmoco.getText().toString());
                            if(validaHoraAnterior.isAfter(validaHoraAtual)){
                                atencao.setVisibility(View.VISIBLE);
                                horaRetornoAlmoco.setTextColor(Color.RED);
                                Toast.makeText(getActivity(),"A hora da volta do almoço não pode ser antes da hora da saída!",Toast.LENGTH_LONG).show();
                            }else{
                                editor.putString("AlmocoR",String.format("%02d:%02d", hourOfDay, minute));
                                editor.commit();
                                atencao.setVisibility(View.INVISIBLE);
                                horaRetornoAlmoco.setTextColor(Color.parseColor("#FFF5F5F5"));
                                //((TabActivity)getActivity()).setCurrentItem(3, true);
                            }
                        }else {
                            Toast.makeText(getActivity(),"Você precisa definir um horário de almoço antes!",Toast.LENGTH_LONG).show();
                            //((TabActivity)getActivity()).setCurrentItem(1, true);
                        }
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });

        imageViewRetornoAlmoco = view.findViewById(R.id.imageViewRetornoAlmocoID);
        imageViewRetornoAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Escolha a hora em que você voltou do almoço",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
