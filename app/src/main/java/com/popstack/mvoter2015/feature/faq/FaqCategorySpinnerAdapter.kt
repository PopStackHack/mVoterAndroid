package com.popstack.mvoter2015.feature.faq

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.aungkyawpaing.simplespinneradapter.SimpleSpinnerAdapter
import com.popstack.mvoter2015.domain.faq.model.FaqCategory

class FaqCategorySpinnerAdapter : SimpleSpinnerAdapter<FaqCategory>(
  itemList = FaqCategory.values().toList()
) {

  override fun getDisplayString(position: Int, context: Context): String {
    return when (getItem(position)) {
      FaqCategory.GENERAL -> {
        "အထွေထွေ"
      }
      FaqCategory.KNOWLEDGE -> {
        "အသိပညာ"
      }
    }
  }

  override fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
    return super.getCustomView(position, convertView, parent)
  }

  override fun getItemId(position: Int): Long {
    return getItem(position).ordinal.toLong()
  }

}