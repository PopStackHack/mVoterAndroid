package com.popstack.mvoter2015.data.android.paging

import android.content.Context
import com.popstack.mvoter2015.data.android.MVoterRemoteKeyDb
import com.popstack.mvoter2015.data.cache.entity.PartyRemoteKeyTable
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver

object RemoteKeyDbProvider {

  private var _instance: MVoterRemoteKeyDb? = null

  fun INSTANCE(context: Context): MVoterRemoteKeyDb {
    if (_instance == null) {
      _instance = MVoterRemoteKeyDb(
        driver = AndroidSqliteDriver(MVoterRemoteKeyDb.Schema, context, "mvoter_remote_key.db"),
        PartyRemoteKeyTableAdapter = PartyRemoteKeyTable.Adapter(
          partyIdAdapter = object : ColumnAdapter<PartyId, String> {
            override fun decode(databaseValue: String): PartyId {
              return PartyId(databaseValue)
            }

            override fun encode(value: PartyId): String {
              return value.value
            }
          }
        )
      )
    }
    return _instance!!
  }

}