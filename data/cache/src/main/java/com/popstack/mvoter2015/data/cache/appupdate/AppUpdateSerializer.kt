package com.popstack.mvoter2015.data.cache.appupdate

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.popstack.mvoter2015.data.cache.AppUpdateProto
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object AppUpdateSerializer : Serializer<AppUpdateProto> {

  override fun readFrom(input: InputStream): AppUpdateProto {
    return try {
      AppUpdateProto.ADAPTER.decode(input)
    } catch (exception: IOException) {
      throw CorruptionException("Cannot read proto", exception)
    }
  }

  override fun writeTo(
    t: AppUpdateProto,
    output: OutputStream
  ) {
    t.encode(output)
  }

}