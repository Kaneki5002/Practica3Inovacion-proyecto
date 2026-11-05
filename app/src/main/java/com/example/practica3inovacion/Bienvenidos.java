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

public class Bienvenidos extends Fragment {

    private ImageView btnVolver, btnEscucharAudio;
    private CardView btnGrabar, btnContinuar, btnSiguiente;

    public Bienvenidos() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bienvenidos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVolver = view.findViewById(R.id.btnVolver);
        btnEscucharAudio = view.findViewById(R.id.btnEscucharAudio);
        btnGrabar = view.findViewById(R.id.btnGrabar);
        btnContinuar = view.findViewById(R.id.btnContinuar);
        btnSiguiente = view.findViewById(R.id.btnSiguiente);

        // 1. Botón Volver -> Regresa a la pantalla anterior ('hola')
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

        // 2. Audio de "Hogä ehe"
        if (btnEscucharAudio != null) {
            btnEscucharAudio.setOnClickListener(v -> reproducirAudioPronunciacion());
        }

        // 3. Micrófono
        if (btnGrabar != null) {
            btnGrabar.setOnClickListener(v -> {
                reproducirSonidoBurbuja();
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Escuchando pronunciación de Hogä ehe...", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // 4. Botón Continuar
        if (btnContinuar != null) {
            btnContinuar.setOnClickListener(v -> {
                reproducirSonidoBurbuja();
                avanzarSiguiente(v);
            });
        }

        // 5. Botón Siguiente -> Avanza al paso 3 de la lección
        if (btnSiguiente != null) {
            btnSiguiente.setOnClickListener(v -> {
                reproducirSonidoBurbuja();
                avanzarSiguiente(v);
            });
        }
    }

    private void avanzarSiguiente(View v) {
        try {
            NavController controller = Navigation.findNavController(v);
            // Reemplaza 'action_bienvenidos_to_gracias' con el ID exacto de tu acción en hola.xml
            controller.navigate(R.id.action_bienvenidos_to_gracias);
        } catch (Exception e) {
            e.printStackTrace();
            if (getContext() != null) {
                Toast.makeText(getContext(), "¡Excelente avance!", Toast.LENGTH_SHORT).show();
            }
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
                Toast.makeText(getContext(), "🔊 Hogä ehe (Bienvenidos)", Toast.LENGTH_SHORT).show();
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