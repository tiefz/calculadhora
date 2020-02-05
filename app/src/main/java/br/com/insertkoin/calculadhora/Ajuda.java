package br.com.insertkoin.calculadhora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ajuda extends AppCompatActivity {

    List<String> listaPerguntas;
    List<String> listaRespostas;
    List<FAQ> listaObjetoFAQ;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        getSupportActionBar().setTitle(R.string.ajuda);
        listaObjetoFAQ = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewFAQ);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaPerguntas = new ArrayList<>();
        listaRespostas = new ArrayList<>();
        listaPerguntas = Arrays.asList(getResources().getStringArray(R.array.faq));
        listaRespostas = Arrays.asList(getResources().getStringArray(R.array.resp));

        for(int i = 0; i < listaPerguntas.size(); i++) {
            listaObjetoFAQ.add(new FAQ(listaPerguntas.get(i),listaRespostas.get(i)));
        }

        AdaptadorFAQ adapter = new AdaptadorFAQ(this, listaObjetoFAQ);
        recyclerView.setAdapter(adapter);
    }
}
