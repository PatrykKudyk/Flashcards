package com.example.flashcards.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.models.MyPackage
import kotlinx.android.synthetic.main.row_package.view.*

class PackageRecyclerViewAdapter(var packagesList: ArrayList<MyPackage>) :
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
        var editing = false
        holder.view.package_cell_name.text = packagesList[position].title
        holder.view.package_cell_image_view_edit.setOnClickListener {
            if (editing) {
                if (holder.view.package_cell_name_edit_text.text.toString() != "") {
                    holder.view.package_cell_image_view_edit.setImageResource(R.drawable.ic_edit)
                    holder.view.package_cell_name_edit_text_layout.visibility = View.GONE
                    holder.view.package_cell_name.visibility = View.VISIBLE
                    holder.view.package_cell_name.text =
                        holder.view.package_cell_name_edit_text.text
                    editing = false
                } else {
                    Toast.makeText(
                        holder.view.context,
                        holder.view.context.getString(R.string.toast_package_name_not_null),
                        Toast.LENGTH_SHORT
                    )
                }
            } else {
                holder.view.package_cell_image_view_edit.setImageResource(R.drawable.ic_save)
                holder.view.package_cell_name_edit_text_layout.visibility = View.VISIBLE
                holder.view.package_cell_name.visibility = View.GONE
                holder.view.package_cell_name_edit_text.setText(holder.view.package_cell_name.text)
                editing = true
            }
        }
    }

}


class PackageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}