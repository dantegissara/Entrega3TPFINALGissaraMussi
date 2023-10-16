package com.example.tp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import android.text.Editable
import androidx.room.RoomDatabase
import androidx.room.Database
import kotlinx.coroutines.newFixedThreadPoolContext

class  Login : AppCompatActivity() {
    private lateinit var etUsuario: EditText
    private lateinit var etPassword: EditText
    private  lateinit var cbRecordar: CheckBox
    private lateinit var btnRegistrar: Button
    private lateinit var btnIniciar: Button
    private lateinit var btnMostrar : Button
    private lateinit var etConsulta : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        saludarUsuario()

        etUsuario= findViewById(R.id.etUsuario)
        etPassword=findViewById(R.id.etPass)
        cbRecordar=findViewById(R.id.cbRecordar)
        btnIniciar=findViewById(R.id.botonIniciar)
        btnRegistrar=findViewById(R.id.botonRegistrar)
        btnMostrar=findViewById(R.id.btnMostrar)
        etConsulta=findViewById(R.id.etConsulta)

        var preferencias = getSharedPreferences(resources.getString((R.string.sp_credenciales)), MODE_PRIVATE)
        var usuarioGuardado = preferencias.getString(resources.getString(R.string.Nombre_usuario), "").toString()
        var passwordGuardado = preferencias.getString(resources.getString(R.string.password_usuario), "").toString()

        if(!usuarioGuardado.isEmpty() && !passwordGuardado.isEmpty()){
            startMainActivity(usuarioGuardado)
        }

        btnRegistrar.setOnClickListener{
            val crear = Intent(this, crearUsuario::class.java)
            //intentMain.putExtra("nombre", nombreUsuario)
            startActivity(crear)
            finish()
        }

        btnIniciar.setOnClickListener {
            var mensaje = ""
            val nombreUsuario = etUsuario.text.toString()
            val password= etPassword.text.toString()

            if (nombreUsuario.isEmpty() || password.isEmpty()) {
                mensaje += "- Faltan Datos"
            } else {

                if ((password.length >= 6 && password.any { it.isLetter() } && password.any { it.isDigit() }) && nombreUsuario.length >= 5) {

                    if (cbRecordar.isChecked) {
                        mensaje += "- Recordar Usuario"
                        var preferencias = getSharedPreferences(
                            resources.getString((R.string.sp_credenciales)),
                            MODE_PRIVATE
                        )
                        preferencias.edit()
                            .putString(resources.getString(R.string.Nombre_usuario), nombreUsuario)
                            .apply()
                        preferencias.edit().putString(
                            resources.getString(R.string.password_usuario),
                            etPassword.text.toString()
                        ).apply()
                    }

                    if (autenticarUsuario(nombreUsuario, etPassword.text.toString())) {


                        val intentMain = Intent(this, MainActivity::class.java)
                        intentMain.putExtra("nombre", nombreUsuario)
                        startActivity(intentMain)
                        finish()
                        return@setOnClickListener
                    }else{
                        mensaje = "Usuario no registrado "
                    }
                }else{
                    mensaje = "Usuario o Contraseña incorrecta"
                }

                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }
        }

        btnMostrar.setOnClickListener {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                "BaseDeDatosI"
            ).allowMainThreadQueries().build()

            val lista = db.usuariosDao().getAll()
            var valores = ""

            for (i in 0 until lista.size) {
                valores += "${lista[i].id}: ${lista[i].getUsuario()} usuario: ${lista[i].usuario} pass: ${lista[i].password}\n"
            }

            etConsulta.text = Editable.Factory.getInstance().newEditable(valores)
        }

    }

    private fun saludarUsuario() {
        var bundle: Bundle? = intent.extras
        if(bundle != null){
            var usuario = bundle?.getString(resources.getString(R.string.Nombre_usuario))
            Toast.makeText(this, "Bienvenido/a $usuario", Toast.LENGTH_LONG).show()
        }
    }

    private fun startMainActivity(usuarioGuardado: String) {
        val intentMain = Intent(this, MainActivity::class.java)
        intentMain.putExtra(resources.getString(R.string.Nombre_usuario), usuarioGuardado)
        startActivity(intentMain)
        finish()
    }

    private fun autenticarUsuario(usuario: String, contraseña: String): Boolean {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "BaseDeDatosI"
        ).allowMainThreadQueries().build()

        val usuarioEnBaseDeDatos = db.usuariosDao().findByName(usuario)

        return usuarioEnBaseDeDatos != null && usuarioEnBaseDeDatos.password == contraseña
    }

}



