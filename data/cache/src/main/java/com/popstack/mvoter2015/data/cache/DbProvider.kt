package com.popstack.mvoter2015.data.cache

import com.squareup.sqldelight.db.SqlDriver

object DbProvider {

  fun create(driver: SqlDriver): MVoterDb {
    return MVoterDb(
      driver = driver,
      CandidateTableAdapter = TableAdapters.candidateTableAdapter(),
      PartyTableAdapter = TableAdapters.partyTableAdapter()
    )
  }
}