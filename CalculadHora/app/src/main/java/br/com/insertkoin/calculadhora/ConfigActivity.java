package br.com.insertkoin.calculadhora;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


public class ConfigActivity extends AppCompatActivity {

    private SeekBar seekbar;
    private TextView horaDeCalculo;
    private Switch ativaPausa;
    private Button botaoOK;
    private static final String SETTINGS = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao);

        seekbar = findViewById(R.id.seekBarID);
        horaDeCalculo = findViewById(R.id.horaDeCalculoID);
        ativaPausa = findViewById(R.id.ativaPausaID);

        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
        if(sharedPreferences.contains("SeekbarPosition")) {

            int mProgress = sharedPreferences.getInt("SeekbarPosition", 4);
            String horaSalva = sharedPreferences.getString("HoraDeCalculo", "08:48");
            seekbar.setProgress(mProgress);
            horaDeCalculo.setText(horaSalva);

        }else {
            seekbar.setProgress(4);
            horaDeCalculo.setText("08:48");
        }

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int valorProgresso = progress;

                if(valorProgresso == 0) {
                    horaDeCalculo.setText("05:40");
                }else if(valorProgresso == 1) {
                    horaDeCalculo.setText("06:00");
                }else if(valorProgresso == 2) {
                    horaDeCalculo.setText("07:00");
                }else if(valorProgresso == 3) {
                    horaDeCalculo.setText("08:00");
                }else if(valorProgresso == 4) {
                    horaDeCalculo.setText("08:48");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int posicaoBarra = seekBar.getProgress();
                SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(posicaoBarra == 0) {

                    editor.putInt("SeekbarPosition",posicaoBarra);
                    editor.putString("HoraDeCalculo","05:40");
                    editor.commit();

                }else if(posicaoBarra == 1) {

                    editor.putInt("SeekbarPosition",posicaoBarra);
                    editor.putString("HoraDeCalculo","06:00");
                    editor.commit();

                }else if(posicaoBarra == 2) {

                    editor.putInt("SeekbarPosition",posicaoBarra);
                    editor.putString("HoraDeCalculo","07:00");
                    editor.commit();

                }else if(posicaoBarra == 3) {

                    editor.putInt("SeekbarPosition",posicaoBarra);
                    editor.putString("HoraDeCalculo","08:00");
                    editor.commit();

                }else if(posicaoBarra == 4) {

                    editor.putInt("SeekbarPosition",posicaoBarra);
                    editor.putString("HoraDeCalculo","08:48");
                    editor.commit();

                }

            }
        });

        ativaPausa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("AtivaPausa", ativaPausa.isChecked());
                editor.commit();

            }
        });

        ativaPausa.setChecked(sharedPreferences.getBoolean("AtivaPausa", true));

        botaoOK = findViewById(R.id.botaoOKID);
        botaoOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
