package com.popstack.mvoter2015.data.cache.source

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import com.popstack.mvoter2015.data.cache.ConstituencyProto
import com.popstack.mvoter2015.data.cache.ConstituencyProto.HouseTypeProto.TYPE_LOWER_HOUSE
import com.popstack.mvoter2015.data.cache.ConstituencyProto.HouseTypeProto.TYPE_STATE_REGION_HOUSE
import com.popstack.mvoter2015.data.cache.ConstituencyProto.HouseTypeProto.TYPE_UPPER_HOUSE
import com.popstack.mvoter2015.data.cache.StateTownshipProto
import com.popstack.mvoter2015.data.cache.WardProto
import com.popstack.mvoter2015.data.cache.source.location.StateTownshipSerializer
import com.popstack.mvoter2015.data.cache.source.location.WardSerializer
import com.popstack.mvoter2015.data.common.location.LocationCacheSource
import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType.LOWER_HOUSE
import com.popstack.mvoter2015.domain.constituency.model.HouseType.REGIONAL_HOUSE
import com.popstack.mvoter2015.domain.constituency.model.HouseType.UPPER_HOUSE
import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import com.popstack.mvoter2015.domain.location.model.Ward
import com.popstack.mvoter2015.domain.location.model.WardId
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LocationCacheSourceImpl @Inject constructor(
  context: Context
) : LocationCacheSource {

  companion object {
    private const val KEY_USER_STATE_REGION_TOWNSHIP = "saved_user_state_region_township"
    private const val KEY_STATE_REGION = "state_region"
    private const val KEY_TOWNSHIP = "township"
  }

  private val wardDataStore: DataStore<WardProto> = context.createDataStore(
    fileName = "ward_pref.pb",
    serializer = WardSerializer
  )

  private val stateRegionTownshipStore: DataStore<StateTownshipProto> = context.createDataStore(
    fileName = "state_region.pb",
    serializer = StateTownshipSerializer
  )

  override suspend fun getUserWard(): Ward? {
    try {
      return wardDataStore.data.first()
        .toWard()
    } catch (exception: Exception) {
      return null
    }
  }

  override suspend fun saveUserWard(ward: Ward) {
    wardDataStore.updateData { wardProto ->
      WardProto(
        id = ward.id.value,
        name = ward.name,
        lower_constituency = ward.lowerHouseConstituency.toConstituencyProto(),
        upper_constituency = ward.upperHouseConstituency.toConstituencyProto(),
        state_region_constituency = ward.stateRegionConstituency?.toConstituencyProto()
      )
    }
  }

  override suspend fun getUserStateRegionTownship(): StateRegionTownship? {
    try {
      return with(stateRegionTownshipStore.data.first()) {
        StateRegionTownship(
          stateRegion = this.state_region,
          township = this.township
        )
      }
    } catch (exception: Exception) {
      return null
    }
  }

  override suspend fun saveUserStateRegionTownship(stateRegionTownship: StateRegionTownship) {
    stateRegionTownshipStore.updateData { stateTownshipProto ->
      StateTownshipProto(
        state_region = stateRegionTownship.stateRegion,
        township = stateRegionTownship.township
      )
    }
  }

  private fun WardProto.toWard(): Ward {
    return Ward(
      id = WardId(this.id),
      name = this.name,
      lowerHouseConstituency = this.lower_constituency!!.toConstituency(),
      upperHouseConstituency = this.upper_constituency!!.toConstituency(),
      stateRegionConstituency = this.state_region_constituency?.toConstituency()
    )
  }

  private fun ConstituencyProto.toConstituency(): Constituency {
    return Constituency(
      id = ConstituencyId(this.id),
      name = this.name,
      house = when (this.houseType) {
        TYPE_LOWER_HOUSE -> LOWER_HOUSE
        TYPE_UPPER_HOUSE -> UPPER_HOUSE
        TYPE_STATE_REGION_HOUSE -> REGIONAL_HOUSE
      },
      remark = if (this.remark.isEmpty()) null else this.remark,
    )
  }

  private fun Constituency.toConstituencyProto(): ConstituencyProto {
    return ConstituencyProto(
      id = this.id.value,
      name = this.name,
      houseType = when (this.house) {
        LOWER_HOUSE -> TYPE_LOWER_HOUSE
        UPPER_HOUSE -> TYPE_UPPER_HOUSE
        REGIONAL_HOUSE -> TYPE_STATE_REGION_HOUSE
      },
      remark = this.remark.orEmpty()
    )
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun destroy() {
  }

}