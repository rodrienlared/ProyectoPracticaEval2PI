package com.example.proyectopracticaeval2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import com.example.proyectopracticaeval2.databinding.ActivitySignUpBinding
import com.google.gson.Gson

private lateinit var binding: ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cuando el usuario pulsa el botón de "Volver", se cierra la activity actual.
        binding.backBtn.setOnClickListener {
            finish()
        }

        // Creamos la función "functionalButtons" para handlear los botones de la activity.
        functionalButtons()

        // Creamos la función "styleish" para darle estilo a la activity (colores)
        styleish()
    }

    private fun functionalButtons() {
        binding.continuarToPasswordFromSignUp.setOnClickListener {
            if (binding.email.text.toString().isEmpty())
                binding.textField.error = "El correo electrónico no puede estar en blanco"
            else
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString())
                        .matches()
                )
                    binding.textField.error = "El correo electrónico no es válido"
                else
                // Si el email está registrado, se muestra un error. Para ello, se llama a la función "isTheEmailRegistered()" que comprueba si el email está registrado en el fichero de usuarios.
                    if (isTheEmailRegistered(binding.email.text.toString()))
                        binding.textField.error = "El correo electrónico ya está registrado"
                    else {
                        val intent = Intent(this, PasswordSignUpActivity::class.java)
                        intent.putExtra("email", binding.email.text.toString())
                        startActivity(intent)
                    }
        }
    }

    private fun isTheEmailRegistered(email: String): Boolean {
        // Comprueba si el email está registrado en el fichero de usuarios. El fichero consta de dos String. Uno de ellos es el email y el otro es la contraseña.
        val sharedPreferences = getSharedPreferences("USUARIOS", Context.MODE_PRIVATE)
        // Obtenemos el String de los usuarios
        val users = sharedPreferences.getString("users", null)
        // Si el String no está vacío, lo convertimos en un Array de String y comprobamos si el email está registrado.
        if (users != null) {
            val gson = Gson()
            // Convertimos el String en un Array de String
            val usersArray = gson.fromJson(users, Array<String>::class.java)
            // Comprobamos si el email está registrado
            for (i in usersArray.indices step 2)
            // Si el email está registrado, devolvemos true
                if (usersArray[i] == email)
                    return true
        }
        // Si el email no está registrado, devolvemos false
        return false
    }

    private fun styleish() {
        // Cambiamos el color del checkbox
        var spannableString = SpannableString(binding.checkbox.text)
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#03A9F4")),
            123,
            161,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.checkbox.text = spannableString

        // Cambiamos el color del texto "se describe en nuestra Política de Privacidad" reutilizando las variables anteriores
        spannableString = SpannableString(binding.disclaimer.text)
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#03A9F4")),
            239,
            261,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // Subrayamos el texto "Política de Privacidad" utilizando UnderlineSpan
        spannableString.setSpan(UnderlineSpan(), 239, 261, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#03A9F4")),
            393,
            415,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // Lo mismo para los demás textos de color azul
        spannableString.setSpan(UnderlineSpan(), 393, 415, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#03A9F4")),
            417,
            436,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(UnderlineSpan(), 417, 436, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#03A9F4")),
            460,
            489,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(UnderlineSpan(), 460, 489, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Cambiamos el texto de la variable "spannableString" por el texto de la variable "disclaimer"
        binding.disclaimer.text = spannableString
    }
}