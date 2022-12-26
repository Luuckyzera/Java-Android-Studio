package com.example.prjcrud;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DBAmigosDAO {

    private final String TABLE_AMIGOS = "Amigos";
    private DbAmigosGateway gw;

    public DBAmigosDAO(Context ctx) {
        gw = DbAmigosGateway.getInstance(ctx);
    }


    public boolean salvar(String nome, String celular, int status) {
        return salvar(0, nome, celular, status);
    }

    public boolean salvar(int id, String nome, String celular, int status) {
        ContentValues cv = new ContentValues();
        cv.put("Nome", nome);
        cv.put("Celular", celular);
        cv.put("Status", status);
        if (id > 0) {
            return gw.getDatabase().update(TABLE_AMIGOS, cv, "ID=?", new String[]{id + ""}) > 0;

        } else {
            return gw.getDatabase().insert(TABLE_AMIGOS, null, cv) > 0;
        }
    }
    public boolean alterarStatus(int id) {
        ContentValues cv = new ContentValues();
        cv.put("Status", 30);
        return gw.getDatabase().update(TABLE_AMIGOS, cv, "ID=?", new String[]{id + ""}) > 0;
    }
    public boolean alterarStatusRest(int id) {
        ContentValues cv = new ContentValues();
        cv.put("Status", 20);
        return gw.getDatabase().update(TABLE_AMIGOS, cv, "ID=?", new String[]{id + ""}) > 0;
    }
    public List<DbAmigo> listarAmigosexcluidos(){
        List<DbAmigo> amigos = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Amigos WHERE Status = 30", null);

        while (cursor.moveToNext())
        {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
            @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            @SuppressLint("Range") String celular = cursor.getString(cursor.getColumnIndex("Celular"));
            @SuppressLint("Range") int situacao = cursor.getInt(cursor.getColumnIndex("Status"));
            amigos.add(new DbAmigo(id, nome , celular, situacao));
        }
        cursor.close();
        return amigos;
    }


    public List<DbAmigo> listarAmigos(){
                List<DbAmigo> amigos = new ArrayList<>();
                Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Amigos WHERE Status <= 20", null);

             while (cursor.moveToNext())
                     {
                         @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
                         @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("Nome"));
                         @SuppressLint("Range") String celular = cursor.getString(cursor.getColumnIndex("Celular"));
                         @SuppressLint("Range") int situacao = cursor.getInt(cursor.getColumnIndex("Status"));
                         amigos.add(new DbAmigo(id, nome , celular, situacao));
                     }
     cursor.close();
     return amigos;
    }
    public  DbAmigo ultimoAmigo(){
        Cursor cursor =  gw.getDatabase().rawQuery("SELECT * FROM Amigos ORDER BY ID DESC", null);
        if (cursor.moveToFirst()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
            @SuppressLint("RANGE") String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            @SuppressLint("RANGE") String celular = cursor.getString(cursor.getColumnIndex("Celular"));
            @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex("Status"));
            cursor.close();
            return new DbAmigo(id ,nome ,celular , status);
        }
        return null;
    }
    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_AMIGOS, "ID=?", new String[]{id + ""}) > 0;
    }

}

