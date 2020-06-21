package com.aungkyawpaing.simplespinneradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.aungkyawpaing.simplespinneradapter.databinding.SpinnerRowBinding

abstract class SimpleSpinnerAdapter<T>(
  private var itemList: List<T> = listOf()
) : BaseAdapter() {

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    return getCustomView(position, convertView, parent!!)
  }

  override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
    return getCustomView(position, convertView, parent!!)
  }

  override fun getItem(position: Int): T {
    return itemList[position]
  }

  override fun getCount(): Int {
    return itemList.size
  }

  fun submitList(newList: List<T>) {
    this.itemList = newList
    notifyDataSetChanged()
  }

  protected open fun getCustomView(
    position: Int,
    convertView: View?,
    parent: ViewGroup
  ): View {
    var convertView1 = convertView
    val holder: ViewHolder
    if (convertView == null) {
      val binding = SpinnerRowBinding.inflate(LayoutInflater.from(parent.context), null, false)
      convertView1 = binding.root
      holder = ViewHolder(binding)
      convertView1.tag = holder
    } else {
      holder = convertView1!!.tag as ViewHolder
    }

    holder.binding.tvSpinnerItem.text = getDisplayString(position, convertView1!!.context)
    modifyHolder(holder)
    return convertView1
  }

  protected open fun modifyHolder(holder: ViewHolder) {
    //Do Nothing
  }

  protected abstract fun getDisplayString(
    position: Int,
    context: Context
  ): String

  protected class ViewHolder(
    val binding: SpinnerRowBinding
  )

  fun getPositionOfId(id: Long): Int {
    for (position in 0..count) {
      if (getItemId(position) == id) {
        return position
      }
    }
    return -1
  }

}