package br.com.androidpro.bollyfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FilmeDetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhe);

        Intent intent = getIntent();
        ItemFilme itemFilme = (ItemFilme) intent.getSerializableExtra(MainActivity.KEY_FILME);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FilmeDetalheFragment fragment = new FilmeDetalheFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.KEY_FILME, itemFilme);
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_filme_detalhe, fragment);

        fragmentTransaction.commit();
    }
}
