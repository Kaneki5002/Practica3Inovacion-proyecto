package com.example.practica3inovacion;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AutoReguistration extends Fragment {

    private NavController nav;
    private CardView crdFechaNac, crdGuardar, crdCancelar;
    private TextView txtFechaNac;
    private ImageView imvFoto, imvgaleria;
    private TextInputEditText edtNombre, edtApellido, edtUsuario, edtCorreo, edtPassword, edtConfirmarPass;

    private Uri uriFotoGaleria = null;
    private Bitmap bitmapFotoCamara = null;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    public AutoReguistration() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auto_reguistro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            mDatabase = FirebaseDatabase.getInstance("https://proyecto-integrador-af3b4-default-rtdb.firebaseio.com/").getReference();
            mStorage = FirebaseStorage.getInstance().getReference().child("fotos_perfil");
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        edtNombre = view.findViewById(R.id.autoreg_edtnombre);
        edtApellido = view.findViewById(R.id.autoreg_edtapellido);
        edtUsuario = view.findViewById(R.id.autoreg_edtusuario);
        edtCorreo = view.findViewById(R.id.autoreg_edtcorreo);
        edtPassword = view.findViewById(R.id.autoreg_edtpass);
        edtConfirmarPass = view.findViewById(R.id.autoreg_edtconfirmpass);

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

        // Al tocar la foto o la camarita, se abre la opción para elegir entre cámara o galería
        if (imvgaleria != null) imvgaleria.setOnClickListener(v -> mostrarOpcionesFoto());
        if (imvFoto != null) imvFoto.setOnClickListener(v -> mostrarOpcionesFoto());
    }

    // CUADRO DE DIÁLOGO PARA ELEGIR CÁMARA O GALERÍA
    private void mostrarOpcionesFoto() {
        if (getContext() == null) return;
        CharSequence[] opciones = {"Tomar foto con la cámara", "Elegir de la galería"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Selecciona una opción");
        builder.setItems(opciones, (dialog, item) -> {
            if (item == 0) {
                clickCamara();
            } else if (item == 1) {
                clickGaleria();
            }
        });
        builder.show();
    }

    private void clicGuardar(@NonNull View view) {
        reproducirSonido();

        String nombre = edtNombre != null && edtNombre.getText() != null ? edtNombre.getText().toString().trim() : "";
        String apellido = edtApellido != null && edtApellido.getText() != null ? edtApellido.getText().toString().trim() : "";
        String usuario = edtUsuario != null && edtUsuario.getText() != null ? edtUsuario.getText().toString().trim() : "";
        String correo = edtCorreo != null && edtCorreo.getText() != null ? edtCorreo.getText().toString().trim() : "";
        String pass = edtPassword != null && edtPassword.getText() != null ? edtPassword.getText().toString().trim() : "";
        String confirmPass = edtConfirmarPass != null && edtConfirmarPass.getText() != null ? edtConfirmarPass.getText().toString().trim() : "";
        String fechaNac = txtFechaNac != null ? txtFechaNac.getText().toString() : "";

        if (usuario.isEmpty() || correo.isEmpty() || pass.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(getContext(), "Por favor llena los campos requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirmPass)) {
            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mDatabase == null) {
            Toast.makeText(getContext(), "Error de conexión con Firebase", Toast.LENGTH_SHORT).show();
            return;
        }

        // Se usa el nombre de usuario sanitizado como clave principal para Firebase
        String claveUsuario = usuario.replace(".", "_").replace("@", "_").replace("#", "_").replace("$", "_");

        Toast.makeText(getContext(), "Guardando datos...", Toast.LENGTH_SHORT).show();

        // SUBIR FOTO SI EXISTE
        if (uriFotoGaleria != null) {
            subirFotoUri(uriFotoGaleria, claveUsuario, nombre, apellido, usuario, correo, pass, fechaNac);
        } else if (bitmapFotoCamara != null) {
            subirFotoBitmap(bitmapFotoCamara, claveUsuario, nombre, apellido, usuario, correo, pass, fechaNac);
        } else {
            // Guardar sin foto
            guardarEnDatabase(claveUsuario, nombre, apellido, usuario, correo, pass, fechaNac, "");
        }
    }

    // SUBIR IMAGEN DESDE LA GALERÍA A FIREBASE STORAGE
    private void subirFotoUri(Uri uri, String claveUsuario, String nombre, String apellido, String usuario, String correo, String pass, String fechaNac) {
        StorageReference fileRef = mStorage.child(claveUsuario + ".jpg");
        fileRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    guardarEnDatabase(claveUsuario, nombre, apellido, usuario, correo, pass, fechaNac, downloadUri.toString());
                }))
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al subir la imagen, guardando sin foto...", Toast.LENGTH_SHORT).show();
                    guardarEnDatabase(claveUsuario, nombre, apellido, usuario, correo, pass, fechaNac, "");
                });
    }

    // SUBIR IMAGEN DESDE LA CÁMARA A FIREBASE STORAGE
    private void subirFotoBitmap(Bitmap bitmap, String claveUsuario, String nombre, String apellido, String usuario, String correo, String pass, String fechaNac) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference fileRef = mStorage.child(claveUsuario + ".jpg");
        fileRef.putBytes(data)
                .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    guardarEnDatabase(claveUsuario, nombre, apellido, usuario, correo, pass, fechaNac, downloadUri.toString());
                }))
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al subir foto de cámara, guardando sin foto...", Toast.LENGTH_SHORT).show();
                    guardarEnDatabase(claveUsuario, nombre, apellido, usuario, correo, pass, fechaNac, "");
                });
    }

    // GUARDAR DATOS COMPLETOS EN REALTIME DATABASE
    private void guardarEnDatabase(String claveUsuario, String nombre, String apellido, String usuario, String correo, String pass, String fechaNac, String urlFoto) {
        Map<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("nombre", nombre);
        usuarioMap.put("apellido", apellido);
        usuarioMap.put("usuario", usuario);
        usuarioMap.put("correo", correo);
        usuarioMap.put("password", pass);
        usuarioMap.put("fechaNacimiento", fechaNac);
        usuarioMap.put("fotoUrl", urlFoto);

        mDatabase.child("usuarios").child(claveUsuario).setValue(usuarioMap)
                .addOnSuccessListener(aVoid -> {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "¡Cuenta registrada exitosamente!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "Error al registrar: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void clickGaleria() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            galeriaLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickCamara() {
        try {
            camaraLauncher.launch(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final ActivityResultLauncher<Void> camaraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.TakePicturePreview(),
                    bitmap -> {
                        if (bitmap != null && imvFoto != null) {
                            bitmapFotoCamara = bitmap;
                            uriFotoGaleria = null; // Prioridad a la nueva foto
                            imvFoto.setImageBitmap(bitmap);
                        }
                    });

    private final ActivityResultLauncher<Intent> galeriaLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            uriFotoGaleria = result.getData().getData();
                            bitmapFotoCamara = null; // Prioridad a la nueva foto
                            if (uriFotoGaleria != null && imvFoto != null) {
                                imvFoto.setImageURI(uriFotoGaleria);
                            }
                        }
                    });

    private void clicFechar(@NonNull View view) {
        if (getContext() == null) return;
        Calendar calenadrio = Calendar.getInstance();
        int anio = calenadrio.get(Calendar.YEAR);
        int mes = calenadrio.get(Calendar.MONTH);
        int dia = calenadrio.get(Calendar.DAY_OF_MONTH);
        DatePicker dtpFecha = new DatePicker(getContext());
        dtpFecha.init(anio, mes, dia, null);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Seleccionar fecha")
                .setView(dtpFecha)
                .setNegativeButton("cancelar", (d, which) -> {
                    if (txtFechaNac != null) txtFechaNac.setText("No se seleccionó fecha");
                })
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
                if (txtFechaNac != null) txtFechaNac.setText(fecha);
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