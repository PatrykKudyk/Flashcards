package com.example.flashcards.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R

class PackageRecyclerViewAdapter(var packagesList: ArrayList<Package>) :
    RecyclerView.Adapter<PackageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.row_package, parent, false)
        return PackageViewHolder(cellRow)
    }

    override fun getItemCount(): Int {
        return packagesList.size
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {

    }

}


class PackageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}