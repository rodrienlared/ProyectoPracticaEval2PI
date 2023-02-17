package com.example.proyectopracticaeval2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.proyectopracticaeval2.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mostramos un mensaje de alerta al usuario para que sepa que puede volver a la primera activity haciendo doble clic en el icono de "Usuario" en la parte inferior derecha de la pantalla.
        showAlert()

        binding.user.setOnClickListener {
            binding.user.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun showAlert() {
        // Como hemos hecho en ejercicios anteriores, creamos un AlertDialog para mostrar el mensaje de alerta al usuario.
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alerta")
        builder.setMessage("Para volver a la primera activity, haz doble clic en el icono de \"Usuario\" en la parte inferior derecha de la pantalla.\n\nPulsa fuera de este cuadro de diálogo para quitar este aviso.")
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}