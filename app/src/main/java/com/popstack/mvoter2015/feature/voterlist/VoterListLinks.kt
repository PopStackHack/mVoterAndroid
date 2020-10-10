package com.popstack.mvoter2015.feature.voterlist

object VoterListLinks {

  val LINKS = listOf(
    Pair("ကချင်ပြည်နယ်", "http://bit.ly/kachin-voters"),
    Pair("ကယားပြည်နယ်", "http://bit.ly/kayah-voters"),
    Pair("ကရင်ပြည်နယ်", "http://bit.ly/kayin-voters"),
    Pair("ချင်းပြည်နယ်", " http://bit.ly/chin-voters"),
    Pair("စစ်ကိုင်းတိုင်းဒေသကြီး", "http://bit.ly/sagaing-voters"),
    Pair("တနင်္သာရီတိုင်းဒေသကြီး", "http://bit.ly/tanintharyi-voters"),
    Pair("ပဲခူးတိုင်းဒေသကြီး", "http://bit.ly/bago-voters"),
    Pair("မကွေးတိုင်းဒေသကြီး", "http://bit.ly/magway-voters"),
    Pair("မန္တလေးတိုင်းဒေသကြီး", "http://bit.ly/mandalay-voters"),
    Pair("မွန်ပြည်နယ်", "http://bit.ly/mon-voters"),
    Pair("ရခိုင်ပြည်နယ်", "http://bit.ly/rakhine-voters"),
    Pair("ရန်ကုန်တိုင်းဒေသကြီး", "http://bit.ly/yangon-voters"),
    Pair("ရှမ်းပြည်နယ်", "http://bit.ly/shan-voters"),
    Pair("ဧရာဝတီတိုင်းဒေသကြီး", "http://bit.ly/ayarwaddy-voters"),
    Pair("ပြည်ထောင်စုနယ်မြေ", "http://bit.ly/naypyitaw-voters")
  ).sortedBy { it.first }
    .toMutableList().also {
      it.add(0, Pair("ပြည်နယ်တိုင်းအားလုံး", "https://findyourpollingstation.uec.gov.mm/"))
    }.toList()
}