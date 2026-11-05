package com.example.practica3inovacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Ixmiquilpan extends Fragment {

    private ImageView btnVolver;
    private CardView cardBannerIxmiquilpan;
    private View navBtnInicio, navBtnPerfil;

    public Ixmiquilpan() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ixmiquilpan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVolver = view.findViewById(R.id.btnVolver);
        cardBannerIxmiquilpan = view.findViewById(R.id.cardBannerIxmiquilpan);
        navBtnInicio = view.findViewById(R.id.nav_btnInicio);
        navBtnPerfil = view.findViewById(R.id.nav_btnPerfil);

        // Regresar a ModuloCultura
        if (btnVolver != null) {
            btnVolver.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigateUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // Tocar el banner de la foto principal abre la pantalla 2 (IxmiquilpanHistoria)
        if (cardBannerIxmiquilpan != null) {
            cardBannerIxmiquilpan.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_ixmiquilpan_to_ixmiquilpanHistoria);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if (navBtnInicio != null) {
            navBtnInicio.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_ixmiquilpan_to_menu2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if (navBtnPerfil != null) {
            navBtnPerfil.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_ixmiquilpan_to_perfil);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}