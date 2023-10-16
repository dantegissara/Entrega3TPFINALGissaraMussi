package com.example.tp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room


class crearUsuario : AppCompatActivity() {

    private lateinit var etCorreo: EditText
    private lateinit var etUsuario: EditText
    private lateinit var etPass: EditText
    private lateinit var etRepetirPass: EditText
    private lateinit var btnCrear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)

        etCorreo=(findViewById(R.id.etCorreo))
        etUsuario=findViewById(R.id.etUsuario)
        etPass=(findViewById(R.id.etPass))
        etRepetirPass=(findViewById(R.id.etRepetirPass))
        btnCrear=findViewById(R.id.btnCrear)

        btnCrear.setOnClickListener{
            var mensaje = "Crear Usuario"
            var nombreUsuario= etUsuario.text.toString()
            val password = etPass.text.toString()
            val repetirPassword = etRepetirPass.text.toString()
            val correo= etCorreo.text.toString()

            if(nombreUsuario.isEmpty() || password.isEmpty() || repetirPassword.isEmpty() || correo.isEmpty()){
                mensaje= " Faltan Datos"

            }else{
                if (nombreUsuario.length < 5) {
                    mensaje = "El usuario debe tener al menos 5 caracteres"
                } else if (password.length < 6 || !password.any { it.isLetter() } || !password.any { it.isDigit() }) {
                    mensaje = "La contraseña debe tener al menos 6 caracteres y al menos un número"
                } else if (password != repetirPassword) {
                    mensaje = "Las contraseñas no coinciden"
                } else {
                    mensaje = "Usuario Creado Correctamente"

                    val db = Room.databaseBuilder(
                        applicationContext,
                        AppDataBase::class.java,
                        "BaseDeDatosI"
                    ).allowMainThreadQueries().build()

                    val usuarios = Usuarios(nombreUsuario, password)
                    val reg = db.usuariosDao().insert(usuarios)

                    val intentMain = Intent(this, Login::class.java)
                    startActivity(intentMain)
                    finish()
                }
            }

            Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
        }

    }
}