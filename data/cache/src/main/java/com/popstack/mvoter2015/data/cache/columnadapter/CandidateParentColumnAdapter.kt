package com.popstack.mvoter2015.data.cache.columnadapter

import com.popstack.mvoter2015.domain.candidate.model.CandidateParent
import com.squareup.sqldelight.ColumnAdapter
import org.json.JSONObject

object CandidateParentColumnAdapter : ColumnAdapter<CandidateParent, String> {

  private const val FIELD_NAME = "name"
  private const val FIELD_RELIGION = "religion"
  private const val FIELD_ETHNICITY = "ethnicity"

  override fun decode(databaseValue: String): CandidateParent {
    val jsonObject = JSONObject(databaseValue)
    return CandidateParent(
      name = jsonObject.getString(FIELD_NAME),
      religion = jsonObject.getString(FIELD_RELIGION),
      ethnicity = jsonObject.getString(FIELD_ETHNICITY),
    )
  }

  override fun encode(value: CandidateParent): String {
    val jsonObject = JSONObject()
    jsonObject.put(FIELD_NAME, value.name)
    jsonObject.put(FIELD_RELIGION, value.religion)
    jsonObject.put(FIELD_ETHNICITY, value.ethnicity)
    return jsonObject.toString()
  }

}