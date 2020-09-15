package com.popstack.mvoter2015.data.cache.source.location

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.popstack.mvoter2015.data.cache.WardProto
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object WardSerializer : Serializer<WardProto> {

  override fun readFrom(input: InputStream): WardProto {
    return try {
      WardProto.ADAPTER.decode(input)
    } catch (exception: IOException) {
      throw CorruptionException("Cannot read proto", exception)
    }
  }

  override fun writeTo(
    t: WardProto,
    output: OutputStream
  ) {
    t.encode(output)
  }

}