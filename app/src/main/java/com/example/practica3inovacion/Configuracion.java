package com.example.practica3inovacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Configuracion extends Fragment {

    private ImageView btnVolver;

    public Configuracion() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_configuracion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVolver = view.findViewById(R.id.btnVolver);

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
    }
}