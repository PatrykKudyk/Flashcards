package com.example.flashcards.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.flashcards.R
import com.example.flashcards.db.DataBaseHelper
import com.example.flashcards.models.Flashcard
import kotlinx.android.synthetic.main.fragment_add_package.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccountFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Long? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var helpView: View
    private lateinit var cardView: CardView
    private lateinit var mainTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getLong(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_review, container, false);
        initFragment()
        return rootView
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Long) =
            ReviewFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                }
            }
    }

    private fun initFragment() {
        nextButton = rootView.findViewById(R.id.review_button_next)
        previousButton = rootView.findViewById(R.id.review_button_previous)
        helpView = rootView.findViewById(R.id.review_view)
        cardView = rootView.findViewById(R.id.review_card_view_main)
        mainTextView = rootView.findViewById(R.id.review_text_view_main)

        val dbHelper = DataBaseHelper(rootView.context)
        val flashcardsList = dbHelper.getFlashcardsList(param1 as Long)
        flashcardsList.shuffle()
        setTextSize(flashcardsList[0].question)
        mainTextView.setText(flashcardsList[0].question)
        var number = 0
        var question = true

        cardView.setOnClickListener {
            if (question) {
                question = false
                setTextSize(flashcardsList[number].answer)
                mainTextView.setText(flashcardsList[number].answer)
            } else {
                question = true
                setTextSize(flashcardsList[number].question)
                mainTextView.setText(flashcardsList[number].question)
            }
        }

        nextButton.setOnClickListener {
            number++
            question = true
            setTextSize(flashcardsList[number].question)
            mainTextView.setText(flashcardsList[number].question)
            if (number == flashcardsList.size - 1) {
                nextButton.visibility = View.GONE
            }
            if (number >= 1) {
                previousButton.visibility = View.VISIBLE
                helpView.visibility = View.GONE
            }
        }

        previousButton.setOnClickListener {
            number--
            question = true
            setTextSize(flashcardsList[number].question)
            mainTextView.setText(flashcardsList[number].question)
            if (number == 0) {
                previousButton.visibility = View.GONE
                helpView.visibility = View.VISIBLE
            }
            if (number < flashcardsList.size - 1) {
                nextButton.visibility = View.VISIBLE
            }
        }
    }

    private fun setTextSize(string: String){
        if(string.length <= 40) {
            mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35F)
        } else if (string.length <= 50) {
            mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30F)
        } else if (string.length <= 70) {
            mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24F)
        }
    }
}