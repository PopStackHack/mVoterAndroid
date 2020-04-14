package com.popstack.mvoter2015.helper

import android.graphics.Rect
import android.view.View
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State

//Copied and modified from https://stackoverflow.com/a/38984354/3125020

class RecyclerViewMarginDecoration
/**
 * constructor
 * @param margin desirable margin size in px between the views in the recyclerView
 * @param columns number of columns of the RecyclerView
 */(
  @IntRange(from = 0) private val margin: Int,
  @IntRange(from = 0) private val columns: Int
) :
    RecyclerView.ItemDecoration() {

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: State
  ) {
    val position: Int = parent.getChildLayoutPosition(view)
    //set right margin to all
    outRect.right = margin
    //set bottom margin to all
    outRect.bottom = margin
    //we only add top margin to the first row
    if (position < columns) {
      outRect.top = margin
    }
    //add left margin only to the first column
    if (position % columns == 0) {
      outRect.left = margin
    }

  }

}