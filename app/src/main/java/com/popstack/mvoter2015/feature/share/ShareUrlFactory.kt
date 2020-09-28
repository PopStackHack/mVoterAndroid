package com.popstack.mvoter2015.feature.share

import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.faq.model.FaqId
import com.popstack.mvoter2015.domain.party.model.PartyId

class ShareUrlFactory {

  companion object {
    private const val BASE_WEB_APP_URL = "https://web.mvoterapp.com"
  }

  fun candidate(candidateId: CandidateId): String {
    return "$BASE_WEB_APP_URL/candidates/${candidateId.value}"
  }

  fun party(partyId: PartyId): String {
    return "$BASE_WEB_APP_URL/parties/${partyId.value}"
  }

  fun faq(faqId: FaqId): String {
    return "$BASE_WEB_APP_URL/faqs/${faqId.value}"
  }

}