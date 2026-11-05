package com.example.practica3inovacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Menu extends Fragment {

    private DrawerLayout drawerLayout;
    private ImageView btnMenu;

    // BOTONES PRINCIPALES DE PANTALLA (MÓDULOS)
    private View btnSaludos, btnColores, btnPersonas, btnNumeros, btnNaturaleza, btnAnimales;
    private View btnTiempoClima, btnComidas, btnLugares, btnOraciones, btnCulturaOtomi;

    // BOTONES DEL MENÚ DESPLEGABLE (DRAWER)
    private View drawerBtnSaludos, drawerBtnColores, drawerBtnPersonas, drawerBtnNumeros, drawerBtnNaturaleza, drawerBtnAnimales;
    private View drawerBtnTiempoClima, drawerBtnComidas, drawerBtnLugares, drawerBtnOraciones, drawerBtnCulturaOtomi;

    // BOTONES DE LA BARRA DE NAVEGACIÓN INFERIOR (BOTTOM NAV)
    private View navBtnInicio, navBtnProgreso, navBtnPerfil, navBtnLogros;

    public Menu() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawerLayout = view.findViewById(R.id.drawer_layout);
        btnMenu = view.findViewById(R.id.menu_btnmenu);

        // VINCULACIÓN BOTONES PRINCIPALES
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

        // VINCULACIÓN BOTONES DEL MENÚ DESPLEGABLE
        drawerBtnSaludos = view.findViewById(R.id.drawer_btnSaludos);
        drawerBtnColores = view.findViewById(R.id.drawer_btnColores);
        drawerBtnPersonas = view.findViewById(R.id.drawer_btnPersonas);
        drawerBtnNumeros = view.findViewById(R.id.drawer_btnNumeros);
        drawerBtnNaturaleza = view.findViewById(R.id.drawer_btnNaturaleza);
        drawerBtnAnimales = view.findViewById(R.id.drawer_btnAnimales);
        drawerBtnTiempoClima = view.findViewById(R.id.drawer_btnTiempoClima);
        drawerBtnComidas = view.findViewById(R.id.drawer_btnComidas);
        drawerBtnLugares = view.findViewById(R.id.drawer_btnLugares);
        drawerBtnOraciones = view.findViewById(R.id.drawer_btnOraciones);
        drawerBtnCulturaOtomi = view.findViewById(R.id.drawer_btnCulturaOtomi);

        // VINCULACIÓN DE LA BARRA INFERIOR
        navBtnInicio = view.findViewById(R.id.nav_btnInicio);
        navBtnProgreso = view.findViewById(R.id.nav_btnProgreso);
        navBtnPerfil = view.findViewById(R.id.nav_btnPerfil);
        navBtnLogros = view.findViewById(R.id.nav_btnLogros);

        // ABRIR MENÚ DESPLEGABLE
        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> {
                if (drawerLayout != null) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        asignarEventos(view);
    }

    private void asignarEventos(View rootView) {
        // EVENTOS BOTONES PRINCIPALES
        if (btnSaludos != null) btnSaludos.setOnClickListener(v -> navegar(rootView, R.id.action_menu2_to_producto1));
        if (btnNumeros != null) btnNumeros.setOnClickListener(v -> navegar(rootView, R.id.action_menu2_to_numeros));
        if (btnNaturaleza != null) btnNaturaleza.setOnClickListener(v -> navegar(rootView, R.id.action_menu2_to_naturaleza));
        if (btnAnimales != null) btnAnimales.setOnClickListener(v -> navegar(rootView, R.id.action_menu2_to_animales));
        if (btnCulturaOtomi != null) btnCulturaOtomi.setOnClickListener(v -> navegar(rootView, R.id.action_menu2_to_culturaOtomi));

        // EVENTOS DEL MENÚ DESPLEGABLE (DRAWER)
        if (drawerBtnSaludos != null) drawerBtnSaludos.setOnClickListener(v -> navegarCerrandoDrawer(rootView, R.id.action_menu2_to_producto1));
        if (drawerBtnColores != null) drawerBtnColores.setOnClickListener(v -> mostrarMensaje("Colores"));
        if (drawerBtnPersonas != null) drawerBtnPersonas.setOnClickListener(v -> mostrarMensaje("Personas"));
        if (drawerBtnNumeros != null) drawerBtnNumeros.setOnClickListener(v -> navegarCerrandoDrawer(rootView, R.id.action_menu2_to_numeros));
        if (drawerBtnNaturaleza != null) drawerBtnNaturaleza.setOnClickListener(v -> navegarCerrandoDrawer(rootView, R.id.action_menu2_to_naturaleza));
        if (drawerBtnAnimales != null) drawerBtnAnimales.setOnClickListener(v -> navegarCerrandoDrawer(rootView, R.id.action_menu2_to_animales));
        if (drawerBtnTiempoClima != null) drawerBtnTiempoClima.setOnClickListener(v -> mostrarMensaje("Tiempo y clima"));
        if (drawerBtnComidas != null) drawerBtnComidas.setOnClickListener(v -> mostrarMensaje("Comidas"));
        if (drawerBtnLugares != null) drawerBtnLugares.setOnClickListener(v -> mostrarMensaje("Lugares"));
        if (drawerBtnOraciones != null) drawerBtnOraciones.setOnClickListener(v -> mostrarMensaje("Oraciones"));
        if (drawerBtnCulturaOtomi != null) drawerBtnCulturaOtomi.setOnClickListener(v -> navegarCerrandoDrawer(rootView, R.id.action_menu2_to_culturaOtomi));

        // EVENTOS DE LA BARRA DE NAVEGACIÓN INFERIOR
        if (navBtnInicio != null) {
            navBtnInicio.setOnClickListener(v -> {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Ya te encuentras en el Inicio", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (navBtnProgreso != null) {
            navBtnProgreso.setOnClickListener(v -> mostrarMensaje("Progreso"));
        }

        // NAVEGACIÓN A PERFIL
        if (navBtnPerfil != null) {
            navBtnPerfil.setOnClickListener(v -> navegar(rootView, R.id.action_menu2_to_perfil));
        }

        if (navBtnLogros != null) {
            navBtnLogros.setOnClickListener(v -> mostrarMensaje("Logros"));
        }
    }

    private void mostrarMensaje(String modulo) {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (getContext() != null) {
            Toast.makeText(getContext(), "Abriendo " + modulo, Toast.LENGTH_SHORT).show();
        }
    }

    private void navegarCerrandoDrawer(View view, int actionId) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        navegar(view, actionId);
    }

    private void navegar(View view, int actionId) {
        try {
            NavController controller = Navigation.findNavController(view);
            controller.navigate(actionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}