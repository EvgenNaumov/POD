package com.naumov.pictureoftheday.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout

class NestedScrollBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<NestedScrollView>(context, attrs) {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {

        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        if (dependency is AppBarLayout) {
            child.y = dependency.y + dependency.height
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}