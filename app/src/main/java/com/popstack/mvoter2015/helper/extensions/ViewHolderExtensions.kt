package com.popstack.mvoter2015.helper.extensions

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Created by Vincent on 2/13/20
 */
fun ViewHolder.withSafeAdapterPosition(
  onUnsafePosition: ((Unit) -> (Unit)) = { },
  function: ((@ParameterName("position") Int) -> (Unit))
) {
  val position = adapterPosition
  if (position != RecyclerView.NO_POSITION) {
    function.invoke(position)
  }
}