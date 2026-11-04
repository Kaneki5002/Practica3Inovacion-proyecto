package com.example.practica3inovacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Menu extends Fragment {

    private LinearLayout btnSaludos, btnColores, btnPersonas, btnNumeros, btnNaturaleza, btnAnimales;
    private LinearLayout btnTiempoClima, btnComidas, btnLugares, btnOraciones, btnCulturaOtomi;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Menu() {
        // Required empty public constructor
    }

    public static Menu newInstance(String param1, String param2) {
        Menu fragment = new Menu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Vincular los elementos según sus IDs del XML
        btnSaludos = view.findViewById(R.id.btnSaludos);
        btnColores = view.findViewById(R.id.btnColores);
        btnPersonas = view.findViewById(R.id.btnPersonas);
        btnNumeros = view.findViewById(R.id.btnNumeros);
        btnNaturaleza = view.findViewById(R.id.btnNaturaleza);
        btnAnimales = view.findViewById(R.id.btnAnimales);

        btnTiempoClima = view.findViewById(R.id.btnTiempoClima);
        btnComidas = view.findViewById(R.id.btnComidas);
        btnLugares = view.findViewById(R.id.btnLugares);
        btnOraciones = view.findViewById(R.id.btnOraciones);
        btnCulturaOtomi = view.findViewById(R.id.btnCulturaOtomi);

        // 2. Eventos de navegación para la primera sección de módulos
        if (btnSaludos != null) btnSaludos.setOnClickListener(v -> navegar(v, R.id.action_menu2_to_producto1));
        if (btnColores != null) btnColores.setOnClickListener(v -> navegar(v, R.id.action_menu2_to_producto2));
        if (btnPersonas != null) btnPersonas.setOnClickListener(v -> navegar(v, R.id.action_menu2_to_producto3));
        if (btnNumeros != null) btnNumeros.setOnClickListener(v -> navegar(v, R.id.action_menu2_to_numeros));
        if (btnNaturaleza != null) btnNaturaleza.setOnClickListener(v -> navegar(v, R.id.action_menu2_to_naturaleza));
        if (btnAnimales != null) btnAnimales.setOnClickListener(v -> navegar(v, R.id.action_menu2_to_animales));

        // 3. Eventos para la segunda sección de módulos
        if (btnTiempoClima != null) btnTiempoClima.setOnClickListener(v -> mostrarMensaje("Tiempo y clima"));
        if (btnComidas != null) btnComidas.setOnClickListener(v -> mostrarMensaje("Comidas"));
        if (btnLugares != null) btnLugares.setOnClickListener(v -> mostrarMensaje("Lugares"));
        if (btnOraciones != null) btnOraciones.setOnClickListener(v -> mostrarMensaje("Oraciones"));
        if (btnCulturaOtomi != null) btnCulturaOtomi.setOnClickListener(v -> mostrarMensaje("Cultura otomí"));
    }

    private void navegar(View view, int actionId) {
        try {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(actionId);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al abrir el módulo", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarMensaje(String modulo) {
        Toast.makeText(getContext(), "Módulo seleccionado: " + modulo, Toast.LENGTH_SHORT).show();
    }
}