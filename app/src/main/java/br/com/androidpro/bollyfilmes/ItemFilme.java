package br.com.androidpro.bollyfilmes;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*{
        "poster_path": "/IfB9hy4JH1eH6HEfIgIGORXi5h.jpg",
        "adult": false,
        "overview": "Jack Reacher must uncover the truth behind a major government conspiracy in order to clear his name. On the run as a fugitive from the law, Reacher uncovers a potential secret from his past that could change his life forever.",
        "release_date": "2016-10-19",
        "genre_ids": [
        53,
        28,
        80,
        18,
        9648
        ],
        "id": 343611,
        "original_title": "Jack Reacher: Never Go Back",
        "original_language": "en",
        "title": "Jack Reacher: Never Go Back",
        "backdrop_path": "/4ynQYtSEuU5hyipcGkfD6ncwtwz.jpg",
        "popularity": 33.793474,
        "vote_count": 304,
        "video": false,
        "vote_average": 4.38
}*/

public class ItemFilme implements Serializable {

    private long id;

    private String titulo;

    private String descricao;

    private String dataLancamento;

    // http://image.tmdb.org/t/p/w185/IfB9hy4JH1eH6HEfIgIGORXi5h.jpg
    private String posterPath;

    // http://image.tmdb.org/t/p/w185/4ynQYtSEuU5hyipcGkfD6ncwtwz.jpg
    private String capaPath;

    private float avaliacao;

    private float popularidade;

    public ItemFilme(long id, String titulo, String descricao, String dataLancamento, String posterPath, String capaPath, float avaliacao, float popularidade) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.posterPath = posterPath;
        this.capaPath = capaPath;
        this.avaliacao = avaliacao;
        this.popularidade = popularidade;
    }

    public ItemFilme(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getLong("id");
        this.titulo = jsonObject.getString("title");
        this.descricao = jsonObject.getString("overview");
        this.dataLancamento = jsonObject.getString("release_date");
        this.posterPath = jsonObject.getString("poster_path");
        this.capaPath = jsonObject.getString("backdrop_path");
        this.avaliacao = (float) jsonObject.getDouble("vote_average");
        this.popularidade = (float) jsonObject.getDouble("popularity");
    }

    private String buildPath(String width, String path) {
        StringBuilder builder = new StringBuilder();
        builder.append("http://image.tmdb.org/t/p/")
                .append(width)
                .append(path);
        return  builder.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataLancamento() {

        Locale locale = new Locale("pt", "BR");

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", locale).parse(dataLancamento);
            return new SimpleDateFormat("dd/MM/yyyy", locale).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getPosterPath() {
        return buildPath("w500", posterPath);
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getCapaPath() {
        return buildPath("w780", capaPath);
    }

    public void setCapaPath(String capaPath) {
        this.capaPath = capaPath;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }

    public float getPopularidade() {
        return popularidade;
    }

    public void setPopularidade(float popularidade) {
        this.popularidade = popularidade;
    }
}
