package com.popstack.mvoter2015.data.cache

import com.popstack.mvoter2015.data.cache.columnadapter.BallotExampleIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.CandidateParentColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.FaqIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.LocalDateColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.NewsIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.PartyIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.StringListColumnAdapter
import com.popstack.mvoter2015.data.cache.entity.BallotExampleTable
import com.popstack.mvoter2015.data.cache.entity.CandidateTable
import com.popstack.mvoter2015.data.cache.entity.ConstituencyTable
import com.popstack.mvoter2015.data.cache.entity.FaqTable
import com.popstack.mvoter2015.data.cache.entity.NewsTable
import com.popstack.mvoter2015.data.cache.entity.PartyTable
import com.squareup.sqldelight.EnumColumnAdapter

object TableAdapters {

  fun candidateTableAdapter(): CandidateTable.Adapter {
    return CandidateTable.Adapter(
      genderAdapter = EnumColumnAdapter(),
      birthDateAdapter = LocalDateColumnAdapter,
      partyIdAdapter = PartyIdColumnAdapter,
      fatherAdapter = CandidateParentColumnAdapter,
      motherAdapter = CandidateParentColumnAdapter,
    )
  }

  fun partyTableAdapter(): PartyTable.Adapter {
    return PartyTable.Adapter(
      leadersAndChairmenAdapter = StringListColumnAdapter(),
      contactsAdapter = StringListColumnAdapter(),
      establishmentApplicationDateAdapter = LocalDateColumnAdapter,
      establishmentApprovalDateAdapter = LocalDateColumnAdapter,
      registrationApplicationDateAdapter = LocalDateColumnAdapter,
      registrationApprovalDateAdapter = LocalDateColumnAdapter
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
      idAdapter = BallotExampleIdColumnAdapter,
      categoryAdapter = EnumColumnAdapter()
    )
  }

  fun newsTableAdapter(): NewsTable.Adapter {
    return NewsTable.Adapter(
      idAdapter = NewsIdColumnAdapter,
      publishedDateAdapter = LocalDateColumnAdapter
    )
  }

  fun ConsitutencyTableAdapter(): ConstituencyTable.Adapter {
    return ConstituencyTable.Adapter(
      houseAdapter = EnumColumnAdapter()
    )
  }

}