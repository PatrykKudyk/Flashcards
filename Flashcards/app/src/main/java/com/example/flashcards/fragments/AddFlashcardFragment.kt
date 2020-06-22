package com.example.flashcards.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.flashcards.R
import com.example.flashcards.db.DataBaseHelper


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
class AddFlashcardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Long? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var addButton: Button
    private lateinit var questionEditText: EditText
    private lateinit var answerEditText: EditText

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
        rootView = inflater.inflate(R.layout.fragment_add_flashcard, container, false);
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
            AddFlashcardFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                }
            }
    }

    private fun initFragment() {
        addButton = rootView.findViewById(R.id.add_flashcard_button_add)
        questionEditText = rootView.findViewById(R.id.add_flashcard_question_edit_text)
        answerEditText = rootView.findViewById(R.id.add_flashcard_answer_edit_text)

        addButton.setOnClickListener {
            if (questionEditText.text.toString() != "" && answerEditText.text.toString() != "") {
                val dbHelper = DataBaseHelper(rootView.context)
                dbHelper.addFlashcard(
                    param1 as Long,
                    questionEditText.text.toString(),
                    answerEditText.text.toString()
                )
                fragmentManager
                    ?.popBackStack()
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_flashcard_not_null),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}