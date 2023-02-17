package com.example.proyectopracticaeval2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectopracticaeval2.databinding.ActivityPasswordSignUpBinding
import com.google.gson.Gson

private lateinit var binding: ActivityPasswordSignUpBinding

class PasswordSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cuando el usuario pulsa el botón de "Atrás", se cierra la activity.
        binding.backBtn.setOnClickListener {
            finish()
        }

        // Mostramos el email que el usuario ha introducido en la activity anterior.
        val email = intent.getStringExtra("email")
        binding.emailUsed.text = email

        // Creamos la función "functionalButtons()" que contiene la lógica de los botones de esta activity.
        functionalButtons()
    }

    private fun functionalButtons() {
        // Cuando el usuario pulsa el botón de "Continuar", se comprueba si la contraseña es válida y se guarda el usuario en el fichero de usuarios.
        val email = intent.getStringExtra("email")

        binding.signin.setOnClickListener {
            // Comprobamos si la contraseña es válida: Utiliza un mínimo de 6 caracteres (distingue mayúsculas de minúsculas) e incluye 2 de estos tipos: letras, números y caracteres especiales.
            if (binding.password.text.toString().isEmpty())
                binding.textField.error = "La contraseña no puede estar en blanco"
            else
                if (binding.password.text.toString().length < 6)
                    binding.textField.error = "La contraseña debe tener al menos 6 caracteres"
                else
                    if (!binding.password.text.toString()
                            .matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}\$"))
                    )
                        binding.textField.error =
                            "La contraseña debe tener al menos 2 de estos tipos: letras, números y caracteres especiales"
                    else
                        registrarUsuario(email!!)
        }
    }

    private fun registrarUsuario(email: String) {
        // Guardamos el usuario en el fichero de usuarios. El fichero consta de dos String. Uno de ellos es el email y el otro es la contraseña.
        val sharedPreferences = getSharedPreferences("USUARIOS", Context.MODE_PRIVATE)
        // Obtenemos el String que contiene los usuarios registrados.
        val users = sharedPreferences.getString("users", null)
        val gson = Gson()
        // Convertimos el String en un Array de String.
        val usersArray =
            if (users != null) gson.fromJson(users, Array<String>::class.java) else arrayOf()
        // Creamos un nuevo Array de String con el tamaño del Array anterior + 2.
        val usersArrayNew = Array(usersArray.size + 2) { "" }
        // Copiamos los datos del Array anterior al nuevo Array.
        for (i in usersArray.indices)
            usersArrayNew[i] = usersArray[i]
        // Añadimos el nuevo usuario al Array.
        usersArrayNew[usersArrayNew.size - 2] = email
        // Añadimos la contraseña del nuevo usuario al Array.
        usersArrayNew[usersArrayNew.size - 1] = binding.password.text.toString()
        // Convertimos el Array en un String y lo guardamos en el fichero de usuarios.
        val usersNew = gson.toJson(usersArrayNew)
        // Guardamos el nuevo String en el fichero de usuarios.
        sharedPreferences.edit().putString("users", usersNew).apply()
        // Abrimos la activity de "Home" una vez registrado correctamente al usuario.
        startActivity(Intent(this, HomeActivity::class.java))
    }
}