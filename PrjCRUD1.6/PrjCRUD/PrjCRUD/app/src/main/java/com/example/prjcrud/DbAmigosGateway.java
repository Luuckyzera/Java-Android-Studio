package com.example.prjcrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbAmigosGateway {

    private static DbAmigosGateway gw;
    private SQLiteDatabase db;


    private DbAmigosGateway(Context ctx){
        DbAmigosHelper helper = new DbAmigosHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public static  DbAmigosGateway getInstance(Context ctx){
        if (gw == null)
            gw = new DbAmigosGateway(ctx);
        return gw;
    }

    public SQLiteDatabase getDatabase(){
        return this.db;
    }
}
