package com.example.practica3inovacion.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ContentInfo;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.practica3inovacion.R;

public class DialogoMsg {
    private Dialog dialogo;
    private TextView tvtitulo;
    private TextView tvMessage;
    private CardView btnAccept;
    private ProgressBar progressBar;

    private Context contexto;
    public DialogoMsg(Context contexto) {
        this.contexto = contexto;
        dialogo = new Dialog(contexto);
        dialogo.setContentView(R.layout.cuadro_msg);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvtitulo = dialogo.findViewById(R.id.dialogomsg_titulo);
        tvMessage = dialogo.findViewById(R.id.dialogomsg_mensaje);
        btnAccept = dialogo.findViewById(R.id.dialogomsg_btnAccept);
        progressBar = dialogo.findViewById(R.id.dialogomsg_progressbar);
    }
    public  void mostrarDialogoBoton(String titulo, String mensaje) {
        tvMessage.setText(mensaje);
        tvtitulo.setText(titulo);
        progressBar.setVisibility(View.GONE);
        btnAccept.setVisibility(View.VISIBLE);
        dialogo.show();
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        }
        );

    }
    public  void mostrarDialogoProgress(String titulo, String mensaje) {
        tvMessage.setText(mensaje);
        tvtitulo.setText(titulo);
        progressBar.setVisibility(View.VISIBLE);
        btnAccept.setVisibility(View.GONE);
        dialogo.show();

    }

    public  void cerrarDialogo() {
        dialogo.dismiss();
    }

}
