package com.example.daniel.ej1formulario

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.daniel.ej1formulario.Usuario

class MainActivity : AppCompatActivity() {

    lateinit var usuario1: Usuario

    private val LOGTAG = "INFO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Outlets
        val registerButton = findViewById(R.id.registerButton) as Button;
        val nameTextField = findViewById(R.id.nameTextField) as TextView;
        val emailTextField = findViewById(R.id.emailTextField) as TextView;
        val ageTextField = findViewById(R.id.ageTextField) as TextView;
        val usernameTextField = findViewById(R.id.usernameTextField) as TextView;
        val passwordTextField = findViewById(R.id.passwordTextField) as TextView;
        val passwordConfirmationTextField = findViewById(R.id.passwordConfirmationTextField) as TextView;
        val termsAndCondition = findViewById(R.id.termsCheckBox) as CheckBox;

        //Register button tapped
        registerButton.setOnClickListener {

            //Validaciones
            var validaDatos: Boolean = true

            if (nameTextField.length() == 0) {
                nameTextField.error = "El nombre no puede estar vacío"
                validaDatos = false
            }

            if (ageTextField.length() == 0) {
                ageTextField.error = "Por favor ingresa tu edad"
                validaDatos = false
            }

            if (emailTextField.length() == 0 || !emailTextField.text.contains("@")) {
                emailTextField.setError("Por favor proporciona una dirección de correo válida")
                validaDatos = false
            }

            if (usernameTextField.length() == 0) {
                usernameTextField.setError("El nombre de usuario no puede estar vacío")
                validaDatos = false
            }

            if (passwordTextField.length() == 0) {
                passwordTextField.setError("Elige una contraseña válida")
                validaDatos = false
            }

            if (passwordTextField.text.toString() != passwordConfirmationTextField.text.toString()) {
                passwordConfirmationTextField.setError("Las contraseñas no coinciden")
                validaDatos = false
            }

            if (termsAndCondition.isChecked.not()) {
                termsAndCondition.setError("Por favor acepta los términos y condiciones")
                validaDatos = false
            }

            if (validaDatos) {
                usuario1 = Usuario(nameTextField.text.toString(),
                                    emailTextField.text.toString(),
                                    Integer.parseInt(ageTextField.text.toString()),
                                    usernameTextField.text.toString(),
                                    passwordTextField.text.toString())

                Log.i(LOGTAG, usuario1.toString())
                Toast.makeText(applicationContext, usuario1.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
