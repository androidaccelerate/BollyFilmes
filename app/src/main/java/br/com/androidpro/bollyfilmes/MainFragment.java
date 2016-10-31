package br.com.androidpro.bollyfilmes;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private int posicaoItem = ListView.INVALID_POSITION;

    private static final String KEY_POSICAO = "SELECIONADO";

    private ListView list;

    private FilmesAdapter adapter;

    private boolean useFilmeDestaque = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        list = (ListView) view.findViewById(R.id.list_filmes);

        final ArrayList<ItemFilme> arrayList = new ArrayList<>();
//        arrayList.add(new ItemFilme("Homem Aranha", "Filme de heroi picado por uma aranha", "10/04/2016", 4));
//        arrayList.add(new ItemFilme("Homem Cobra", "Filme de heroi picado por uma cobra", "06/01/2016", 2));
//        arrayList.add(new ItemFilme("Homem Javali", "Filme de heroi mordido por uma javali", "30/06/2016", 3));
//        arrayList.add(new ItemFilme("Homem Passaro", "Filme de heroi picado por uma passaro", "23/05/2016", 5));
//        arrayList.add(new ItemFilme("Homem Cachorro", "Filme de heroi mordido por uma cachorro", "14/02/2016", 3.5f));
//        arrayList.add(new ItemFilme("Homem Gato", "Filme de heroi mordido por uma gato", "10/04/2016", 2.5f));

        adapter = new FilmesAdapter(getContext(), arrayList);
        adapter.setUseFilmeDestaque(useFilmeDestaque);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemFilme itemFilme = arrayList.get(position);
                Callback callback = (Callback) getActivity();
                callback.onItemSelected(itemFilme);
                posicaoItem = position;
            }
        });

        if(savedInstanceState != null && savedInstanceState.containsKey(KEY_POSICAO)) {
            posicaoItem = savedInstanceState.getInt(KEY_POSICAO);
        }

        // https://api.themoviedb.org/3/movie/popular?api_key=qwer08776&language=pt-BR

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            String urlBase = "https://api.themoviedb.org/3/movie/popular?";
            String apiKey = "api_key";
            String language = "language";

            Uri uriApi = Uri.parse(urlBase).buildUpon()
                    .appendQueryParameter(apiKey, "")
                    .appendQueryParameter(language, "pt-BR")
                    .build();

            URL url = new URL(uriApi.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String linha;
            StringBuffer buffer = new StringBuffer();
            while ((linha = reader.readLine()) != null) {
                buffer.append(linha);
                buffer.append("\n");
            }

            List<ItemFilme> list = JsonUtil.fromJsonToList(buffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(posicaoItem != ListView.INVALID_POSITION) {
            outState.putInt(KEY_POSICAO, posicaoItem);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (posicaoItem != ListView.INVALID_POSITION && list != null) {
            list.smoothScrollToPosition(posicaoItem);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_atualizar:
                Toast.makeText(getContext(), "Atualizando os filmes...", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUseFilmeDestaque(boolean useFilmeDestaque) {
        this.useFilmeDestaque = useFilmeDestaque;

        if (adapter != null) {
            adapter.setUseFilmeDestaque(useFilmeDestaque);
        }
    }

    public interface Callback {
        void onItemSelected(ItemFilme itemFilme);
    }

}
