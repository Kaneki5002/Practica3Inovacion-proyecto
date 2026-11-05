package com.example.practica3inovacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class Producto1 : Fragment() {

    private var btnVolver: ImageView? = null
    private var btnComprar: CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_producto1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnVolver = view.findViewById(R.id.btnVolver)
        btnComprar = view.findViewById(R.id.btnComprar)

        // Volver al menú
        btnVolver?.setOnClickListener {
            try {
                findNavController().navigateUp()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Acción del botón Continuar -> Avanza hacia 'hola2'
        btnComprar?.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_producto1_to_hola2)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}