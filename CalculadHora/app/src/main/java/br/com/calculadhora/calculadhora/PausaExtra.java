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
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.gson.Gson;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PausaExtra extends Fragment {

    private EditText pausaSaida;
    private EditText pausaRetorno;
    private TextView maisPausas;
    private ImageView coffe;
    private ImageView maisPausasImg;
    private ImageView limparPausas;
    private Button botaoAddPausa;
    private Button proximo;
    private Button voltar;
    private static final String SETTINGS = "Settings";
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    ArrayList<Pausa> listaPausas = new ArrayList<>();


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
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });

        botaoAddPausa = view.findViewById(R.id.botaoAddPausaID);
        botaoAddPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String pausaS = pausaSaida.getText().toString();
                String pausaR = pausaRetorno.getText().toString();
                Pausa descanso = new Pausa(pausaS, pausaR);
                listaPausas.add(descanso);
                Gson gson = new Gson();
                String json = gson.toJson(listaPausas);
                editor.putString("PausasExtras",json);
                editor.commit();
                botaoAddPausa.setEnabled(false);
                botaoAddPausa.setText("Pausa adicionada");
                Toast.makeText(getActivity(),"Pausa adicionada ;)",Toast.LENGTH_SHORT).show();
                maisPausasImg.setVisibility(View.VISIBLE);
                maisPausas.setVisibility(View.VISIBLE);
                limparPausas.setVisibility(View.VISIBLE);

            }
        });

        maisPausasImg = view.findViewById(R.id.maisPausasImgID);
        maisPausasImg.setVisibility(View.INVISIBLE);
        maisPausasImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausaSaida.setText(R.string.zerohora);
                pausaRetorno.setText(R.string.zerohora);
                botaoAddPausa.setEnabled(true);
                botaoAddPausa.setText(R.string.bt1);
                maisPausasImg.setVisibility(View.INVISIBLE);
                maisPausas.setVisibility(View.INVISIBLE);
                limparPausas.setVisibility(View.INVISIBLE);
            }
        });

        limparPausas = view.findViewById(R.id.limparPausasID);
        limparPausas.setVisibility(View.INVISIBLE);
        limparPausas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                listaPausas.clear();
                editor.remove("PausasExtras");
                editor.commit();
                botaoAddPausa.setEnabled(true);
                botaoAddPausa.setText(R.string.bt1);
                limparPausas.setVisibility(View.INVISIBLE);
                maisPausasImg.setVisibility(View.INVISIBLE);
                maisPausas.setVisibility(View.INVISIBLE);
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

        proximo = view.findViewById(R.id.proximoID4);
        //proximo.setVisibility(View.INVISIBLE);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabActivity)getActivity()).setCurrentItem(4, true);

            }
        });

        voltar = view.findViewById(R.id.voltarID3);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabActivity)getActivity()).setCurrentItem(2, true);

            }
        });

        return view;
    }

}
