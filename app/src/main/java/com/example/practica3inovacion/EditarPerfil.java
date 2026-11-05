package com.example.practica3inovacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class EditarPerfil extends Fragment {

    private ImageView btnVolver;
    private CardView btnGuardarCambios;

    public EditarPerfil() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVolver = view.findViewById(R.id.btnVolver);
        btnGuardarCambios = view.findViewById(R.id.btnGuardarCambios);

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

        if (btnGuardarCambios != null) {
            btnGuardarCambios.setOnClickListener(v -> {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "¡Cambios guardados con éxito!", Toast.LENGTH_SHORT).show();
                }
                try {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigateUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}