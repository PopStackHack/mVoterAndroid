package com.popstack.mvoter2015.data.cache

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

class TestDbProvider {

  fun create(): MVoterDb {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    MVoterDb.Schema.create(driver)
    return DbProvider.create(driver)
  }

}