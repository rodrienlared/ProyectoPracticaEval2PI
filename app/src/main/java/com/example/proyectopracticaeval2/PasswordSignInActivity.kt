package com.example.proyectopracticaeval2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.proyectopracticaeval2.databinding.ActivityPasswordSignInBinding
import com.google.gson.Gson

private lateinit var binding: ActivityPasswordSignInBinding

class PasswordSignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cuando el usuario pulsa el botón de "Volver atrás", se abre la activity de "Iniciar sesión".
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }

        // Creamos la función "functionalButtons()" que contiene el código de los botones de "Iniciar sesión" y "¿Has olvidado la contraseña?"
        functionalButtons()
    }

    private fun functionalButtons() {
        // Recuperamos el email del usuario del intent anterior
        val email = intent.getStringExtra("email")

        binding.signin.setOnClickListener {
            // Comprobamos que el email y la contraseña son correctos después de hacer las comprobaciones de errores pertinentes, así como de verificar que la contraseña no esté vacía
            if (binding.password.text.toString().isEmpty())
                binding.textField.error = "La contraseña no puede estar en blanco"
            else
                if (comprobarEmailYPassword(email!!, binding.password.text.toString()))
                // Si el email y la contraseña son correctos, se abre la actividad principal de la aplicación
                    startActivity(Intent(this, HomeActivity::class.java))
                else
                    binding.textField.error = "La contraseña no es correcta"
        }

        binding.forgotPassword.setOnClickListener {
            // Abrimos este enlace: "https://www.disneyplus.com/reset-password" desde la propia aplicación sin salir de ella gracias a las "Google Custom Tabs"
            CustomTabsIntent.Builder().build()
                .launchUrl(this, Uri.parse("https://www.disneyplus.com/reset-password"))
        }
    }

    private fun comprobarEmailYPassword(email: String, passwordIntroducida: String): Boolean {
        // Comprueba si el email y la contraseña son correctos. El fichero consta de dos String. Uno de ellos es el email y el otro es la contraseña.
        val sharedPreferences = getSharedPreferences("USUARIOS", Context.MODE_PRIVATE)
        // Recuperamos el String de los usuarios del fichero de preferencias
        val users = sharedPreferences.getString("users", null)
        // Si el String no es nulo, lo convertimos en un Array de String y comprobamos si el email y la contraseña son correctos
        if (users != null) {
            val gson = Gson()
            // Convertimos el String en un Array de String
            val usersArray = gson.fromJson(users, Array<String>::class.java)
            // Comprobamos si el email y la contraseña son correctos
            for (i in usersArray.indices step 2)
                if (usersArray[i] == email && usersArray[i + 1] == passwordIntroducida)
                    return true
        }
        return false
    }
}