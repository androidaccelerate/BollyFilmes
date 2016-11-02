package br.com.androidpro.bollyfilmes.data;

import android.provider.BaseColumns;

public class FilmesContract {

    private FilmesContract() {}

    public static abstract class FilmeEntry implements BaseColumns {

        public static final String TABLE_NAME = "filmes";

        public static final String _ID = "_id";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_CAPA_PATH = "capaPath";
        public static final String COLUMN_AVALIACAO = "avaliacao";
    }
}
