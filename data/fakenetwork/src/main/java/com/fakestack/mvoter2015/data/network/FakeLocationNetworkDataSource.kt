package com.fakestack.mvoter2015.data.network

import com.popstack.mvoter2015.data.common.location.LocationNetworkSource
import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.domain.location.model.Township
import com.popstack.mvoter2015.domain.location.model.TownshipPCode
import com.popstack.mvoter2015.domain.location.model.Ward
import com.popstack.mvoter2015.domain.location.model.WardId
import javax.inject.Inject

class FakeLocationNetworkDataSource @Inject constructor() : LocationNetworkSource {

  private val fakeStateRegions = listOf(
    "ကချင်ပြည်နယ်",
    "ကယားပြည်နယ်",
    "ကရင်ပြည်နယ်",
    "ချင်းပြည်နယ်",
    "စစ်ကိုင်းတိုင်းဒေသကြီး",
    "တနင်္သာရီတိုင်းဒေသကြီး",
    "နေပြည်တော်ပြည်ထောင်စုနယ်မြေ",
    "ပဲခူးတိုင်းဒေသကြီး",
    "မကွေးတိုင်းဒေသကြီး",
    "မန္တလေးတိုင်းဒေသကြီး",
    "မွန်ပြည်နယ်",
    "ရခိုင်ပြည်နယ်",
    "ရန်ကုန်တိုင်းဒေသကြီး",
    "ရှမ်းပြည်နယ်",
    "ဧရာဝတီတိုင်းဒေသကြီး"
  )

  override fun getStateRegionList(): List<String> {
    return fakeStateRegions
  }

  private val fakeTownships = listOf(
    "ကော့ကရိတ်မြို့နယ်",
    "ကြာအင်းဆိပ်ကြီးမြို့နယ်",
    "ဖာပွန်မြို့နယ်",
    "ဘားအံမြို့နယ်",
    "မြဝတီမြို့နယ်",
    "လှိုင်းဘွဲ့မြို့နယ်",
    "သံတောင်ကြီးမြို့နယ်"
  )

  override fun getTownshipsListForStateRegion(stateRegionIdentifier: String): List<Township> {
    return fakeTownships.map {
      Township(
        pCode = TownshipPCode(it),
        name = it
      )
    }
  }

