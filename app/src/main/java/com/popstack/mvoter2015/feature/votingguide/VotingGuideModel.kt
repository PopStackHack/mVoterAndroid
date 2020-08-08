package com.popstack.mvoter2015.feature.votingguide

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class VotingGuideModel @ViewModelInject constructor() : ViewModel() {

  fun constructViewItems(
    sectionTitles: List<String>,
    steps: List<Array<String>>
  ): List<VotingGuideViewItem> = ArrayList<VotingGuideViewItem>().apply {
    add(Header)
    sectionTitles.forEachIndexed { index, value ->
      add(SectionTitle(value))
      steps[index].forEachIndexed { stepIndex, step ->
        add(Step(step, stepIndex != 0, stepIndex != steps[index].size - 1))
      }
    }
  }
}