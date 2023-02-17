package com.example.proyectopracticaeval2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectopracticaeval2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cuando el usuario pulsa el botón de "Iniciar sesión", se abre la activity de "Iniciar sesión".
        binding.iniciarSesion.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        // Cuando el usuario pulsa el botón de "Suscribirse ahora", se abre la activity de "Suscribirse".
        binding.suscribirseAhora.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}