package br.com.androidpro.bollyfilmes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmesAdapter extends ArrayAdapter<ItemFilme> {

    private static final int VIEW_TYPE_DESTAQUE = 0;

    private static final int VIEW_TYPE_ITEM = 1;

    public FilmesAdapter(Context context, ArrayList<ItemFilme> filmes) {
        super(context, 0, filmes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int viewType = getItemViewType(position);
        ItemFilme filme = getItem(position);
        View itemView = convertView;

        switch (viewType) {
            case VIEW_TYPE_DESTAQUE: {
                itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_filme_destaque, parent, false);

                TextView desc = (TextView) itemView.findViewById(R.id.item_desc);
                desc.setText(filme.getDescricao());

                RatingBar avaliacao = (RatingBar) itemView.findViewById(R.id.item_avaliacao);
                avaliacao.setRating(filme.getAvaliacao());
                break;
            }
            case VIEW_TYPE_ITEM: {
                itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_filme, parent, false);

                TextView titulo = (TextView) itemView.findViewById(R.id.item_titulo);
                titulo.setText(filme.getTitulo());

                TextView desc = (TextView) itemView.findViewById(R.id.item_desc);
                desc.setText(filme.getDescricao());

                TextView dataLancamento = (TextView) itemView.findViewById(R.id.item_data);
                dataLancamento.setText(filme.getDataLancamento());

                RatingBar avaliacao = (RatingBar) itemView.findViewById(R.id.item_avaliacao);
                avaliacao.setRating(filme.getAvaliacao());
                break;
            }
        }

        return itemView;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 ? VIEW_TYPE_DESTAQUE : VIEW_TYPE_ITEM);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
