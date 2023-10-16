package com.example.tp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RazasAdapter(private val razas: List<String>) : RecyclerView.Adapter<RazasAdapter.RazaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RazaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.raza_item, parent, false)
        return RazaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RazaViewHolder, position: Int) {
        holder.bind(razas[position])
    }

    override fun getItemCount(): Int {
        return razas.size
    }

    inner class RazaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val razaNombre: TextView = itemView.findViewById(R.id.razaNombre)

        fun bind(raza: String) {
            razaNombre.text = raza
        }
    }
}