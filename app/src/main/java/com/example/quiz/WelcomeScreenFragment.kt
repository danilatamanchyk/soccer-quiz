package com.example.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.quiz.databinding.FragmentWelcomeScreenBinding

class WelcomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentWelcomeScreenBinding>(
            inflater, R.layout.fragment_welcome_screen, container, false
        )
        binding.buttonLetsQuiz.setOnClickListener { view: View ->
            view.findNavController().navigate(
                R.id.action_welcomeScreenFragment_to_quizFragment
            )
        }
        binding.buttonSettings.setOnClickListener { view: View ->
            view.findNavController().navigate(
                R.id.action_welcomeScreenFragment_to_settingsFragment
            )
        }
        return binding.root
    }
}