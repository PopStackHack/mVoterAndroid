package com.popstack.mvoter2015.data.cache

import com.squareup.sqldelight.db.SqlDriver

object DbProvider {

  fun create(driver: SqlDriver): MVoterDb {
    return MVoterDb(
      driver = driver,
      CandidateTableAdapter = TableAdapters.candidateTableAdapter(),
      CandidateFtsTableAdapter = TableAdapters.candidateFtsTableAdapter(),
      PartyTableAdapter = TableAdapters.partyTableAdapter(),
      PartyFtsTableAdapter = TableAdapters.partyFtsTableAdapter(),
      FaqTableAdapter = TableAdapters.faqTableAdapter(),
      NewsTableAdapter = TableAdapters.newsTableAdapter(),
      BallotExampleTableAdapter = TableAdapters.ballotExampleTableAdapter()
    )
  }
}