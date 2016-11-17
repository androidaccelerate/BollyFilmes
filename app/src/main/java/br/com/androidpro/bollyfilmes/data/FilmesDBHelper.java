package br.com.androidpro.bollyfilmes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FilmesDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "bollyfilmes.db";

    public FilmesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTableFilmes = "CREATE TABLE " + FilmesContract.FilmeEntry.TABLE_NAME + " (" +
                FilmesContract.FilmeEntry._ID + " INTEGER PRIMARY KEY, " +
                FilmesContract.FilmeEntry.COLUMN_TITULO + " TEXT NOT NULL, " +
                FilmesContract.FilmeEntry.COLUMN_DESCRICAO + " TEXT NOT NULL, " +
                FilmesContract.FilmeEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FilmesContract.FilmeEntry.COLUMN_CAPA_PATH + " TEXT NOT NULL, " +
                FilmesContract.FilmeEntry.COLUMN_AVALIACAO + " REAL, " +
                FilmesContract.FilmeEntry.COLUMN_DATA_LANCAMENTO + " TEXT NOT NULL, " +
                FilmesContract.FilmeEntry.COLUMN_POPULARIDADE + " REAL " +
                ");";

        db.execSQL(sqlTableFilmes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + FilmesContract.FilmeEntry.TABLE_NAME);
        onCreate(db);
    }
}
