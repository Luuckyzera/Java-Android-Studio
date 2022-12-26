package com.example.prjcrud;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class DbAmigosAdapterExcluidos extends
        RecyclerView.Adapter<DbAmigoHolderExcluidos>{
    private final List<DbAmigo> amigos;

    public DbAmigosAdapterExcluidos(List<DbAmigo> amigos){
        this.amigos = amigos;
    }
    @Override
    public DbAmigoHolderExcluidos onCreateViewHolder(ViewGroup parent, int viewType){
        return new
                DbAmigoHolderExcluidos(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_dados_amigos_excluidos, parent, false));
    }

    @Override
    public void onBindViewHolder(DbAmigoHolderExcluidos holder, @SuppressLint("RecyclerView") int position) {
        holder.nmAmigo.setText(amigos.get(position).getNome());
        holder.vlCelular.setText(amigos.get(position).getCelular());

        // Botão para restaurar o amigo
        final DbAmigo amigo = amigos.get(position);
        holder.btnReciclar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja restaurar o amigo ["+amigo.getNome().toString()+"]?")
                        .setPositiveButton("Restaurar", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int whitch){
                                DbAmigo amigo = amigos.get(position);
                                DBAmigosDAO dao = new DBAmigosDAO(view.getContext());
                                boolean sucesso = dao.alterarStatusRest(amigo.getId());
                                excluirAmigo(amigo);
                                if(sucesso){
                                    Snackbar.make(view, "Restaurando o amigo ["+amigo.getNome().toString()+"]!",Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });

    }

    @Override
    public int getItemCount(){
        return  amigos != null ? amigos.size() : 0;
    }
    public void excluirAmigo(DbAmigo amigo){
        int position = amigos.indexOf(amigo);
        amigos.remove(position);
        notifyItemRemoved(position);
    }

}

