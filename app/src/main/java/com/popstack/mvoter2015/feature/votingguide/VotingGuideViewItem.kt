package com.popstack.mvoter2015.feature.votingguide

sealed class VotingGuideViewItem

object Header : VotingGuideViewItem()

object CheckVoterList : VotingGuideViewItem()

class SectionTitle(val text: String) : VotingGuideViewItem()

class Step(
  val text: String,
  val shouldShowUpperLine: Boolean,
  val shouldShowLowerLine: Boolean
) : VotingGuideViewItem()