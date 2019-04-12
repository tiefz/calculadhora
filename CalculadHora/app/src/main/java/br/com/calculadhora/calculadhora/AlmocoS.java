package br.com.calculadhora.calculadhora;


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
public class AlmocoS extends Fragment {

    private EditText horaSaidaAlmoco;
    private ImageView imageViewSaidaAlmoco;
    private Button proximo;
    private Button voltar;
    private static final String SETTINGS = "Settings";
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;

    public AlmocoS() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_almoco, container, false);

        horaSaidaAlmoco = view.findViewById(R.id.horaRetornoAlmocoID);

        horaSaidaAlmoco.setOnClickListener(new View.OnClickListener() {
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
                        horaSaidaAlmoco.setText(String.format("%02d:%02d", hourOfDay, minute));
                        editor.putString("AlmocoS",String.format("%02d:%02d", hourOfDay, minute));
                        editor.commit();
                        //proximo.setVisibility(View.VISIBLE);
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });

        imageViewSaidaAlmoco = view.findViewById(R.id.imageViewSaidaAlmocoID);
        imageViewSaidaAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Escolha a hora em que você saiu para almoçar",Toast.LENGTH_SHORT).show();
            }
        });

        proximo = view.findViewById(R.id.proximoID2);
        //proximo.setVisibility(View.INVISIBLE);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabActivity)getActivity()).setCurrentItem(2, true);

            }
        });

        voltar = view.findViewById(R.id.voltarID);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabActivity)getActivity()).setCurrentItem(0, true);

            }
        });

        return view;
    }

}
