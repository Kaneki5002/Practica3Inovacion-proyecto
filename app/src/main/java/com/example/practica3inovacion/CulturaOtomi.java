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

public class CulturaOtomi extends Fragment {

    private ImageView btnVolver;
    private CardView btnComenzarCultura;
    private View navBtnInicio, navBtnPerfil;

    public CulturaOtomi() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cultura_otomi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVolver = view.findViewById(R.id.btnVolver);
        btnComenzarCultura = view.findViewById(R.id.btnComenzarCultura);
        navBtnInicio = view.findViewById(R.id.nav_btnInicio);
        navBtnPerfil = view.findViewById(R.id.nav_btnPerfil);

        // FLECHA DE REGRESO -> Vuelve al menú anterior
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

        // BOTÓN "COMENZAR" -> Navega a la pantalla ModuloCultura (Valle del Mezquital)
        if (btnComenzarCultura != null) {
            btnComenzarCultura.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_culturaOtomi_to_moduloCultura);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // Navegación desde la barra inferior
        if (navBtnInicio != null) {
            navBtnInicio.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_culturaOtomi_to_menu2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if (navBtnPerfil != null) {
            navBtnPerfil.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_culturaOtomi_to_perfil);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}