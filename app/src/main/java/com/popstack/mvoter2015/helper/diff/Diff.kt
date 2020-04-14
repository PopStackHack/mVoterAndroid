package com.popstack.mvoter2015.helper.diff

import androidx.recyclerview.widget.DiffUtil

inline fun <T> diffCallBackWith(
  crossinline areItemTheSame: ((@ParameterName("item1") T, @ParameterName("item2") T) -> Boolean),
  crossinline areContentsTheSame: ((@ParameterName("item1") T, @ParameterName("item2") T) -> Boolean)
): DiffUtil.ItemCallback<T> {
  return object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
      return areItemTheSame.invoke(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
      return areContentsTheSame.invoke(oldItem, newItem)
    }
  }

}
