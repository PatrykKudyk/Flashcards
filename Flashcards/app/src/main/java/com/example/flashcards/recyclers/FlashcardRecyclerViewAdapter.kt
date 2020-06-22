package com.example.flashcards.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.MainActivity
import com.example.flashcards.R
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
    }

}


class FlascardViewHolder(val view: View) : RecyclerView.ViewHolder(view)