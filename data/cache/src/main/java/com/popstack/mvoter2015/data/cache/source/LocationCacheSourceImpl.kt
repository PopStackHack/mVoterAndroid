package com.popstack.mvoter2015.data.cache.source

import android.content.Context
import androidx.core.content.edit
import com.popstack.mvoter2015.data.common.location.LocationCacheSource
import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.model.StateRegionTownship
import com.popstack.mvoter2015.domain.location.model.Ward
import com.popstack.mvoter2015.domain.location.model.WardId
import org.json.JSONObject
import javax.inject.Inject

class LocationCacheSourceImpl @Inject constructor(
  context: Context
) : LocationCacheSource {

  companion object {
    private const val KEY_USER_WARD = "saved_user_ward"
    private const val KEY_USER_STATE_REGION_TOWNSHIP = "saved_user_state_region_township"

    private const val KEY_WARD_ID = "ward_id"
    private const val KEY_WARD_NAME = "ward_name"
    private const val KEY_LOWER_CONST_ID = "lower_const_id"
    private const val KEY_LOWER_CONST_NAME = "lower_const_name"
    private const val KEY_UPPER_CONST_ID = "upper_const_id"
    private const val KEY_UPPER_CONST_NAME = "upper_const_name"
    private const val KEY_STATE_CONST_ID = "state_const_id"
    private const val KEY_STATE_CONST_NAME = "sate_const_name"
    private const val KEY_STATE_REGION = "state_region"
    private const val KEY_TOWNSHIP = "township"
  }

  private val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

  override fun getUserWard(): Ward? {
    val wardJson = sharedPreferences.getString(KEY_USER_WARD, null) ?: return null
    return JSONObject(wardJson).toWard()
  }

  override fun saveUserWard(ward: Ward) {
    sharedPreferences.edit {
      putString(KEY_USER_WARD, JSONObject(ward.toMap()).toString())
    }
  }

  override fun getUserStateRegionTownship(): StateRegionTownship? {
    val stateRegionTownship = sharedPreferences.getString(KEY_USER_STATE_REGION_TOWNSHIP, null)
      ?: return null
    return JSONObject(stateRegionTownship).toStateRegionTownship()
  }

  override fun saveUserStateRegionTownship(stateRegionTownship: StateRegionTownship) {
    sharedPreferences.edit {
      putString(KEY_USER_STATE_REGION_TOWNSHIP, JSONObject(stateRegionTownship.toMap()).toString())
    }
  }

  private fun JSONObject.toWard(): Ward {
    return Ward(
      id = WardId(getString(KEY_WARD_ID)),
      name = getString(KEY_WARD_NAME),
      lowerHouseConstituency = Constituency(
        id = getString(KEY_LOWER_CONST_ID),
        name = getString(KEY_LOWER_CONST_NAME),
        house = HouseType.LOWER_HOUSE,
        null, null
      ),
      upperHouseConstituency = Constituency(
        id = getString(KEY_UPPER_CONST_ID),
        name = getString(KEY_UPPER_CONST_NAME),
        house = HouseType.UPPER_HOUSE,
        null, null
      ),
      stateRegionConstituency = Constituency(
        id = getString(KEY_STATE_CONST_ID),
        name = getString(KEY_STATE_CONST_NAME),
        house = HouseType.REGIONAL_HOUSE,
        null, null
      )
    )
  }

  private fun JSONObject.toStateRegionTownship(): StateRegionTownship {
    return StateRegionTownship(
      stateRegion = getString(KEY_STATE_REGION),
      township = getString(KEY_TOWNSHIP),
    )
  }

  private fun Ward.toMap(): Map<String, String> {
    return mapOf(
      KEY_WARD_ID to id.value,
      KEY_WARD_NAME to name,
      KEY_LOWER_CONST_ID to lowerHouseConstituency.id,
      KEY_LOWER_CONST_NAME to lowerHouseConstituency.name,
      KEY_UPPER_CONST_ID to upperHouseConstituency.id,
      KEY_UPPER_CONST_NAME to upperHouseConstituency.name,
      KEY_STATE_CONST_ID to stateRegionConstituency.id,
      KEY_STATE_CONST_NAME to stateRegionConstituency.name
    )
  }

  private fun StateRegionTownship.toMap(): Map<String, String> {
    return mapOf(
      KEY_STATE_REGION to stateRegion,
      KEY_TOWNSHIP to township
    )
  }


}