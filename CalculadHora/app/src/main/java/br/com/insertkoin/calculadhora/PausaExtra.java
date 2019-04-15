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
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.gson.Gson;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class PausaExtra extends Fragment {

    private EditText pausaSaida;
    private EditText pausaRetorno;
    private TextView maisPausas;
    private ImageView coffe;
    private ImageView limparPausas;
    private Button botaoAddPausa;
    private Button proximo;
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
                        botaoAddPausa.setVisibility(View.VISIBLE);
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
                botaoAddPausa.setText("Pausa adicionada");
                Toast.makeText(getActivity(),"Pausa adicionada ;)",Toast.LENGTH_SHORT).show();
                pausaSaida.setText(R.string.zerohora);
                pausaRetorno.setText(R.string.zerohora);
                maisPausas.setVisibility(View.VISIBLE);
                limparPausas.setVisibility(View.VISIBLE);
                botaoAddPausa.setVisibility(View.INVISIBLE);

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
                pausaSaida.setText(R.string.zerohora);
                pausaRetorno.setText(R.string.zerohora);
                limparPausas.setVisibility(View.INVISIBLE);
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


        return view;
    }

}
