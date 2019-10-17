package com.danilolimadev.minhasanotacoes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private EditText texto;
    private ImageView botaoSalvar;
    private static final String NOME_ARQUIVO = "arquivo_anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = findViewById(R.id.textoId);
        botaoSalvar = findViewById(R.id.botaoSalvarId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(MainActivity.this, "Anotação salva com sucesso!", Toast.LENGTH_SHORT).show();

            }
        });

        //Recuperar o que foi gravado
        if(lerArquivo()!=null){
            texto.setText(lerArquivo());
        }
    }

    private void gravarNoArquivo(String texto){

        try {    //Tanta gravar o arquivo, caso não consiga gera uma exeção no catch informando que houve um erro

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE) ); //Esse modo indica que só a nosa aplicação poderá ler esse arquivo
            outputStreamWriter.write(texto);
            outputStreamWriter.close();

        }catch (IOException e){   //É uma exeção que é gerada sempre que tentar fazer uma operação de Input ou Output, ou seja inserir ou ler arquivos
            Log.v("MainActivity", e.toString());
        }

    }

    private String lerArquivo(){

        String resultado = "";

        try{

            //Abrir arquivo
            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            if(arquivo != null){

                //ler o arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //gerar buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //recuperar textos do arquivo
                String linhaArquivo = "";
                while((linhaArquivo = bufferedReader.readLine()) != null){
                    resultado += linhaArquivo;
                }

                arquivo.close();

            }

        }catch (IOException e){
            Log.v("MainActivity", e.toString());
        }


        return resultado;

    }
}
