package br.com.calculadhora.calculadhora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText horaEntrada;
    private Button botaoPausa;
    private Button botaoCalc;
    private TextView resultadoSaida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horaEntrada = findViewById(R.id.horaEntradaID);
        botaoPausa = (Button) findViewById(R.id.botaoPausaID);
        botaoCalc = (Button) findViewById(R.id.botaoCalcID);
        resultadoSaida = (TextView) findViewById(R.id.resultadoSaidaID);

        botaoPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultadoSaida.setText(horaEntrada.getText().toString());
            }
        });

        botaoCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultadoSaida.setText("20:20");
            }
        });

    }
}
