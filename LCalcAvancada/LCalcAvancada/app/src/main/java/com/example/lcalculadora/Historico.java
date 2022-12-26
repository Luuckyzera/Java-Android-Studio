package com.example.lcalculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.widget.TextView;
import android.widget.Toast;

public class Historico extends AppCompatActivity {

    public String datacompara = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        TextView Txtgrava   = (TextView) findViewById(R.id.Txtgrava);
        Button btretonar    = (Button) findViewById(R.id.Btretornar);
        Button Btclear      = (Button) findViewById(R.id.Btclear);
        Button btmanterhoje =  (Button) findViewById(R.id.btmanterhoje);


        /*BOTÃO PARA VOLTAR PARA A MAIN ACTIVITY*/
        btretonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 retornar();
            }

        });
        /*BOTÃO PARA APAGAR TUDO*/
        Btclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apagartudo();
                retornar();
            }
        });

        Date datahoragora = new Date();
        String datagora = new SimpleDateFormat("dd/MM/yyyy").format(datahoragora);
        /*--------------------------------------------------------------------------------------------------------------*/

        File arquivoLido = getFileStreamPath("historico.txt");

        //    StringBuilder text = new StringBuilder();
        BufferedReader reader = null;

        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> listhoje = new ArrayList<>();
        ArrayList<String> datalist = new ArrayList<>();
        ArrayList<String> valorlist = new ArrayList<>();

        try {

            if (arquivoLido.exists()) {

                FileInputStream arquivoLi = openFileInput("historico.txt");

                reader = new BufferedReader(new InputStreamReader(arquivoLi));
                StringBuffer stringBuffer = new StringBuffer();
                String tempStr = "";

                // Preenche a Lista com os dados do TxT
                while ((tempStr = reader.readLine()) != null) {

                    list.add(tempStr);
                }

                // Inverte Ordem da Lista
                Collections.reverse(list);

                // Percorre os itens da Lista para imprimir na tela
                for (String itens : list) {

                    String[] vect = itens.split(";");
                    String data = vect[0];
                    String nome = vect[1];

                    // Se for branco ou se for diferente da data, datacompara recebe data
                    if (datacompara == "" || !datacompara.equals(data)) {
                        datacompara = data;
                    }
                    if (datacompara == data) {
                        Txtgrava.append(data + '\n');
                        Txtgrava.append(nome + '\n');
                    } else {
                        Txtgrava.append(nome + '\n');
                    }

                }

            }
        } catch (Exception e) {
            Toast.makeText(Historico.this, "Ocorreu o erro: " + e, Toast.LENGTH_LONG).show();

        }
        btmanterhoje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Txtgrava.setText("");

                // Gera lista com apenas os dados da data de hoje
                for(String itensatual : list){
                    if(itensatual.contains(datagora)){
                        listhoje.add(itensatual);
                    }
                }

                String datacompara2 = "";
                // Percorre os itens da Lista para imprimir na tela
                for(String itens2 : listhoje){

                    String[] vect = itens2.split(";");
                    String data = vect[0];
                    String nome = vect[1];

                    // Se for branco ou se for diferente da data, datacompara recebe data
                    if (datacompara2 == "" || !datacompara2.equals(data)){
                        datacompara2 = data;
                    }
                    if(datacompara2 == data){
                        Txtgrava.append(data + '\n');
                        Txtgrava.append(nome + '\n');
                    }else{
                        Txtgrava.append(nome + '\n');
                    }
                }


                // Limpa arquivo TXT e Grava apenas as informações de hoje
                apagartudo();
                // Inverte Ordem da Lista
                Collections.reverse(listhoje);

                File file = new File(Historico.this.getFilesDir(), "calculadora");
                if (!file.exists()) {
                    file.mkdir();
                }

                try {
                    for (String itenssalvahoje : listhoje) {
                        FileOutputStream textogravar = openFileOutput("historico.txt", MODE_APPEND);
                        textogravar.write(itenssalvahoje.getBytes());
                        textogravar.write('\n');
                        textogravar.flush();
                        textogravar.close();
                        Toast.makeText(Historico.this, "Saved your text", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(Historico.this, "Ocorreu o erro: " + e, Toast.LENGTH_LONG).show();
                }

                // Limpa List para evitar duplicação na hora de mostrar na tela e gravar no TXT
                listhoje.clear();
            }
        });

    }
    public void apagartudo() {

        File arquivodelete = getFileStreamPath("historico.txt");

        try {
            if (arquivodelete.exists()) {
                FileOutputStream arquivodel = openFileOutput("historico.txt", MODE_PRIVATE);
                arquivodel.write(("").getBytes());
                arquivodel.flush();
                arquivodel.close();
                Toast.makeText(Historico.this, "Dados Apagados com Sucesso", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(Historico.this, "Ocorreu o erro: " + e, Toast.LENGTH_LONG).show();

        }
    }
    public void retornar() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

