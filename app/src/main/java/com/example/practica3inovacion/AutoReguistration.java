package com.example.practica3inovacion;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AutoReguistration extends Fragment {

    private NavController nav;
    private CardView crdFechaNac, crdGuardar, crdCancelar;
    private TextView txtFechaNac;
    private ImageView imvFoto, imvgaleria;
    private TextInputEditText edtNombre, edtApellido, edtCorreo, edtPassword, edtConfirmarPass;
    private Uri uriFoto;

    // Referencia a Firebase Realtime Database
    private DatabaseReference mDatabase;

    public AutoReguistration() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auto_reguistro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar referencia de la Realtime Database
        mDatabase = FirebaseDatabase.getInstance("https://proyecto-integrador-af3b4-default-rtdb.firebaseio.com/").getReference();

        vincular(view);
        eventos(view);
    }

    private void vincular(@NonNull View view) {
        crdGuardar = view.findViewById(R.id.aurreg_crdguardar);
        crdCancelar = view.findViewById(R.id.aurreg_crdcancelar);
        crdFechaNac = view.findViewById(R.id.autoreg_crdfechanac);
        txtFechaNac = view.findViewById(R.id.autoreg_txtfechanac);
        imvFoto = view.findViewById(R.id.autoreg_imvfoto);
        imvgaleria = view.findViewById(R.id.autoreg_imvgaleria);

        ViewGroup containerLayout = (ViewGroup) view.findViewById(R.id.aurreg_crdguardar).getParent();
        if (containerLayout != null) {
            for (int i = 0; i < containerLayout.getChildCount(); i++) {
                View child = containerLayout.getChildAt(i);
                if (child instanceof com.google.android.material.textfield.TextInputLayout) {
                    com.google.android.material.textfield.TextInputLayout til = (com.google.android.material.textfield.TextInputLayout) child;
                    TextInputEditText edit = (TextInputEditText) til.getEditText();
                    if (edit != null && edit.getHint() != null) {
                        String hint = edit.getHint().toString().toLowerCase();
                        if (hint.contains("nombre")) edtNombre = edit;
                        else if (hint.contains("apellido")) edtApellido = edit;
                        else if (hint.contains("correo")) edtCorreo = edit;
                        else if (hint.contains("confirmar")) edtConfirmarPass = edit;
                        else if (hint.contains("contraseña")) edtPassword = edit;
                    }
                }
            }
        }

        try {
            nav = NavHostFragment.findNavController(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eventos(@NonNull View view) {
        if (crdGuardar != null) crdGuardar.setOnClickListener(v -> clicGuardar(view));
        if (crdCancelar != null) crdCancelar.setOnClickListener(v -> clicCancelar(view));
        if (crdFechaNac != null) crdFechaNac.setOnClickListener(v -> clicFechar(view));
        if (imvgaleria != null) imvgaleria.setOnClickListener(v -> clickCamara());
        if (imvFoto != null) imvFoto.setOnClickListener(v -> clickGaleria());
    }

    private void clicGuardar(@NonNull View view) {
        reproducirSonido();

        String nombre = edtNombre != null && edtNombre.getText() != null ? edtNombre.getText().toString().trim() : "";
        String apellido = edtApellido != null && edtApellido.getText() != null ? edtApellido.getText().toString().trim() : "";
        String correo = edtCorreo != null && edtCorreo.getText() != null ? edtCorreo.getText().toString().trim() : "";
        String pass = edtPassword != null && edtPassword.getText() != null ? edtPassword.getText().toString().trim() : "";
        String confirmPass = edtConfirmarPass != null && edtConfirmarPass.getText() != null ? edtConfirmarPass.getText().toString().trim() : "";
        String fechaNac = txtFechaNac != null ? txtFechaNac.getText().toString() : "";

        if (correo.isEmpty() || pass.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(getContext(), "Por favor llena los campos requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirmPass)) {
            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reemplazar caracteres especiales del correo para usarlo como clave en Firebase
        String claveUsuario = correo.replace(".", "_").replace("@", "_");

        Map<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("nombre", nombre);
        usuarioMap.put("apellido", apellido);
        usuarioMap.put("correo", correo);
        usuarioMap.put("password", pass);
        usuarioMap.put("fechaNacimiento", fechaNac);

        // REGISTRAR EN FIREBASE
        mDatabase.child("usuarios").child(claveUsuario).setValue(usuarioMap)
                .addOnSuccessListener(aVoid -> {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Cuenta registrada en Firebase correctamente", Toast.LENGTH_SHORT).show();
                    }
                    if (isAdded() && nav != null) {
                        try {
                            nav.navigate(R.id.action_autoReguistro_to_login);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Error al registrar en Firebase: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void clickGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galeriaLauncher.launch(intent);
    }

    private void clicFechar(@NonNull View view) {
        Calendar calenadrio = Calendar.getInstance();
        int anio = calenadrio.get(Calendar.YEAR);
        int mes = calenadrio.get(Calendar.MONTH);
        int dia = calenadrio.get(Calendar.DAY_OF_MONTH);
        DatePicker dtpFecha = new DatePicker(this.getContext());
        dtpFecha.init(anio, mes, dia, null);

        AlertDialog dialog = new AlertDialog.Builder(this.getContext())
                .setTitle("Seleccionar fecha")
                .setView(dtpFecha)
                .setNegativeButton("cancelar", (d, which) -> txtFechaNac.setText("No se selecciono una fecha"))
                .setPositiveButton("aceptar", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button btnAceptar = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            btnAceptar.setOnClickListener(v -> {
                int añoSeleccionado = dtpFecha.getYear();
                int mesSeleccionado = dtpFecha.getMonth();
                int diaSeleccionado = dtpFecha.getDayOfMonth();

                String fecha = String.format(
                        Locale.getDefault(),
                        "%02d/%02d/%04d",
                        diaSeleccionado,
                        mesSeleccionado + 1,
                        añoSeleccionado
                );
                txtFechaNac.setText(fecha);
                dialog.dismiss();
            });
        });
        dialog.show();
    }

    private void clicCancelar(@NonNull View view) {
        reproducirSonido();
        if (isAdded() && nav != null) {
            try {
                nav.navigate(R.id.action_autoReguistro_to_login);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private final ActivityResultLauncher<Void> camaraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.TakePicturePreview(),
                    bitmap -> {
                        if (bitmap != null) {
                            imvFoto.setImageBitmap(bitmap);
                        }
                    });

    private void clickCamara() {
        camaraLauncher.launch(null);
    }

    private final ActivityResultLauncher<Intent> galeriaLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            uriFoto = result.getData().getData();
                            if (uriFoto != null) {
                                imvFoto.setImageURI(uriFoto);
                            }
                        }
                    });

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