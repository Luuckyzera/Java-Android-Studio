package com.example.prjcrud;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prjcrud.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        Button btnSalvar   = (Button) findViewById(R.id.btnSalvar);
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fabexcluido =(FloatingActionButton) findViewById(R.id.fab_Excluido);


        // esse bloco verifica se a acvitity foi acionada para uma edição ou apenas listagem dos amigos na view
        Intent intent = getIntent();
        if (intent.hasExtra("amigo")){
            findViewById(R.id.include_cadastro).setVisibility(View.VISIBLE);
            findViewById(R.id.include_listagem).setVisibility(View.INVISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            findViewById(R.id.fab_Excluido).setVisibility(View.INVISIBLE);
            amigoAlterado = (DbAmigo) intent.getSerializableExtra("amigo");

            EditText edtNome      =(EditText) findViewById(R.id.edtNome);
            EditText edtCelular   =(EditText) findViewById(R.id.edtCelular);

            edtNome.setText(amigoAlterado.getNome());
            edtCelular.setText(amigoAlterado.getCelular());
            int status = 2;
        }
        // COMENTÁRIOS DO CODIGO ACIMA
        // esse código permite que o app identifique o processo assim
        // que tiver um amigo na chamda então o app entenderá que é um
        // processo de alteração e ativiará a tela de edição de
        // dados (que é a mesma da inserção)

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sincronizando os campos com o contexto
                EditText edtNome = (EditText) findViewById(R.id.edtNome);
                EditText edtCelular = (EditText) findViewById(R.id.edtCelular);

                // Adaptando atributos
                String nome = edtNome.getText().toString();
                String celular = edtCelular.getText().toString();
                int siutacao = 1;

                //Gravando no banco de dados
                DBAmigosDAO dao = new DBAmigosDAO(getBaseContext());
                boolean sucesso;
                if(amigoAlterado != null){
                    sucesso = dao.salvar(amigoAlterado.getId(), nome, celular , 20);

                }else{
                    sucesso = dao.salvar(nome, celular, 10);
                }
                if (sucesso)
                {
                    DbAmigo amigo = dao.ultimoAmigo();
                    if (amigoAlterado != null){
                        adapter.atualizarAmigo(amigo);
                        amigoAlterado = null;
                        configurarRecycler();
                    }else {
                        adapter.inserirAmigo(amigo);
                    }
                    Snackbar.make(view,"Dados de ["+nome+"] Salvos com sucesso!",Snackbar.LENGTH_LONG)
                    .setAction("Ação",null).show();

                    //Inicializando os campos do contexto
                    edtNome.setText("");
                    edtCelular.setText("");

                    //Atualizando visibilidades
                    findViewById(R.id.include_listagem).setVisibility(View.VISIBLE);
                    findViewById(R.id.include_cadastro).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                }else{
                    //Mensagem de erro
                    Snackbar.make(view,"Erro ao salvar, consulte o log!",
                            Snackbar.LENGTH_LONG).setAction("Ação", null).show();
                }
            }
        });
        btnCancelar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Cancelando...",Snackbar.LENGTH_LONG)
                        .setAction("ACTION",null).show();
                findViewById(R.id.include_listagem).setVisibility(View.VISIBLE);
                findViewById(R.id.include_cadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        }));
        fabexcluido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.include_listagem).setVisibility(View.VISIBLE);
                findViewById(R.id.include_cadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.include_listagem_deletado).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab_Excluido).setVisibility(View.INVISIBLE);
                configurarRecyclerAmigosExcluidos();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.include_listagem).setVisibility(View.INVISIBLE);
                findViewById(R.id.include_cadastro).setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            }
        });
        configurarRecycler();
        configurarRecyclerAmigosExcluidos();
    }

    RecyclerView recyclerView;
    DbAmigosAdapter adapter;

    //C:\Users\5149354\Downloads\PrjCRUD

    private void configurarRecycler(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DBAmigosDAO dao = new DBAmigosDAO(this);
        adapter = new DbAmigosAdapter(dao.listarAmigos());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    RecyclerView recyclerViewExcluido;
    DbAmigosAdapterExcluidos adapterExcluidos;

    private void configurarRecyclerAmigosExcluidos(){
        recyclerViewExcluido= (RecyclerView)findViewById(R.id.recyclerViewexcluido);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewExcluido.setLayoutManager(layoutManager);

        DBAmigosDAO dao = new DBAmigosDAO(this);
        adapter = new DbAmigosAdapter(dao.listarAmigos());
        recyclerViewExcluido.setAdapter(adapterExcluidos);
        recyclerViewExcluido.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    DbAmigo amigoAlterado = null;
    private int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i=0; (i<spinner.getCount())&&!
                (spinner.getItemAtPosition(i).toString()
                        .equalsIgnoreCase(myString));i++);

        return index;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}