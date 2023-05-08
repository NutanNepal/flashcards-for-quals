package com.example.flashcardsapp.ui.topics

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.cardview.widget.CardView
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.subjects.ActivityFlashcardsSubjects

class ActivityFlashcardsTopics : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards_topics)
        val cardView: CardView = findViewById<CardView>(R.id.card_view_topics)
        val webView: WebView = cardView.findViewById<WebView>(R.id.web_view_topics)
        webView.settings.javaScriptEnabled=true
        webView.settings.domStorageEnabled=true
        // set the MathJax configuration options
        val mathJaxUrl = "file:///android_asset/mathjax/es5/tex-chtml.js"
        val mathJaxConfig = """
        MathJax.Hub.Config({
        extensions: ["tex2jax.js"],
        jax: ["input/TeX", "output/CommonHTML"],
        tex2jax: {
            inlineMath: [['$', '$'], ['\\(', '\\)']],
            displayMath: [['$$', '$$'], ['\\[', '\\]']],
            processEscapes: true
        },
        showMathMenu: false,
        messageStyle: "none"
        });
        """

        // wrap the math expression in a math tag
        val mathExpression = "<math>" +
                "\n" +
                "        <h1>MathJax Example</h1>\n" +
                "\t    <p>Here is some inline LaTeX: \\(x^2 + y^2 = z^2\\)</p>\n" +
                "\t    <p>And here is some displayed LaTeX:</p>\n" +
                "\t    \\[ \\int_{-\\infty}^{\\infty} e^{-x^2} dx = \\sqrt{\\pi} \\]" +
                "</math>"

        // build the HTML content with the MathJax configuration and math expression
        val htmlContent = """
        <html><head><style>body {
                background-color: #001e2c;
                color: #ffffff;
            }
        </style>
            <script type='text/javascript' src='$mathJaxUrl'></script>
            <script type='text/x-mathjax-config'>$mathJaxConfig</script>
        </head>
        <body>$mathExpression</body>
        </html>
        """

        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null)
    }

    companion object {

        fun newIntent(context: Context, listItem: String): Intent {
            return Intent(context, ActivityFlashcardsTopics::class.java)
        }
    }
}