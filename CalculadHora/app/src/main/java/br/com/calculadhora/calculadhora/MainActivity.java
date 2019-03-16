package br.com.calculadhora.calculadhora;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TimePickerDialog timePickerDialog;
    private EditText horaEntrada;
    private EditText horaSaidaAlmoco;
    private EditText horaRetornoAlmoco;
    private EditText pausaSaida;
    private EditText pausaRetorno;
    private Button botaoPausa;
    private Button botaoCalc;
    private TextView resultadoPausa;
    private TextView resultadoSaida;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;

    List<Pausa> pausas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horaEntrada = findViewById(R.id.horaEntradaID);
        horaSaidaAlmoco = findViewById(R.id.horaSaidaAlmocoID);
        horaRetornoAlmoco = findViewById(R.id.horaRetornoAlmocoID);
        pausaSaida = findViewById(R.id.pausaSaidaID);
        pausaRetorno = findViewById(R.id.pausaRetornoID);
        botaoPausa = findViewById(R.id.botaoPausaID);
        botaoCalc = findViewById(R.id.botaoCalcID);
        resultadoPausa = findViewById(R.id.resultadoPausaID);
        resultadoSaida = findViewById(R.id.resultadoSaidaID);

        botaoPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultadoSaida.setText(horaEntrada.getText().toString());
            }
        });

        horaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horaEntrada.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();
            }
        });

        horaSaidaAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horaSaidaAlmoco.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();
            }
        });

        horaRetornoAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horaRetornoAlmoco.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();
            }
        });

        pausaSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        pausaRetorno.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();
            }
        });

        botaoPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intervalo intervalo = new Intervalo();
                String pausaS = pausaSaida.getText().toString();
                String pausaR = pausaRetorno.getText().toString();
                Pausa descanso = new Pausa(pausaS, pausaR);
                pausas.add(descanso);
                intervalo.setPausas(pausas);

                LocalTime p1;
                LocalTime p2;
                LocalTime pausaLista;
                LocalTime pausaTotal = LocalTime.of(00, 00);
                for(int i = 0; i < intervalo.getPausas().size(); i++){

                    p1 = LocalTime.parse(intervalo.getPausas().get(i).getInicio());
                    p2 = LocalTime.parse(intervalo.getPausas().get(i).getFim());

                    pausaLista = p2.minusHours(p1.getHour()).minusMinutes(p1.getMinute());
                    pausaTotal = pausaTotal.plusHours(pausaLista.getHour()).plusMinutes(pausaLista.getMinute());
                    resultadoPausa.setText(pausaTotal.toString());
                    pausaSaida.setText(R.string.zerohora);
                    pausaRetorno.setText(R.string.zerohora);
                }


            }
        });

        botaoCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intervalo intervalo = new Intervalo();
                intervalo.setEntrada(horaEntrada.getText().toString());
                String almocoS = horaSaidaAlmoco.getText().toString();
                String almocoR = horaRetornoAlmoco.getText().toString();
                Pausa almoco = new Pausa(almocoS, almocoR);

                CalculoSaida calculosaida = new CalculoSaida();

                pausas.add(almoco);
                intervalo.setPausas(pausas);
                Intervalo resultado = calculosaida.calcularSaida(intervalo);

                resultadoSaida.setText(resultado.getSaida());
            }
        });

    }
}
