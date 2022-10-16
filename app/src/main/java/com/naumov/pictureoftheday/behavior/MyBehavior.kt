package com.naumov.pictureoftheday.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.get
import com.naumov.pictureoftheday.R

class MyBehavior(context: Context, attrs: AttributeSet?=null):CoordinatorLayout.Behavior<View>(context,attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency.id == R.id.constrain_behavior)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency.id == R.id.constrain_behavior){

            Log.d("@@@", "onDependentViewChanged: ${dependency.y} ${child.y}")
                child.y = dependency.y + dependency.height
        }
        return true
    }
}