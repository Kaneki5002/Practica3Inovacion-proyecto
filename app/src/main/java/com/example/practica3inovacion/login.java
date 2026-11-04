package com.example.practica3inovacion;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class login extends Fragment {

    private CardView crdEntrar;
    private View crdRegistrar;
    private TextInputEditText edtUsuario, edtPassword;
    private DatabaseReference mDatabase;

    public login() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar referencia a Firebase
        mDatabase = FirebaseDatabase.getInstance("https://proyecto-integrador-af3b4-default-rtdb.firebaseio.com/").getReference();

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
            Toast.makeText(getContext(), "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String claveUsuario = usuarioIngresado.replace(".", "_").replace("@", "_");

        // CONSULTAR FIREBASE DATABASE
        mDatabase.child("usuarios").child(claveUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passReal = snapshot.child("password").getValue(String.class);
                    if (passReal != null && passReal.equals(passIngresada)) {
                        Toast.makeText(getContext(), "¡Bienvenido!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Error de lectura en Firebase: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Error al cambiar de pantalla: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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