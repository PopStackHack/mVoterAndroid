package com.popstack.mvoter2015.data.cache

import com.popstack.mvoter2015.data.cache.columnadapter.CandidateIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.LocalDateColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.PartyIdColumnAdapter
import com.popstack.mvoter2015.data.cache.columnadapter.StringListColumnAdapter
import com.popstack.mvoter2015.data.cache.entity.CandidateTable
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

  fun partyTableAdapter(): PartyTable.Adapter {
    return PartyTable.Adapter(
      idAdapter = PartyIdColumnAdapter,
      chairmansAdapter = StringListColumnAdapter,
      leadersAdapter = StringListColumnAdapter,
      registrationApplicationDateAdapter = LocalDateColumnAdapter,
      establishmentApprovalDateAdapter = LocalDateColumnAdapter,
      registrationApprovalDateAdapter = LocalDateColumnAdapter
    )

  }
}