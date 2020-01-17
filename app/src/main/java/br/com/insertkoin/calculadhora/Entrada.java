package br.com.insertkoin.calculadhora;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;


public class Entrada extends Fragment {

    private EditText horaEntrada;
    private ImageView imageViewEntrada, slideImage;
    private static final String SETTINGS = "Settings";
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour, currentMinute;
    Long duracaoAnimacao = 2000L;


    public Entrada() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entrada, container, false);

        slideImage = view.findViewById(R.id.slidingID);
        slideImage.setVisibility(View.INVISIBLE);
        horaEntrada = view.findViewById(R.id.horaEntradaID);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
        if(sharedPreferences.contains("Entrada")){
            horaEntrada.setText(sharedPreferences.getString("Entrada", "00:00"));
        }

        horaEntrada.setOnClickListener(new View.OnClickListener() {
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
                        horaEntrada.setText(String.format("%02d:%02d", hourOfDay, minute));
                        editor.putString("Entrada",String.format("%02d:%02d", hourOfDay, minute));
                        editor.commit();
                        //((TabActivity)getActivity()).setCurrentItem(1, true);
                        slideImage.setVisibility(View.VISIBLE);
                        animar();
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });

        imageViewEntrada = view.findViewById(R.id.imageViewEntradaID);
        imageViewEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Escolha a hora em que você entrou no trabalho",Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    public void animar() {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(slideImage, "alpha",1);
        ObjectAnimator animacao = ObjectAnimator.ofFloat(slideImage, "rotation", 0f, -30f, 0f, 30f, 0f, -30f, 0f, 30f);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(slideImage, "alpha",0);
        fadeIn.setDuration(0);
        animacao.setDuration(duracaoAnimacao);
        fadeOut.setDuration(duracaoAnimacao);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fadeIn,animacao,fadeOut);
        animatorSet.start();
    }

}
