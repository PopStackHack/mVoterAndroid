package com.popstack.mvoter2015.helper

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.TabConstituencyBinding

class ConstituencyTab @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
  LinearLayout(context, attrs, defStyleAttr) {

  private val binding: TabConstituencyBinding

  init {
    val view = View.inflate(context, R.layout.tab_constituency, this)
    binding = TabConstituencyBinding.bind(view)
    //view.findViewById<TextView>(R.id.tvConstituencyType).text = constituencyType
  }

  fun setText(text: String) {
    binding.tvConstituencyType.text = text
  }

  fun setSelected() {
    val color = ContextCompat.getColor(context, R.color.accent)
    binding.tvConstituencyType.setTextColor(color)
    binding.tvHouse.setTextColor(color)
  }

  fun setUnselected() {
    val color = ContextCompat.getColor(context, R.color.text_primary)
    binding.tvConstituencyType.setTextColor(color)
    binding.tvHouse.setTextColor(color)
  }

}