  private val fakeWard = listOf(
    "ကညင်ကတိုက်ကျေးရွာအုပ်စု",
    "ကမ်းနီကျေးရွာအုပ်စု",
    "ကရင်ကျောက်ဖျာကျေးရွာအုပ်စု",
    "ကုလားကျောက်ဖျာကျေးရွာအုပ်စု",
    "ကော့ဂိုးကျေးရွာအုပ်စု",
    "ကော့နွဲကျေးရွာအုပ်စု",
    "ကော့ပေါက်ကျေးရွာအုပ်စု",
    "ကော့ဘိန်းကျေးရွာအုပ်စု",
    "ကော့လျှမ်းကျေးရွာအုပ်စု",
    "ကော့ဝါလဲကျေးရွာအုပ်စု",
    "ကျုံဒိုးချောင်းဖျားကျေးရွာအုပ်စု",
    "ကျုံဘိုင်းကျေးရွာအုပ်စု",
    "ခနိမ်းဖော်ကျေးရွာအုပ်စု",
    "ခရင်းကျေးရွာအုပ်စု",
    "ခရစ်ကျောက်တန်းကျေးရွာအုပ်စု",
    "ချောင်းတောင်ရပ်ကွက်(ကော့ကရိတ်မြို့)",
    "စက္ကဝက်ကျေးရွာအုပ်စု",
    "တတံကူးကျေးရွာအုပ်စု",
    "တရိတခေါင်းကျေးရွာအုပ်စု",
    "တောင်ကမ္မရိုက်ကျေးရွာအုပ်စု",
    "တောင်ကြာအင်းကျေးရွာအုပ်စု",
    "တံတားဦးရပ်ကွက်(ကော့ကရိတ်မြို့)",
    "ထိဖိုးဇံကျေးရွာအုပ်စု",
    "ထီကလေးကျေးရွာအုပ်စု",
    "ထီဟူးသံကျေးရွာအုပ်စု",
    "ဒေါက်ပလန်ကျေးရွာအုပ်စု",
    "ဒေါဖျာကျေးရွာအုပ်စု",
    "နဘူးတံခွန်တိုင်ကျေးရွာအုပ်စု",
    "နဘူးနှစ်ချားကျေးရွာအုပ်စု",
    "နောင်ကိုင်းကျေးရွာအုပ်စု",
    "နောင်တပွဲကျေးရွာအုပ်စု",
    "နောင်ထပ္ပံကျေးရွာအုပ်စု",
    "ပိုင်ရပ်ကျေးရွာအုပ်စု",
    "ဖါးဘုတ်ကျေးရွာအုပ်စု",
    "မင်းရွာကျေးရွာအုပ်စု",
    "မိကလုံကျေးရွာအုပ်စု",
    "မြပတိုင်ကျေးရွာအုပ်စု",
    "မြောက်ကမ္မရိုက်ကျေးရွာအုပ်စု",
    "မြောက်ကြာအင်းကျေးရွာအုပ်စု",
    "ရန်ကုတ်ကျေးရွာအုပ်စု",
    "ရေကျော်ကြီးကျေးရွာအုပ်စု",
    "ရေပူကြီးကျေးရွာအုပ်စု",
    "လောင်းကိုင်ကျေးရွာအုပ်စု",
    "ဝင်းကကျေးရွာအုပ်စု",
    "ဝင်းကြီးရပ်ကွက်(ကော့ကရိတ်မြို့)",
    "ဝဲလစ်ကျေးရွာအုပ်စု",
    "သပြု(ကမော့ပိ)ကျေးရွာအုပ်စု",
    "သမိန်ဒွတ်ကျေးရွာအုပ်စု",
    "သရက်တောကျေးရွာအုပ်စု",
    "ဟောင်သရောကျေးရွာအုပ်စု",
    "အင်းကြီးကျေးရွာအုပ်စု",
    "အင်းရှည်ကျေးရွာအုပ်စု",
    "အထက်တောင်သူစုရပ်ကွက်(ကော့ကရိတ်မြို့)",
    "အနောက်ပိုင်းရပ်ကွက်(ကော့ကရိတ်မြို့)",
    "အနောက်ဘက်ကမ်းကျေးရွာအုပ်စု",
    "အမှတ်(၁)ရပ်ကွက်(ကျုံဒိုးမြို့)",
    "အမှတ်(၂)ရပ်ကွက်(ကျုံဒိုးမြို့)",
    "အမှတ်(၃)ရပ်ကွက်(ကျုံဒိုးမြို့)",
    "အမှတ်(၄)ရပ်ကွက်(ကျုံဒိုးမြို့)",
    "အလယ်တန်းရပ်ကွက်(ကော့ကရိတ်မြို့)",
    "အီဘိုင်းကျေးရွာအုပ်စု",
    "အောက်တောင်သူစုရပ်ကွက်(ကော့ကရိတ်မြို့)",
    "အံကောင်ကျေးရွာအုပ်စု",
    "အံဖကြီးကျေးရွာအုပ်စု"
  )

  override fun getWardsForTownship(stateRegion: String, township: String): List<String> {
    return fakeWard
  }

  override fun getWardDetails(stateRegion: String, township: String, wardName: String): Ward {
    return Ward(
      id = WardId("34610"),
      name = "ကညင်ကတိုက်ကျေးရွာအုပ်စု",
      upperHouseConstituency = Constituency(
        id = ConstituencyId("2698"),
        name = "ကရင်ပြည်နယ် အမျိုးသားလွှတ်တော် မဲဆန္ဒနယ်အမှတ်(၂)",
        house = HouseType.LOWER_HOUSE,
        remark = null
      ),
      lowerHouseConstituency = Constituency(
        id = ConstituencyId("2368"),
        name = "ကော့ကရိတ်မြို့နယ် ပြည်သူ့လွှတ်တော်မဲဆန္ဒနယ်",
        house = HouseType.LOWER_HOUSE,
        remark = "Remark left to test if remark is showing, put as null if you dont want this"
      ),
      stateRegionConstituency = Constituency(
        id = ConstituencyId("2892"),
        name = "ကကော့ကရိတ်မြို့နယ် ပြည်နယ်လွှတ်တော် မဲဆန္ဒနယ်အမှတ်(၂)",
        house = HouseType.LOWER_HOUSE,
        remark = null
      )
    )
  }

}