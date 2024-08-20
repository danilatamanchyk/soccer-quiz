package com.example.quiz

class QuizItem {
    private var question: String
    private var answerList: List<String>

    constructor(question: String, answer: List<String>) {
        this.question = question
        this.answerList = answer
    }

    fun getAnswerList(): List<String> {
        return answerList
    }

    fun getQuestion(): String {
        return question
    }

}