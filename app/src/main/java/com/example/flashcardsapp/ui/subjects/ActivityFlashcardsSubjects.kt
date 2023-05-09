package com.example.flashcardsapp.ui.subjects

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Xml
import android.view.HapticFeedbackConstants
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.flashcardsapp.Flashcard
import com.example.flashcardsapp.R
import org.xmlpull.v1.XmlPullParser

class ActivityFlashcardsSubjects : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards_subjects)
        val cardView = findViewById<CardView>(R.id.card_view)
        val fileName = intent.getStringExtra(EXTRA_FILE_NAME)
        val inputStream = fileName?.let { assets.open(it) }
        // Create a new instance of the XmlPullParser interface
        val parser: XmlPullParser = Xml.newPullParser()
        // Set the input stream for the parser to the opened file
        parser.setInput(inputStream, null)
        // Call a method to parse the XML file and get the questions and answers
        var flashcards = parseXml(parser).shuffled()

        val textview = cardView.findViewById<TextView>(R.id.item_question)
        var index: Int = 0
        textview.text = flashcards[index].question
        cardView.setOnClickListener{
            if (textview.text == flashcards[index].question) {
                textview.text = flashcards[index].answer
                if (index==flashcards.size-1){
                    flashcards=flashcards.shuffled()
                    cardView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)

                }
            } else {
                index = (index + 1) % flashcards.size
                textview.text = flashcards[index].question
            }
        }
    }

    private fun parseXml(parser: XmlPullParser): MutableList<Flashcard> {
        val flashcards = mutableListOf<Flashcard>()
        var eventType = parser.eventType
        var currentFlashcard: Flashcard? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "flashcard" -> {
                            // Create a new Flashcard object when a <flashcard> tag is encountered
                            currentFlashcard = Flashcard("", "")
                        }
                        "question" -> {
                            // Set the question for the current Flashcard object when a <question> tag is encountered
                            currentFlashcard?.question = parser.nextText()
                        }
                        "answer" -> {
                            // Set the answer for the current Flashcard object when an <answer> tag is encountered
                            currentFlashcard?.answer = parser.nextText()
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "flashcard") {
                        // Add the current Flashcard object to the list when a </flashcard> tag is encountered
                        currentFlashcard?.let { flashcards.add(it) }
                        currentFlashcard = null
                    }
                }
            }
            eventType = parser.next()
        }
        return flashcards
    }

    companion object {
        private const val EXTRA_FILE_NAME = "FILE_NAME"

        fun newIntent(context: Context, listItem: String): Intent {
            return Intent(context, ActivityFlashcardsSubjects::class.java).putExtra(EXTRA_FILE_NAME,listItem)
        }
    }
}
