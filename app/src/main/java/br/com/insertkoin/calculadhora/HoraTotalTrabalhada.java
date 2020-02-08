package br.com.insertkoin.calculadhora;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class HoraTotalTrabalhada extends AppCompatActivity {

    private static final String HORA_DE_CALCULO = "08:48";
    private static final String SETTINGS = "Settings";
    private String entradaSalva, almocoSSalvo, almocoRSalvo, pausaSalva, saidaSalva;
    private TextView textoBHResultado, bancoHorasResultado, horasTrabalhadasResultado, horasPrevistas;
    private Button botaoConcluir;
    private ImageView smiley;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hora_total_trabalhada);


        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String HoraDeCalculo = HORA_DE_CALCULO;
        horasPrevistas = findViewById(R.id.horasPrevistasID);
        horasPrevistas.setText(HORA_DE_CALCULO);
        smiley = findViewById(R.id.smileyID);
        smiley.setImageDrawable( ContextCompat.getDrawable(this, R.drawable.happy ) );
        if(sharedPreferences.contains("HoraDeCalculo")){
            HoraDeCalculo = sharedPreferences.getString("HoraDeCalculo", "08:48");
            horasPrevistas.setText(HoraDeCalculo);
        }
        Bundle b = getIntent().getExtras();

        if(b != null) {
            entradaSalva = b.getString("Entrada");
            almocoSSalvo = b.getString("AlmocoS");
            almocoRSalvo = b.getString("AlmocoR");
            saidaSalva = b.getString("SaidaHT");

            LocalTime horaEntradaHT = LocalTime.parse(entradaSalva);
            LocalTime horaAlmocoSHT = LocalTime.parse(almocoSSalvo);
            LocalTime horaAlmocoRHT = LocalTime.parse(almocoRSalvo);
            LocalTime horaSaidaHT = LocalTime.parse(saidaSalva);
            LocalTime horarioDeTrabalho = LocalTime.parse(HoraDeCalculo);
            LocalTime calculoPausaHT;
            LocalTime calculoDiaHT;
            LocalTime calculoDescontaPausa;
            Period horaExtra;

            calculoPausaHT = horaAlmocoRHT.minusHours(horaAlmocoSHT.getHourOfDay()).minusMinutes(horaAlmocoSHT.getMinuteOfHour());
            if(b.containsKey("PausasExtras")) {
                pausaSalva = b.getString("PausasExtras");
                LocalTime pausaExtraHT = LocalTime.parse(pausaSalva);
                calculoPausaHT = calculoPausaHT.plusHours(pausaExtraHT.getHourOfDay()).plusMinutes(pausaExtraHT.getMinuteOfHour());
            }
            calculoDescontaPausa = horaSaidaHT.minusHours(calculoPausaHT.getHourOfDay()).minusMinutes(calculoPausaHT.getMinuteOfHour());
            calculoDiaHT = calculoDescontaPausa.minusHours(horaEntradaHT.getHourOfDay()).minusMinutes(horaEntradaHT.getMinuteOfHour());

            PeriodFormatter fmt = new PeriodFormatterBuilder()
                    .appendDays().appendSuffix("d")
                    .printZeroAlways().minimumPrintedDigits(2)
                    .appendHours().appendSuffix(":")
                    .printZeroAlways().minimumPrintedDigits(2)
                    .appendMinutes().toFormatter();

            horaExtra = new Period(horarioDeTrabalho, calculoDiaHT, PeriodType.dayTime());
            Duration dur = horaExtra.toStandardDuration();

            textoBHResultado = findViewById(R.id.textoBHResultadoID);

            if(dur.getStandardMinutes() < 0) {
                textoBHResultado.setText(R.string.bh_negativo);
                textoBHResultado.setBackgroundResource(R.color.vermelho);
                smiley.setImageDrawable( ContextCompat.getDrawable(this, R.drawable.sad ) );
            }else if(dur.getStandardMinutes() == 0){
                textoBHResultado.setText(R.string.bh_zerado);
                textoBHResultado.setBackgroundResource(R.color.bg_cinza);
            }else {
                textoBHResultado.setText(R.string.bh_positivo);
                textoBHResultado.setBackgroundResource(R.color.bg_green);
            }

            bancoHorasResultado = findViewById(R.id.bancoHorasResultadoID);
            bancoHorasResultado.setText(durationToHHHmmFormat(dur, ":"));
            horasTrabalhadasResultado = findViewById(R.id.horasTrabalhadasResultadoID);
            horasTrabalhadasResultado.setText(calculoDiaHT.toString("HH:mm"));

            editor.remove("Entrada");
            editor.remove("AlmocoS");
            editor.remove("AlmocoR");
            editor.remove("PausasExtras");
            editor.remove("SaidaHT");
            editor.commit();

            botaoConcluir = findViewById(R.id.botaoConcluirID);
            botaoConcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HoraTotalTrabalhada.this, MainActivity.class));
                    finish();
                }
            });

        } else {
            startActivity(new Intent(HoraTotalTrabalhada.this, MainActivity.class));
            Log.i("Erro", "Tentou calcular sem bundle intent");
        }

        botaoConcluir = findViewById(R.id.botaoConcluirID);

    }

    public static String durationToHHHmmFormat(Duration duration, String separator){
        if ( duration == null ) {
            return null;
        }

        Duration dToFormat = duration;
        if (duration.getStandardMinutes() < 0) {
            dToFormat = duration.minus(duration).minus(duration);
        }

        Period p = dToFormat.toPeriod();
        PeriodFormatter pf = new PeriodFormatterBuilder()
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendHours()
                .appendSeparator(separator)
                .appendMinutes()
                .toFormatter();

        if (duration.getStandardMinutes() < 0) {
            return "-"+pf.print(p);
        }
        return pf.print(p);
    }
}
