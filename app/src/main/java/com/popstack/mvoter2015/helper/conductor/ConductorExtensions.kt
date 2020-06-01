package com.popstack.mvoter2015.helper.conductor

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bluelinelabs.conductor.Controller

fun Controller.requireContext(): Context {
  return applicationContext!!
}

fun Controller.requireActivity(): Activity {
  return activity!!
}

fun Controller.requireActivityAsAppCompatActivity(): AppCompatActivity {
  return requireActivity() as AppCompatActivity
}

fun Controller.setSupportActionBar(toolbar: Toolbar) {
  requireActivityAsAppCompatActivity().setSupportActionBar(toolbar)
}

fun Controller.supportActionBar(): ActionBar? {
  return requireActivityAsAppCompatActivity().supportActionBar
}