package com.example.flashcards.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.db.DataBaseHelper
import com.example.flashcards.recyclers.FlashcardRecyclerViewAdapter
import com.example.flashcards.recyclers.MarginItemDecoration
import com.example.flashcards.recyclers.PackageRecyclerViewAdapter


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
class FlashcardsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Long? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var addButton: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var reviewButton: Button

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
        rootView = inflater.inflate(R.layout.fragment_flashcards, container, false);
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
            FlashcardsFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                }
            }
    }

    private fun initFragment() {
        addButton = rootView.findViewById(R.id.flashcards_linear_layout_add_new)
        recyclerView = rootView.findViewById(R.id.flashcards_recycler_view)
        reviewButton = rootView.findViewById(R.id.flashcards_button_review)

        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                12
            )
        )
        val dbHelper = DataBaseHelper(rootView.context)
        val flashcardList = dbHelper.getFlashcardsList(param1 as Long)

        recyclerView.adapter =
            FlashcardRecyclerViewAdapter(flashcardList)


        addButton.setOnClickListener {
            val addFlashcardFragment = AddFlashcardFragment.newInstance(param1 as Long)
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left,
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right
                )
                ?.replace(R.id.main_frame_layout, addFlashcardFragment)
                ?.addToBackStack(AddFlashcardFragment.toString())
                ?.commit()
        }

        reviewButton.setOnClickListener {
            if (flashcardList.size == 0) {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_no_flashcards_for_review),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (flashcardList.size == 1) {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_too_few_flashcards_for_review),
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val reviewFragment = ReviewFragment.newInstance(param1 as Long)
                fragmentManager
                    ?.beginTransaction()
                    ?.setCustomAnimations(
                        R.anim.enter_left_to_right, R.anim.exit_right_to_left,
                        R.anim.enter_right_to_left, R.anim.exit_left_to_right
                    )
                    ?.replace(R.id.main_frame_layout, reviewFragment)
                    ?.addToBackStack(ReviewFragment.toString())
                    ?.commit()
            }
        }
    }
}