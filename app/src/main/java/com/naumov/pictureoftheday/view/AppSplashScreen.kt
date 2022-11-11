package com.naumov.pictureoftheday.view

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import com.naumov.pictureoftheday.ui.MainActivity
import com.naumov.pictureoftheday.utils.SPLASH_COUNTDOWN_INTERVAL
import com.naumov.pictureoftheday.utils.SPLASH_MILLI_FUTURE

class AppSplashScreen {

    companion object {
        @RequiresApi(Build.VERSION_CODES.S)
        @JvmStatic
        fun startSplashScreen(context: Context, view: View) {
            startScreen(context, view)
        }

        @RequiresApi(Build.VERSION_CODES.S)
        private fun startScreen(context: Context, content: View) {

//           val content: View = findViewById(android.R.id.content)
            var isHideSplashScreen = false

            object : CountDownTimer(SPLASH_MILLI_FUTURE, SPLASH_COUNTDOWN_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    //Nothing to do
                }

                override fun onFinish() {
                    isHideSplashScreen = true
                }
            }.start()

            content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            })
            (context as MainActivity).splashScreen.setOnExitAnimationListener { splachscreenView->
                val slideLeft = ObjectAnimator.ofFloat(
                    splachscreenView,
                    View.TRANSLATION_X,
                    0f,
                    -splachscreenView.height.toFloat()
                )

                slideLeft.interpolator = AnticipateInterpolator()
                slideLeft.duration = 1000L

                slideLeft.doOnEnd { splachscreenView.remove() }
                slideLeft.start()
            }

        }
    }

}