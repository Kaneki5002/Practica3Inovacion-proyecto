package com.example.practica3inovacion;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends Fragment {

    private CardView crdEntrar;
    private View crdRegistrar;
    private TextInputEditText edtUsuario, edtPassword;
    private DatabaseReference mDatabase;

    public login() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            // Inicialización con la URL del Realtime Database
            mDatabase = FirebaseDatabase.getInstance("https://proyecto-integrador-af3b4-default-rtdb.firebaseio.com/").getReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        vincular(view);
        eventos(view);
    }

    private void vincular(@NonNull View view) {
        crdEntrar = view.findViewById(R.id.login_crdentrar);
        crdRegistrar = view.findViewById(R.id.login_crdreguistro);
        edtUsuario = view.findViewById(R.id.login_edtusuario);
        edtPassword = view.findViewById(R.id.login_edtpassword);
    }

    private void eventos(View rootView) {
        if (crdEntrar != null) {
            crdEntrar.setOnClickListener(v -> clicEntrar(rootView));
        }

        if (crdRegistrar != null) {
            crdRegistrar.setOnClickListener(v -> clicRegistrar(rootView));
        }
    }

    private void clicEntrar(View rootView) {
        reproducirSonido();

        String usuarioIngresado = edtUsuario != null && edtUsuario.getText() != null ? edtUsuario.getText().toString().trim() : "";
        String passIngresada = edtPassword != null && edtPassword.getText() != null ? edtPassword.getText().toString().trim() : "";

        if (usuarioIngresado.isEmpty() || passIngresada.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, ingresa tu usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mDatabase == null) {
            Toast.makeText(getContext(), "Error de conexión con Firebase", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sanitización del usuario ingresado para coincidir con el registro
        String claveUsuario = usuarioIngresado.replace(".", "_").replace("@", "_").replace("#", "_").replace("$", "_");

        // Búsqueda directa en la rama 'usuarios' usando la clave de usuario
        mDatabase.child("usuarios").child(claveUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passReal = snapshot.child("password").getValue(String.class);
                    if (passReal != null && passReal.equals(passIngresada)) {
                        Toast.makeText(getContext(), "¡Bienvenido al sistema!", Toast.LENGTH_SHORT).show();
                        navegarSeguro(rootView, R.id.action_login_to_menu2);
                    } else {
                        Toast.makeText(getContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error en Firebase: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clicRegistrar(View rootView) {
        reproducirSonido();
        navegarSeguro(rootView, R.id.action_login_to_autoReguistro);
    }

    private void navegarSeguro(View view, int actionId) {
        try {
            if (view != null) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(actionId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al navegar: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void reproducirSonido() {
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