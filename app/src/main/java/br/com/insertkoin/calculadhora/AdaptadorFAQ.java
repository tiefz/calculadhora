package br.com.insertkoin.calculadhora;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorFAQ extends RecyclerView.Adapter<AdaptadorFAQ.PerguntasViewHolder> {

    private Context mCtx;
    //private List<String> listaPerguntas;
    //private List<String> listaRespostas;
    private List<FAQ> listaFAQ;

    public AdaptadorFAQ(Context mCtx, List<FAQ> listaFAQ) {
        this.mCtx = mCtx;
        //this.listaPerguntas = listaPerguntas;
        //this.listaRespostas = listaRespostas;
        this.listaFAQ = listaFAQ;
    }

    @Override
    public PerguntasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lista_faq, null);
        return new PerguntasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PerguntasViewHolder holder, final int position) {
        //String faq = listaPerguntas.get(position);
        //String resp = listaRespostas.get(position);
        final FAQ faq = listaFAQ.get(position);
        holder.bind(faq);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean aberto = faq.isAberto();
                faq.setAberto(!aberto);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaFAQ.size();
    }

    class PerguntasViewHolder extends RecyclerView.ViewHolder {

        TextView helpTitle, helpAnswer;
        ImageView helpIcon;

        public PerguntasViewHolder(View itemView) {
            super(itemView);

            helpTitle = itemView.findViewById(R.id.helpTitle);
            helpAnswer = itemView.findViewById(R.id.helpAnswer);
            helpIcon = itemView.findViewById(R.id.helpIcon);
        }

        private void bind(FAQ faq) {
            boolean aberto = faq.isAberto();

            helpAnswer.setVisibility(aberto ? View.VISIBLE : View.GONE);

            helpTitle.setText(faq.getPergunta());
            helpAnswer.setText(faq.getResposta());
        }


    }

}
