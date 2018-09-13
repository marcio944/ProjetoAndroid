package com.aplicativo.lenovouser.meuaplicativo.Introducao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aplicativo.lenovouser.meuaplicativo.R;

public class AlgoritmoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmo);
    }

    public void iniciarExemploAlgoritmo(View view){
        Intent intent = new Intent(this, ExemploAlgoritmoActivity.class);
        startActivity(intent);
    }

    public void retornarIntroducao(View view){
        Intent intent = new Intent(this, IntroducaoActivity.class);
        startActivity(intent);
    }

}
