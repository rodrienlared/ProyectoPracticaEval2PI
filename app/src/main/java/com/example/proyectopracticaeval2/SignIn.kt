package com.example.proyectopracticaeval2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectopracticaeval2.databinding.ActivitySignInBinding
import com.google.gson.Gson

private lateinit var binding: ActivitySignInBinding

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creamos la función "handleErrors()" para comprobar si el email introducido por el usuario es válido y si está registrado en el fichero de usuarios.
        handleErrors()

        // Cuando el usuario pulsa el botón de "Volver", se abre la activity de "MainActivity".
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Cuando el usuario pulsa el botón de "Suscribirse ahora", se abre la activity de "SignUp".
        binding.subscribeNow.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    private fun handleErrors() {
        binding.signin.setOnClickListener {
            if (binding.email.text.toString().isEmpty())
                binding.textField.error = "El correo electrónico no puede estar en blanco"
            else
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString())
                        .matches()
                )
                    binding.textField.error = "El correo electrónico no es válido"
                else
                // Si el email no está registrado, se muestra un error. Para ello, se llama a la función "isTheEmailRegistered()" que comprueba si el email está registrado en el fichero de usuarios.
                    if (!isTheEmailRegistered(binding.email.text.toString()))
                        binding.textField.error = "El correo electrónico no está registrado"
                    else {
                        val intent = Intent(this, PasswordSignInActivity::class.java)
                        intent.putExtra("email", binding.email.text.toString())
                        startActivity(intent)
                    }

        }
    }

    private fun isTheEmailRegistered(email: String): Boolean {
        // Comprueba si el email está registrado en el fichero de usuarios. El fichero consta de dos String. Uno de ellos es el email y el otro es la contraseña.
        val sharedPreferences = getSharedPreferences("USUARIOS", Context.MODE_PRIVATE)
        // El fichero de usuarios se llama "USUARIOS" y se guarda en modo privado.
        val users = sharedPreferences.getString("users", null)
        // La string se llama "users" y si no existe, se devuelve null.
        if (users != null) {
            val gson = Gson()
            // Se crea un objeto de la clase Gson.
            val usersArray = gson.fromJson(users, Array<String>::class.java)
            // Se crea un array de String con los datos del fichero de usuarios.
            for (i in usersArray.indices step 2)
            // Se comprueba si el email está registrado en el fichero de usuarios, y si es así, se devuelve true.
                if (usersArray[i] == email)
                    return true
        }
        // Si el email no está registrado, se devuelve false.
        return false
    }

}