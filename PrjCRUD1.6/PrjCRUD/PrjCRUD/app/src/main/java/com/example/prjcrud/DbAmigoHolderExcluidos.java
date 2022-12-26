package com.example.prjcrud;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DbAmigoHolderExcluidos extends RecyclerView.ViewHolder {
    public TextView nmAmigo;
    public TextView vlCelular;
    public ImageButton btnReciclar;

    public DbAmigoHolderExcluidos(View itemView) {
        super(itemView);
        nmAmigo = (TextView) itemView.findViewById(R.id.nmAmigo);
        vlCelular = (TextView) itemView.findViewById(R.id.vlCelular);
        btnReciclar = (ImageButton) itemView.findViewById(R.id.btnReciclar);
    }
}
