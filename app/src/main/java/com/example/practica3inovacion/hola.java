package com.example.practica3inovacion;

import android.media.MediaPlayer;
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

public class hola extends Fragment {

    private ImageView btnVolver, btnEscucharAudio;
    private CardView btnGrabar, btnContinuar, btnSiguiente;

    public hola() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hola, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVolver = view.findViewById(R.id.btnVolver);
        btnEscucharAudio = view.findViewById(R.id.btnEscucharAudio);
        btnGrabar = view.findViewById(R.id.btnGrabar);
        btnContinuar = view.findViewById(R.id.btnContinuar);
        btnSiguiente = view.findViewById(R.id.btnSiguiente);

        // 1. Botón Volver (Flecha atrás) -> Regresa a Producto1
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

        // 2. Escuchar audio Hñatho
        if (btnEscucharAudio != null) {
            btnEscucharAudio.setOnClickListener(v -> reproducirAudioPronunciacion());
        }

        // 3. Grabar práctica con micrófono
        if (btnGrabar != null) {
            btnGrabar.setOnClickListener(v -> {
                reproducirSonidoBurbuja();
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Escuchando tu pronunciación...", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // 4. Botón Continuar -> Navega a Bienvenidos
        if (btnContinuar != null) {
            btnContinuar.setOnClickListener(v -> {
                reproducirSonidoBurbuja();
                navegarA(v, R.id.action_hola2_to_bienvenidos);
            });
        }

        // 5. Botón Siguiente -> Navega a Bienvenidos (Paso 2)
        if (btnSiguiente != null) {
            btnSiguiente.setOnClickListener(v -> {
                reproducirSonidoBurbuja();
                navegarA(v, R.id.action_hola2_to_bienvenidos);
            });
        }
    }

    private void navegarA(View view, int actionId) {
        try {
            NavController controller = Navigation.findNavController(view);
            controller.navigate(actionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reproducirAudioPronunciacion() {
        try {
            if (getContext() != null) {
                MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.burbuja);
                if (mp != null) {
                    mp.setOnCompletionListener(MediaPlayer::release);
                    mp.start();
                }
                Toast.makeText(getContext(), "🔊 Hñatho (Hola)", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reproducirSonidoBurbuja() {
        try {
            if (getContext() != null) {
                MediaPlayer x = MediaPlayer.create(getContext(), R.raw.burbuja);
                if (x != null) {
                    x.setOnCompletionListener(MediaPlayer::release);
                    x.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}