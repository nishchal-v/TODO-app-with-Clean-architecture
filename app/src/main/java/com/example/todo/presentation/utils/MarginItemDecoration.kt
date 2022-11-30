package com.example.todo.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val verticalMargin: Int,
    private val horizontalMargin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            top = verticalMargin
            left = horizontalMargin
            right = verticalMargin
            bottom = horizontalMargin
        }
    }
}