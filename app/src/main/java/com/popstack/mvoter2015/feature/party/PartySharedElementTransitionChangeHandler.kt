package com.popstack.mvoter2015.feature.party

import android.view.View
import android.view.ViewGroup
import androidx.transition.ChangeBounds
import androidx.transition.ChangeClipBounds
import androidx.transition.ChangeTransform
import androidx.transition.Transition
import androidx.transition.TransitionSet
import com.bluelinelabs.conductor.changehandler.androidxtransition.SharedElementTransitionChangeHandler
import com.popstack.mvoter2015.R

class PartySharedElementTransitionChangeHandler : SharedElementTransitionChangeHandler() {

  override fun configureSharedElements(container: ViewGroup, from: View?, to: View?, isPush: Boolean) {
    addSharedElement(container.context.getString(R.string.transition_name_party_name))
    addSharedElement(container.context.getString(R.string.transition_name_party_seal))
  }

  override fun getExitTransition(container: ViewGroup, from: View?, to: View?, isPush: Boolean): Transition? {
//    return Slide(Gravity.START)
    return null
  }

  override fun getSharedElementTransition(container: ViewGroup, from: View?, to: View?, isPush: Boolean): Transition? {
    return TransitionSet().addTransition(ChangeBounds()).addTransition(ChangeClipBounds()).addTransition(ChangeTransform())
  }

  override fun getEnterTransition(container: ViewGroup, from: View?, to: View?, isPush: Boolean): Transition? {
    return null
  }

}