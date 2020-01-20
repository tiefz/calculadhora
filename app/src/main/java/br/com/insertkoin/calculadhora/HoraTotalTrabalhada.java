package br.com.insertkoin.calculadhora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HoraTotalTrabalhada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hora_total_trabalhada);

        TextView hoEntradaTeste = findViewById(R.id.hoEntradaTesteID);
        TextView hoAmlocoSTeste = findViewById(R.id.hoAlmocoSTesteID);
        TextView hoAlmocoRTeste = findViewById(R.id.hoAlmocoRTesteID);
        TextView hoSaidaTeste = findViewById(R.id.hoSaidaTesteID);
        TextView pausaTeste = findViewById(R.id.pausaTesteId);

        Bundle b = getIntent().getExtras();

        hoEntradaTeste.setText(b.getString("Entrada"));
        hoAmlocoSTeste.setText(b.getString("AlmocoS"));
        hoAlmocoRTeste.setText(b.getString("AlmocoR"));
        hoSaidaTeste.setText(b.getString("SaidaHT"));
        if(b.containsKey("PausasExtras")) {
            pausaTeste.setText(b.getString("PausasExtras"));
        }
    }
}
