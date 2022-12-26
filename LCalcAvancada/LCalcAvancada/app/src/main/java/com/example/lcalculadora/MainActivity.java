package com.example.lcalculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public Float vl1, vl2, vlres;
    public Float vlmemoria , auxmerory;
    public String Txtgrava , Operacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vlmemoria = Float.valueOf(0);
        auxmerory = Float.valueOf(0);


        EditText editVlr1 = (EditText) findViewById(R.id.Vlr1);
        EditText editVlr2 = (EditText) findViewById(R.id.Vlr2);
        TextView Txvresultado = (TextView) findViewById(R.id.Txvresultado);
        TextView Txvmemory = (TextView) findViewById(R.id.Textmemory);
        ImageButton Btsoma = (ImageButton) findViewById(R.id.Btsoma);
        ImageButton Btsubtacao = (ImageButton) findViewById(R.id.Btsubtacao);
        ImageButton Btmulti = (ImageButton) findViewById(R.id.Btmulti);
        ImageButton Btdividir = (ImageButton) findViewById(R.id.Btdivisao);
        Button Btfinalizaar = (Button) findViewById(R.id.Btfinalizaar);

        /*                                        VARIAVEIS QUE VÃO SER UTILIZADAS                                                               */

        /*                                        SOMA                                                                                           */


        // Componente que está em uso (focado
        //
        View view = this.getCurrentFocus();
        /* descer o teclado após terminar de digitar os valores*/
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        /* fim do código */

        Btsoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vl1 = Float.valueOf(editVlr1.getText().toString());
                vl2 = Float.valueOf(editVlr2.getText().toString());

                vlres = vl1 + vl2;

                Txvresultado.setText(String.valueOf(vlres));
                Operacao = " + "; // Passa o Símbolo da Operação para formar a String do Histórico
                gravatxt(Operacao);
            }
        });

        /*------------------------------------------------------------------------------------------------------------------------*/

        /*                                                     FIM SOMA                                                                        */

        /*                                                     COMEÇO SUBTRAÇÃO                                                                 */


        Btsubtacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vl1 = Float.valueOf(editVlr1.getText().toString());
                vl2 = Float.valueOf(editVlr2.getText().toString());

                vlres = vl1 - vl2;

                Txvresultado.setText(String.valueOf(vlres));
                Operacao = " - "; // Passa o Símbolo da Operação para formar a String do Histórico
                gravatxt(Operacao);
            }
        });
        /*                                                    FIM SUBTRAÇÃO                                                                        */

        /*                                                   INCIO MULTIPLICAÇÃO                                                                  */


        Btmulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vl1 = Float.valueOf(editVlr1.getText().toString());
                vl2 = Float.valueOf(editVlr2.getText().toString());

                vlres = vl1 * vl2;


                Txvresultado.setText(String.valueOf(vlres));
                Operacao = " * "; // Passa o Símbolo da Operação para formar a String do Histórico
                gravatxt(Operacao);

            }
        });
        /*                                                 FIM MULTIPLICAÇÃO                                                                             */
        /*                                                 COMEÇO DA DIVISÃO                                                                             */


        Btdividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vl1 = Float.valueOf(editVlr1.getText().toString());
                vl2 = Float.valueOf(editVlr2.getText().toString());

                if (vl2 == 0)
                    Toast.makeText(getApplicationContext(), "Essa calculadora não realiza tal operação", Toast.LENGTH_LONG).show();
                else
                    vlres = vl1 / vl2;
                Txvresultado.setText(String.valueOf(vlres));
                // Chama o Método Gravar TXT
                Operacao = " / "; // Passa o Símbolo da Operação para formar a String do Histórico
                gravatxt(Operacao);


            }
        });

        /*                                              FIM DA DIVISÃO                                                                                     */


        Btfinalizaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        // INICIO DA FUNCAO M+

        Button btsomamen = (Button) findViewById(R.id.Btsomamen);

        btsomamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vlmemoria += vlres;

                Txvmemory.setText(String.valueOf(vlmemoria));
            }
        });
        // FIM DA FUNCAO M+


        // INICIO DA FUNCAO M-


        Button btsubmen = (Button) findViewById(R.id.Btsubmen);

        btsubmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vlmemoria -= vlres;

                Txvmemory.setText(String.valueOf(vlmemoria));
            }
        });
        // FIM DA FUNCAO DE  M-

        // COMECO DA FUNCAO MR
        Button btrecmen = (Button) findViewById(R.id.Btrecmen);

        btrecmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vlres = vlmemoria;

                Txvresultado.setText(String.valueOf(vlres));
            }
        });
        // FIM DA FUNCAO DE MR

        // COMECO DA FUNCAO DE MS
        Button btsubsmen = (Button) findViewById(R.id.Btsubsmen);

        btsubsmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vlmemoria = vlres;

                Txvmemory.setText(String.valueOf(vlmemoria));
            }
        });
        // FIM DA FUNCAO DE MS


        // INICIO DA FUNCAO DE MC
        Button btclearmen = (Button) findViewById(R.id.Btclearmen);

        btclearmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vlmemoria = auxmerory;

                Txvmemory.setText(String.valueOf(vlmemoria));
            }
        });
        // FIM DA FUNCAO DE MC

        // CHAMANDO NOVA TELA

        Button Bthistorico = (Button) findViewById(R.id.Bthistorico);

        Bthistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Historico.class);
                startActivity(intent);
            }
        });
    }
        private void gravatxt (String Operacao){
            // Data
            Date dataHoraAtual = new Date();
            String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);

            //Concatenando para Gravar
            Txtgrava = (dataAtual + ";" + vl1 + Operacao + vl2 + " = " + vlres);


            File file = new File(MainActivity.this.getFilesDir(), "calculadora");
            if (!file.exists()) {
                file.mkdir();
            }

            try {
                FileOutputStream textogravar = openFileOutput("historico.txt", MODE_APPEND);
                textogravar.write(Txtgrava.getBytes());
                textogravar.write('\n');
                textogravar.flush();
                textogravar.close();
                Toast.makeText(MainActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Ocorreu o erro: " + e, Toast.LENGTH_LONG).show();
            }

        }

    }
