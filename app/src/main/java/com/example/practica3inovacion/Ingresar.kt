package com.example.practica3inovacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Ingresar : Fragment() {

    private var crdIniciar: CardView? = null
    private var crdCrearCuenta: CardView? = null
    private var btnRegistrate: LinearLayout? = null

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingresar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Vincular las vistas
        crdIniciar = view.findViewById(R.id.ingresar_crdiniciar)
        crdCrearCuenta = view.findViewById(R.id.ingresar_crdcrearcuenta)
        btnRegistrate = view.findViewById(R.id.ingresar_btnregistrate)

        // 2. Acción del botón "Iniciar" -> Navega a Login
        crdIniciar?.setOnClickListener { v ->
            navegar(v, R.id.action_ingresar_to_login)
        }

        // 3. Acción del botón "Crear Cuenta" -> Navega a Registro
        crdCrearCuenta?.setOnClickListener { v ->
            navegar(v, R.id.action_ingresar_to_autoReguistro)
        }

        // 4. Acción del texto "Regístrate" -> Navega a Registro
        btnRegistrate?.setOnClickListener { v ->
            navegar(v, R.id.action_ingresar_to_autoReguistro)
        }
    }

    private fun navegar(view: View, actionId: Int) {
        try {
            val navController = Navigation.findNavController(view)
            navController.navigate(actionId)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error de navegación", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Ingresar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}