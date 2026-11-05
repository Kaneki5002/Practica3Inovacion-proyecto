package com.example.practica3inovacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Ingresar extends Fragment {

    private View crdIniciar, crdCrearCuenta, btnRegistrate;

    public Ingresar() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingresar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vincular usando View genérico para asegurar compatibilidad total de layouts
        crdIniciar = view.findViewById(R.id.ingresar_crdiniciar);
        crdCrearCuenta = view.findViewById(R.id.ingresar_crdcrearcuenta);
        btnRegistrate = view.findViewById(R.id.ingresar_btnregistrate);

        // Acción al presionar "Iniciar" -> Navega hacia Login
        if (crdIniciar != null) {
            crdIniciar.setOnClickListener(v -> navegarSeguro(v, R.id.action_ingresar_to_login));
        }

        // Acción al presionar "Crear Cuenta" -> Navega hacia Registro
        if (crdCrearCuenta != null) {
            crdCrearCuenta.setOnClickListener(v -> navegarSeguro(v, R.id.action_ingresar_to_autoReguistro));
        }

        // Acción al presionar "¿No tienes cuenta? Regístrate" -> Navega hacia Registro
        if (btnRegistrate != null) {
            btnRegistrate.setOnClickListener(v -> navegarSeguro(v, R.id.action_ingresar_to_autoReguistro));
        }
    }

    private void navegarSeguro(View view, int actionId) {
        try {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(actionId);
        } catch (Exception e) {
            e.printStackTrace();
            if (getContext() != null) {
                Toast.makeText(getContext(), "Error al navegar: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}