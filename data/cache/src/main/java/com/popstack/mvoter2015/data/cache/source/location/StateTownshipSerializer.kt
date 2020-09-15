package com.popstack.mvoter2015.data.cache.source.location

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.popstack.mvoter2015.data.cache.StateTownshipProto
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object StateTownshipSerializer : Serializer<StateTownshipProto> {

  override fun readFrom(input: InputStream): StateTownshipProto {
    return try {
      StateTownshipProto.ADAPTER.decode(input)
    } catch (exception: IOException) {
      throw CorruptionException("Cannot read proto", exception)
    }
  }

  override fun writeTo(
    t: StateTownshipProto,
    output: OutputStream
  ) {
    t.encode(output)
  }

}