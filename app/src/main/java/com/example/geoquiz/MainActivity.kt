package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_b, true),
        Question(R.string.question_ac, false),
        Question(R.string.question_d, false),
        Question(R.string.question_e, true)
    )

    private var currentIndex = 0
    private var currentScore = 0
    private var locked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            if (locked == false){
                checkAnswer(true)
            }
        }

        falseButton.setOnClickListener { view: View ->
            if (locked == false){
                checkAnswer(false)
            }
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            if (currentIndex < 0) {
                currentIndex = currentIndex + questionBank.size
            }
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        locked = false
        if (currentIndex == 4) {
            calculateScore()
        }
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        locked = true
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        if (userAnswer == correctAnswer) {
            currentScore += 1
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun calculateScore() {
        val score = (currentScore).toString()
        Toast.makeText(this, score, Toast.LENGTH_LONG).show()
    }
}
