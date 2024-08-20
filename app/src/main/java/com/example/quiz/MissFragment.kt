package com.example.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quiz.databinding.FragmentMissBinding

class MissFragment : Fragment() {

    private val args: MissFragmentArgs by navArgs()
    private var curQuestion = 0
    private var allOfQuestions = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMissBinding>(
            inflater, R.layout.fragment_miss, container, false
        )
        curQuestion = args.curQuestion
        allOfQuestions = args.allOfQuestions
        binding.textViewScore.text = "Question: $curQuestion/$allOfQuestions"
        binding.buttonOneMoreTime.setOnClickListener { view: View ->
            view.findNavController().navigate(
                R.id.action_missFragment_to_quizFragment
            )
        }
        binding.buttonGoMenu.setOnClickListener { view: View ->
            view.findNavController().navigate(
                R.id.action_missFragment_to_welcomeScreenFragment
            )
        }
        return binding.root
    }

}