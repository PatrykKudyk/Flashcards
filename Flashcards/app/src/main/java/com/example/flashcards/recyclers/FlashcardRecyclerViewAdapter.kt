package com.example.flashcards.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.MainActivity
import com.example.flashcards.R
import com.example.flashcards.db.DataBaseHelper
import com.example.flashcards.fragments.FlashcardsFragment
import com.example.flashcards.models.Flashcard
import kotlinx.android.synthetic.main.row_flashcard.view.*

class FlashcardRecyclerViewAdapter(var flashcardList: ArrayList<Flashcard>) :
    RecyclerView.Adapter<FlascardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlascardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowCell = layoutInflater.inflate(R.layout.row_flashcard, parent, false)
        return FlascardViewHolder(rowCell)
    }

    override fun getItemCount(): Int {
        return flashcardList.size
    }

    override fun onBindViewHolder(holder: FlascardViewHolder, position: Int) {
        holder.view.flashcard_cell_text_view_question.setText(flashcardList[position].question)
        holder.view.flashcard_cell_text_view_answer.setText(flashcardList[position].answer)
        holder.view.flashcard_cell_image_view_delete.setOnClickListener {
            holder.view.flashcard_cell_constraint_main.visibility = View.GONE
            holder.view.flashcard_cell_constraint_delete.visibility = View.VISIBLE
        }
        holder.view.flashcard_cell_button_delete_no.setOnClickListener {
            holder.view.flashcard_cell_constraint_main.visibility = View.VISIBLE
            holder.view.flashcard_cell_constraint_delete.visibility = View.GONE
        }
        holder.view.flashcard_cell_button_delete_yes.setOnClickListener {
            val dbHelper = DataBaseHelper(holder.view.context)
            val result = dbHelper.deleteFlashcard(flashcardList[position].id)
            flashcardList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, flashcardList.size)
        }
    }

}


class FlascardViewHolder(val view: View) : RecyclerView.ViewHolder(view)