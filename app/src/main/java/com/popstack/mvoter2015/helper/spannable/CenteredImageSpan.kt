package com.popstack.mvoter2015.helper.spannable

/**
 * Source: https://stackoverflow.com/a/29251717
 * @author misaka-10032
 */

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import java.lang.ref.WeakReference

class CenteredImageSpan(context: Context, drawableRes: Int) : ImageSpan(context, drawableRes) {
  private var mDrawableRef: WeakReference<Drawable>? = null
  override fun getSize(paint: Paint, text: CharSequence?,
                       start: Int, end: Int,
                       fm: Paint.FontMetricsInt?): Int {
    val d: Drawable = cachedDrawable
    val rect: Rect = d.bounds
    if (fm != null) {
      val pfm: Paint.FontMetricsInt = paint.fontMetricsInt
      // keep it the same as paint's fm
      fm.ascent = pfm.ascent
      fm.descent = pfm.descent
      fm.top = pfm.top
      fm.bottom = pfm.bottom
    }
    return rect.right
  }

  override fun draw(canvas: Canvas, text: CharSequence?,
                    start: Int, end: Int, x: Float,
                    top: Int, y: Int, bottom: Int, paint: Paint) {
    val b: Drawable = cachedDrawable
    canvas.save()
    val drawableHeight: Int = b.intrinsicHeight
    val fontAscent: Int = paint.fontMetricsInt.ascent
    val fontDescent: Int = paint.fontMetricsInt.descent
    val transY: Int = bottom - b.bounds.bottom +  // align bottom to bottom
      (drawableHeight - fontDescent + fontAscent) / 2 // align center to center
    canvas.translate(x, transY.toFloat())
    b.draw(canvas)
    canvas.restore()
  }

  // Redefined locally because it is a private member from DynamicDrawableSpan
  private val cachedDrawable: Drawable
    get() {
      val wr: WeakReference<Drawable>? = mDrawableRef
      var d: Drawable? = null
      if (wr != null) d = wr.get()
      if (d == null) {
        d = drawable
        mDrawableRef = WeakReference(d)
      }
      return d!!
    }
}