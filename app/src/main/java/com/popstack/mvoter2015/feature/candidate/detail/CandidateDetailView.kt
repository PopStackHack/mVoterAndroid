package com.popstack.mvoter2015.feature.candidate.detail

import com.popstack.mvoter2015.core.mvp.Viewable
import com.popstack.mvoter2015.domain.candidate.model.CandidateId

interface CandidateDetailView : Viewable {

  fun getCandidateId(): CandidateId

}