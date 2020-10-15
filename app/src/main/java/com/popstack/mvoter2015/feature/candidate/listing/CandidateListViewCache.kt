package com.popstack.mvoter2015.feature.candidate.listing

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CandidateListViewCache @Inject constructor(
  private val context: Context
) {

  companion object {
    private val KEY_SHOULD_SHOW_CANDIDATE_PRIVACY_INSTRUCTION = preferencesKey<Boolean>("candidate_privacy_instruction")
  }

  private val candidateListViewCachePrefCandidateListViewCachePref = context.createDataStore("candidate_list_view_cache")

  suspend fun setShouldShowCandidatePrivacyInstruction(shouldShow: Boolean) {
    candidateListViewCachePrefCandidateListViewCachePref.edit {
      it[KEY_SHOULD_SHOW_CANDIDATE_PRIVACY_INSTRUCTION] = shouldShow
    }
  }

  fun shouldShowCandidatePrivacyInstruction(): Flow<Boolean> {
    return candidateListViewCachePrefCandidateListViewCachePref.data.map {
      it[KEY_SHOULD_SHOW_CANDIDATE_PRIVACY_INSTRUCTION] ?: true
    }
  }
}