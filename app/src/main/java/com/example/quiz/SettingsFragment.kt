package com.example.quiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.quiz.databinding.FragmentSettingsBinding
import kotlin.math.log


class SettingsFragment : Fragment() {

    private var allOfQuestions: Int = 0
    private lateinit var sharedPreferences: SharedPreferences
    private val NAME_PREFERENCES: String = "preferences"
    private val NUMBER_OF_SELECTED_ITEM: String = "numberOfSelectedItem"
    private val NUMBER_OF_QUESTIONS_PREFERENCES: String = "numberOfQuestions"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings, container, false
        )
        sharedPreferences = requireActivity().getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE)
        setListeners(binding)
        loadSettings(binding)

//        ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.spinner_difficulty, android.R.layout.simple_spinner_item
//        )
        return binding.root
    }

    private fun setListeners(binding: FragmentSettingsBinding) {
        binding.spinnerDifficulty.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when (binding.spinnerDifficulty.selectedItemPosition) {
                        0 -> {
                            allOfQuestions = 3
                            binding.editTextAmountOfQuestions.isEnabled = false
                        }
                        1 -> {
                            allOfQuestions = 5
                            binding.editTextAmountOfQuestions.isEnabled = false
                        }
                        2 -> {
                            allOfQuestions = 9
                            binding.editTextAmountOfQuestions.isEnabled = false
                        }
                        3 -> {
                            allOfQuestions = 12
                            binding.editTextAmountOfQuestions.isEnabled = false
                        }
                        4 -> {
                            binding.editTextAmountOfQuestions.isEnabled = true
                            allOfQuestions = sharedPreferences.getInt(NUMBER_OF_QUESTIONS_PREFERENCES, 3)
                        }
                    }
                    binding.textViewNumOfQuestions.text = "Questions: $allOfQuestions"
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //nothing need to do
                }
            }
        binding.buttonSave.setOnClickListener {view: View ->
            if (binding.spinnerDifficulty.selectedItemPosition == 4) {
                if (!binding.editTextAmountOfQuestions.text.toString().isEmpty() && binding.editTextAmountOfQuestions.text.toString().toInt() > 0) {
                    allOfQuestions = binding.editTextAmountOfQuestions.text.toString().toInt()
                    saveSettings(allOfQuestions)
                    view.findNavController().navigate(R.id.action_settingsFragment_to_welcomeScreenFragment)
                }
            } else {
                saveSettings(allOfQuestions)
                view.findNavController().navigate(R.id.action_settingsFragment_to_welcomeScreenFragment)
            }
        }
    }

    private fun loadSettings(binding: FragmentSettingsBinding) {
        val numberOfSelectedItem: Int = sharedPreferences.getInt(NUMBER_OF_SELECTED_ITEM, 0)
        binding.spinnerDifficulty.setSelection(numberOfSelectedItem)                                //set selected item
         val allOfQuestions: Int = sharedPreferences.getInt(NUMBER_OF_QUESTIONS_PREFERENCES, 3)
        binding.textViewNumOfQuestions.text = "Questions: $allOfQuestions"
    }

    private fun saveSettings(numberOfSelectedItem: Int) {
        with(sharedPreferences.edit()) {
            putInt(NUMBER_OF_SELECTED_ITEM, numberOfSelectedItem)
            putInt(NUMBER_OF_QUESTIONS_PREFERENCES, allOfQuestions)
            apply()
        }
    }

//    private fun getTextAndValidateHim(binding: FragmentSettingsBinding, prevNumberOfQuestions: Int, maxLimit: Int): Int {
//        val newNumberOfQuestions: Int = prevNumberOfQuestions
//        val customNumberOfQuestions: Int = binding.editTextAmountOfQuestions.text.toString().toInt()
//        //if ()
//        return 2
//    }

    private fun getTextAndValidateHim(binding: FragmentSettingsBinding, prevNumberOfQuestions: Int, maxLimit: Int): Int {
        var localNumberOfQuestions: Int
        if (!binding.editTextAmountOfQuestions.text.isEmpty() && binding.editTextAmountOfQuestions.text.trim().toString().toInt() < 0) {
            if (binding.editTextAmountOfQuestions.text.trim().toString().toInt() >= maxLimit) {
                localNumberOfQuestions = maxLimit
            } else {
                localNumberOfQuestions = binding.editTextAmountOfQuestions.text.trim().toString().toInt()
            }
        } else {
            return prevNumberOfQuestions
        }
        return localNumberOfQuestions
    }
}


//    if (!binding.editTextAmountOfQuestions.text.isEmpty() || binding.editTextAmountOfQuestions.text.trim()
//    .toString().toInt() == 0
//    ) {
//        if (binding.editTextAmountOfQuestions.text.trim().toString()
//                .toInt() >= 50
//        ) {
//            numberOfQuestions = 50
//        } else {
//            numberOfQuestions =
//                binding.editTextAmountOfQuestions.text.trim().toString().toInt()
//        }
//    }
//}

//if (binding.spinnerDifficulty.selectedItemPosition == 4) {
//    allOfQuestions = getTextAndValidateHim(binding, allOfQuestions, 50)
//}
//binding.textViewNumOfQuestions.text = "Questions: $allOfQuestions"
//saveSettings(binding.spinnerDifficulty.selectedItemPosition)