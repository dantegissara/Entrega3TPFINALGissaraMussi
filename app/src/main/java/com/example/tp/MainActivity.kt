package com.example.tp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.json.JSONObject
import okhttp3.Request
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),OnQueryTextListener {

    private  lateinit var  binding: ActivityMainBinding
    private  lateinit var  adapter: DogAdapter
    private  val dogImages = mutableListOf<String>()
    lateinit var toolbar: Toolbar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        startService(Intent(this, NotificacionService::class.java))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        initRecyclerview()

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title=resources.getString(R.string.titulo)


    }

    private fun initRecyclerview() {
        adapter= DogAdapter(dogImages)
        binding.rvDogs.layoutManager= LinearLayoutManager(this)
        binding.rvDogs.adapter=adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call=getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            val puppies=call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    val images= puppies?.images ?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    ShowError()
                }
                hideKeyboard()
            }

        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun ShowError() {
        Toast.makeText(this,"ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_listado){
            val intentListado = Intent(this, ListadoSkinsActivity::class.java)
            startActivity(intentListado)
        }

        if (item.itemId == R.id.item_cerrar_sesion) {

            val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
            preferencias.edit().remove(resources.getString(R.string.Nombre_usuario)).apply()
            preferencias.edit().remove(resources.getString(R.string.password_usuario)).apply()


            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }
        return  true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return  true
    }

}
