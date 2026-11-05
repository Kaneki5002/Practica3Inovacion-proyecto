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

public class Perfil extends Fragment {

    private ImageView btnVolver, btnEngranajeEditar;
    private CardView btnOpcionConfiguracion;
    private View navBtnInicio;

    public Perfil() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVolver = view.findViewById(R.id.btnVolver);
        btnEngranajeEditar = view.findViewById(R.id.btnEngranajeEditar);
        btnOpcionConfiguracion = view.findViewById(R.id.btnOpcionConfiguracion);
        navBtnInicio = view.findViewById(R.id.nav_btnInicio);

        // Volver
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

        // Tocar el Engranaje superior -> Envía a Editar Perfil (Imagen 2)
        if (btnEngranajeEditar != null) {
            btnEngranajeEditar.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_perfil_to_editarPerfil);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // Tocar tarjeta "Configuración" -> Envía a Ajustes de Configuración (Imagen 3)
        if (btnOpcionConfiguracion != null) {
            btnOpcionConfiguracion.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_perfil_to_configuracion);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // Tocar botón Inicio de la barra inferior -> Regresa al Menú Principal
        if (navBtnInicio != null) {
            navBtnInicio.setOnClickListener(v -> {
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_perfil_to_menu2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}