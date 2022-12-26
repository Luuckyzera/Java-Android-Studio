package com.example.prjcrud;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class DbAmigosAdapter extends RecyclerView.Adapter<DbAmigosHolder> {

    private final List<DbAmigo> amigos;

    public DbAmigosAdapter(List<DbAmigo> amigos){
        this.amigos = amigos;
    }
    @NonNull

    @Override
    public DbAmigosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new
                DbAmigosHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.activity_dados_amigo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DbAmigosHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nmAmigo.setText(amigos.get(position).getNome());
        holder.vlCelular.setText(amigos.get(position).getCelular());

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("amigo", amigos.get(position));
                activity.finish();
                activity.startActivity(intent);
            }
        });


        final DbAmigo amigo = amigos.get(position);
        holder.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir o amigo ["+amigo.getNome().toString()+ "?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbAmigo amigo = amigos.get(position);
                                DBAmigosDAO dao = new DBAmigosDAO(view.getContext());
                               boolean sucesso = dao.alterarStatus(amigo.getId());
                               excluirAmigo(amigo);
                                if (sucesso){
                                    Snackbar.make(view,"Excluindo o amigo [" + amigo.getNome().toString()+"]!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                             //excluirAmigo(amigo);
                                }else {
                                    Snackbar.make(view, "Erro ao excluir o amigo ["+amigo.getNome().toString()+"]!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar",null)
                        .create()
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return amigos != null ? amigos.size() : 0;
    }

    public void inserirAmigo (DbAmigo amigo){
        amigos.add(amigo);
        notifyItemInserted(getItemCount());
    }
    public void atualizarAmigo(DbAmigo amigo){
        amigos.set(amigos.indexOf(amigo), amigo);
        notifyItemChanged(amigos.indexOf(amigo));
    }

    public void excluirAmigo(DbAmigo amigo){
        int position = amigos.indexOf(amigo);
        amigos.remove(position);
        notifyItemRemoved(position);
    }
    public void alteraramigo(){
        int position = amigos.indexOf(amigos);
    }

    private Activity getActivity(View view){
        Context context = view.getContext();

            while(context instanceof ContextWrapper){
                if(context instanceof Activity){
                    return (Activity) context;
                }
                context = ((ContextWrapper)context).getBaseContext();
            }
            return null;
    }
}
