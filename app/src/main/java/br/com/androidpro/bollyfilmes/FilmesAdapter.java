package br.com.androidpro.bollyfilmes;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import br.com.androidpro.bollyfilmes.data.FilmesContract;

public class FilmesAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_DESTAQUE = 0;

    private static final int VIEW_TYPE_ITEM = 1;

    private boolean useFilmeDestaque = false;

    public FilmesAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public static class ItemFilmeHolder {
        TextView titulo;
        TextView desc;
        TextView dataLancamento;
        RatingBar avaliacao;
        ImageView poster;
        ImageView capa;

        public ItemFilmeHolder(View view) {
            titulo = (TextView) view.findViewById(R.id.item_titulo);
            desc = (TextView) view.findViewById(R.id.item_desc);
            dataLancamento = (TextView) view.findViewById(R.id.item_data);
            avaliacao = (RatingBar) view.findViewById(R.id.item_avaliacao);
            poster = (ImageView) view.findViewById(R.id.item_poster);
            capa = (ImageView) view.findViewById(R.id.item_capa);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;

        switch (viewType) {
            case VIEW_TYPE_DESTAQUE: {
                layoutId = R.layout.item_filme_destaque;
                break;
            }
            case VIEW_TYPE_ITEM: {
                layoutId = R.layout.item_filme;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ItemFilmeHolder holder = new ItemFilmeHolder(view);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ItemFilmeHolder holder = (ItemFilmeHolder) view.getTag();
        int viewType = getItemViewType(cursor.getPosition());

        int tituloIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_TITULO);
        int descricaoIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_DESCRICAO);
        int posterIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_POSTER_PATH);
        int capaIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_CAPA_PATH);
        int avaliacaoIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_AVALIACAO);
        int dataLancamentoIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_DATA_LANCAMENTO);

        switch (viewType) {
            case VIEW_TYPE_DESTAQUE: {
                holder.titulo.setText(cursor.getString(tituloIndex));
                holder.avaliacao.setRating(cursor.getFloat(avaliacaoIndex));
                new DownloadImageTask(holder.capa).execute(cursor.getString(capaIndex));
                break;
            }
            case VIEW_TYPE_ITEM: {
                holder.titulo.setText(cursor.getString(tituloIndex));
                holder.desc.setText(cursor.getString(descricaoIndex));
                holder.dataLancamento.setText(cursor.getString(dataLancamentoIndex));
                holder.avaliacao.setRating(cursor.getFloat(avaliacaoIndex));
                new DownloadImageTask(holder.poster).execute(cursor.getString(posterIndex));
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && useFilmeDestaque ? VIEW_TYPE_DESTAQUE : VIEW_TYPE_ITEM);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public void setUseFilmeDestaque(boolean useFilmeDestaque) {
        this.useFilmeDestaque = useFilmeDestaque;
    }
}
