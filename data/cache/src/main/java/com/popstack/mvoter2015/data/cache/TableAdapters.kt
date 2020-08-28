package com.popstack.mvoter2015.data.cache

import com.popstack.mvoter2015.data.cache.columnadapter.BallotExampleIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.CandidateIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.FaqIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.LocalDateColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.NewsIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.PartyIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.StringListColumnAdapter
import com.popstack.mvoter2015.data.cache.entity.BallotExampleTable
import com.popstack.mvoter2015.data.cache.entity.CandidateFtsTable
import com.popstack.mvoter2015.data.cache.entity.CandidateTable
import com.popstack.mvoter2015.data.cache.entity.FaqTable
import com.popstack.mvoter2015.data.cache.entity.NewsTable
import com.popstack.mvoter2015.data.cache.entity.PartyFtsTable
import com.popstack.mvoter2015.data.cache.entity.PartyTable
import com.squareup.sqldelight.EnumColumnAdapter

object TableAdapters {

  fun candidateTableAdapter(): CandidateTable.Adapter {
    return CandidateTable.Adapter(
      idAdapter = CandidateIdColumnAdapter,
      genderAdapter = EnumColumnAdapter(),
      birthDateAdapter = LocalDateColumnAdapter,
      partyAdapter = PartyIdColumnAdapter
    )
  }

  fun candidateFtsTableAdapter(): CandidateFtsTable.Adapter {
    return CandidateFtsTable.Adapter(
      idAdapter = CandidateIdColumnAdapter
    )
  }

  fun partyTableAdapter(): PartyTable.Adapter {
    return PartyTable.Adapter(
      idAdapter = PartyIdColumnAdapter,
      leadersAndChairmenAdapter = StringListColumnAdapter,
      contactsAdapter = StringListColumnAdapter,
      establishmentApplicationDateAdapter = LocalDateColumnAdapter,
      establishmentApprovalDateAdapter = LocalDateColumnAdapter,
      registrationApplicationDateAdapter = LocalDateColumnAdapter,
      registrationApprovalDateAdapter = LocalDateColumnAdapter
    )
  }

  fun partyFtsTableAdapter(): PartyFtsTable.Adapter {
    return PartyFtsTable.Adapter(
      idAdapter = PartyIdColumnAdapter
    )
  }

  fun faqTableAdapter(): FaqTable.Adapter {
    return FaqTable.Adapter(
      idAdapter = FaqIdColumnAdapter,
      categoryAdapter = EnumColumnAdapter()
    )
  }

  fun ballotExampleTableAdapter(): BallotExampleTable.Adapter {
    return BallotExampleTable.Adapter(
      idAdapter = BallotExampleIdColumnAdapter
    )
  }

  fun newsTableAdapter(): NewsTable.Adapter {
    return NewsTable.Adapter(
      idAdapter = NewsIdColumnAdapter,
      publishedDateAdapter = LocalDateColumnAdapter
    )
  }

}