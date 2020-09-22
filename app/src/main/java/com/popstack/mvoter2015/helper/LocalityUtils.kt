package com.popstack.mvoter2015.helper

object LocalityUtils {
  fun isTownshipFromNPT(townshipName: String) = arrayOf(
    "ဇမ္ဗူသီရိမြို့နယ်",
    "ဇေယျာသီရိမြို့နယ်",
    "တပ်ကုန်းမြို့နယ်",
    "ဒက္ခိဏသီရိမြို့နယ်",
    "ပုဗ္ဗသီရိမြို့နယ်",
    "ပျဉ်းမနားမြို့နယ်",
    "လယ်ဝေးမြို့နယ်",
    "ဥတ္တရသီရိမြို့နယ်"
  ).firstOrNull { it == townshipName.replace(" ", "") } != null
}