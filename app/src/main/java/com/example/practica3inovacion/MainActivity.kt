package com.example.practica3inovacion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización obligatoria de Firebase al abrir la aplicación
        FirebaseApp.initializeApp(this)

        setContentView(R.layout.activity_main)
    }
}