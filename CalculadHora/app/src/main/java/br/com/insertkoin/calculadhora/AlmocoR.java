package br.com.insertkoin.calculadhora;


import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlmocoR extends Fragment {

    private EditText horaRetornoAlmoco;
    private ImageView imageViewRetornoAlmoco;
    private Button proximo;
    private Button voltar;
    private static final String SETTINGS = "Settings";
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;


    public AlmocoR() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_almoco_r, container, false);

        horaRetornoAlmoco = view.findViewById(R.id.horaRetornoAlmocoID);

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
                        horaRetornoAlmoco.setText(String.format("%02d:%02d", hourOfDay, minute));
                        editor.putString("AlmocoR",String.format("%02d:%02d", hourOfDay, minute));
                        editor.commit();
                        //proximo.setVisibility(View.VISIBLE);
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

        proximo = view.findViewById(R.id.proximoID3);
        //proximo.setVisibility(View.INVISIBLE);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabActivity)getActivity()).setCurrentItem(3, true);

            }
        });

        voltar = view.findViewById(R.id.voltarID2);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabActivity)getActivity()).setCurrentItem(1, true);

            }
        });

        return view;
    }

}
