package com.example.quiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    private val quizItems: MutableList<QuizItem> = mutableListOf(
        QuizItem(
            "1. How many players does each team have on the pitch when a soccer match starts?",
            listOf("11", "8", "12")
        ),
        QuizItem(
            "2. What should be the circumference of a Size 5 (adult) football?",
            listOf("27\" to 28\"", "24\" to 25\"", "23\" to 24\"")
        ),
        QuizItem(
            "3. What is given to a player for a very serious personal foul on an opponent?",
            listOf("Red Card", "Green Card", "Yellow Card")
        ),
        QuizItem(
            "4. To most places in the world, soccer is known as what?",
            listOf("Football", "Footgame", "Legball")
        ),
        QuizItem(
            "5. Offside. If a player is offside, what action does the referee take?",
            listOf(
                "Awards an indirect free kick to the opposing team",
                "Awards a penalty to the opposing team",
                "Awards a yellow card  to the player"
            )
        ),
        QuizItem(
            "6. How many laws of Association Football are there?",
            listOf("17", "11", "23")
        ),
        QuizItem(
            "7. Excluding the goalkeeper, what part of the body cannot touch the ball?",
            listOf("Arm", "Head", "Shoulder")
        ),
        QuizItem(
            "8. What is it called when a player, without the ball on the offensive team is behind the last defender, or fullback?",
            listOf("Offside", "Outside", "Field-side")
        ),
        QuizItem(
            "9. The Ball. The circumference of the ball should not be greater than what?",
            listOf("70", "80", "90")
        ),
        QuizItem(
            "10. How many minutes are played in a regular game (without injury time or extra time)?",
            listOf("90", "95", "100")
        ),
        QuizItem(
            "11. What statement describes a proper throw-in?",
            listOf(
                "Both hands must be on the ball behind the head, both feet must be on the ground",
                "Both hands must be on the ball behind the head",
                "Both feet must be on the ground"
            )
        ),
        QuizItem(
            "12. How big is a regulation official soccer goal?",
            listOf("2.44m high, 7.32m wide", "2.55m high, 7.62m wide", "2.33m high, 8.15m wide")
        ),
    )
    lateinit var currentQuizItem: QuizItem
    lateinit var answerList: MutableList<String>
    private var quizItemIndex: Int = 0
    private var curQuestion: Int = 0
    private var allOfQuestions = 3
    private lateinit var sharedPreferences: SharedPreferences
    private val NAME_PREFERENCES: String = "preferences"
    private val NUMBER_OF_QUESTIONS_PREFERENCES: String = "numberOfQuestions"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentQuizBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_quiz, container, false
        )
        loadSettings()
        getRandomQuizItem()
        binding.quizFragment = this
        binding.textViewScore.text = "Question: $curQuestion/$allOfQuestions"
        binding.buttonPass.setOnClickListener { view: View ->
            val selectedCheckBoxId = binding.radioGroupQuiz.checkedRadioButtonId
            if (selectedCheckBoxId != -1) {
                var answerIndex = 0
                when (selectedCheckBoxId) {
                    R.id.radioButtonFirst -> answerIndex = 0
                    R.id.radioButtonSecond -> answerIndex = 0
                    R.id.radioButtonThird -> answerIndex = 0
                }
                if (answerList[answerIndex] == currentQuizItem.getAnswerList()[0]) {
                    quizItemIndex++
                    curQuestion++
                    binding.textViewScore.text = "Question: $curQuestion/$allOfQuestions"
                    if (quizItemIndex < allOfQuestions) {
                        setQuizItem()
                        binding.invalidateAll()
                    } else {
                        val direction = QuizFragmentDirections.actionQuizFragmentToGoalFragment(curQuestion, allOfQuestions)
                        findNavController().navigate(direction)
                    }
                } else {
                    val direction = QuizFragmentDirections.actionQuizFragmentToMissFragment(curQuestion, allOfQuestions)
                    findNavController().navigate(direction)
                }
            }
        }
        return binding.root
    }

    private fun loadSettings() {
        sharedPreferences = activity?.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE) ?: return
        allOfQuestions = sharedPreferences.getInt(NUMBER_OF_QUESTIONS_PREFERENCES, 3)
    }

    private fun getRandomQuizItem() {
        quizItems.shuffle()
        quizItemIndex = 0
        setQuizItem()
    }

    private fun setQuizItem() {
        currentQuizItem = quizItems[quizItemIndex]
        answerList = currentQuizItem.getAnswerList().toMutableList()
        answerList.shuffle()
    }
}
