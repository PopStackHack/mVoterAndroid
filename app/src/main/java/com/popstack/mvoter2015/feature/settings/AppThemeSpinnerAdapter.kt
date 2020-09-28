package com.popstack.mvoter2015.feature.settings

import android.content.Context
import androidx.core.content.ContextCompat
import com.aungkyawpaing.simplespinneradapter.SimpleSpinnerAdapter
import com.popstack.mvoter2015.R

class AppThemeSpinnerAdapter constructor(
  private val context: Context
) : SimpleSpinnerAdapter<String>(
  itemList = context.resources.getStringArray(R.array.settings_themes).toList()
) {

  override fun getDisplayString(position: Int, context: Context): String {
    return getItem(position)
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun modifyHolder(holder: ViewHolder) {
    super.modifyHolder(holder)
    holder.binding.tvSpinnerItem.setTextColor(ContextCompat.getColor(holder.binding.tvSpinnerItem.context, R.color.text_primary))
  }

}