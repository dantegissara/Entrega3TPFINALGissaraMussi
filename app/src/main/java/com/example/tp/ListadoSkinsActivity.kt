package com.example.tp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListadoSkinsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_skins)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRazas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val razas = listOf("Akita", "Basenji", "Beagle", "Boxer", "Bulldog","Chihuahua", "Collie", "Dachshund", "Dalmatian", "Dane",
            "Doberman", "Greyhound", "Havanese", "Hound", "Husky","Keeshond", "Komondor", "Kuvasz", "Labrador", "Maltese","Mastiff",
            "Newfoundland", "Papillon", "Pitbull", "Pointer", "Pomeranian","Poodle", "Pug", "Rottweiler", "Saluki", "Samoyed","Schipperke",
            "Terrier", "Vizsla", "Weimaraner", "Whippet") // Aquí proporciona tus razas reales

        val adapter = RazasAdapter(razas)
        recyclerView.adapter = adapter
    }
}