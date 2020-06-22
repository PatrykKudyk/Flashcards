package com.example.flashcards

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flashcards.fragments.*

class MainActivity : AppCompatActivity(),
    MainMenuFragment.OnFragmentInteractionListener,
    CreditsFragment.OnFragmentInteractionListener,
    PackagesFragment.OnFragmentInteractionListener,
    AddPackageFragment.OnFragmentInteractionListener,
    FlashcardsFragment.OnFragmentInteractionListener,
    AddFlashcardFragment.OnFragmentInteractionListener {

    private lateinit var mainMenuFragment: MainMenuFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainMenuFragment = MainMenuFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom,
                R.anim.enter_top_to_bottom, R.anim.exit_bottom_to_top
            )
            .add(R.id.main_frame_layout, mainMenuFragment)
            .commit()

    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}