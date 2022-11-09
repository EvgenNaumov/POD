package com.naumov.pictureoftheday.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.naumov.pictureoftheday.R

class FormatTextSpannable {

    private fun getListIdRainbowColor(indColor: Int) =
        when (indColor) {
            1 -> {
                Color.RED
            }
            2 -> {
                Color.BLUE
            }
            3 -> {
                Color.YELLOW
            }
            4 -> {
                Color.GREEN
            }
            else -> {
                Color.RED
            }
        }

    fun setСolorTextExplanation(spannebleText: Spannable) {

        val charArray = spannebleText.toString().toCharArray()


        if (charArray.isNotEmpty()) {
            var indChar = 0
            var i = 0
            charArray.forEach {
                if (i > 4) {
                    i = 0
                }

                if (!it.isWhitespace()) {
                    spannebleText.setSpan(
                        ForegroundColorSpan(getListIdRainbowColor(i.coerceAtMost(4))),
                        indChar.coerceAtMost(charArray.size - 1),
                        indChar.coerceAtMost(charArray.size - 1) + 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                }
                indChar++
                i++
            }
        }


    }

    fun setSpanneble(spanText: String, textView: TextView): Spannable {
        val spannebleString =
            SpannableString(spanText)
        textView.setText(
            spannebleString,
            TextView.BufferType.SPANNABLE
        )
        return textView.text as Spannable

    }

    fun setTextAsHyperReference(spannebleText: Spannable, hyperRef: String? = "") {

        val text = spannebleText.toString();

        val re = "(\\b(https?|ftp|file)://)?(www.)?([A-Za-z0-9+&@#/%?=~_|!:,.;](.(a-z){3,5}))".toRegex()
        val urls = re.findAll(text).toList()
        if (urls.isNotEmpty()){
            println("http range ${urls[0].range}")
        }


    }

    fun setBulletSpan(context: Context, spannebleText: Spannable) {

        val text = spannebleText.toString()
        val listSrlit = text.split("\n").toMutableList()
        var countLine = 0;
        listSrlit.forEach {
            val startPos = countLine
            val endPos = countLine++
            spannebleText.setSpan(
                BulletSpan(4, ContextCompat.getColor(context, R.color.red)),
                startPos,
                endPos,
                0
            )
            countLine += it.length.coerceAtMost(text.length - 1)
        }

    }

    fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> =
        (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)).findAll(this)
            .map { it.range.first }.toList()

}