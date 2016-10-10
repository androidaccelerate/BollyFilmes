package br.com.androidpro.bollyfilmes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_FILME = "FILME";

    private boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_filme_detalhe) != null) {

            if(savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_filme_detalhe, new FilmeDetalheFragment())
                        .commit();
            }

            isTablet = true;
        } else {
            isTablet = false;
        }
    }
}
