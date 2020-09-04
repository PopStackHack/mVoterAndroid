package com.popstack.mvoter2015.data.cache.source

import com.popstack.mvoter2015.data.cache.TestDbProvider
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import org.junit.Assert
import org.junit.Test
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Random
import java.util.UUID

class PartyCacheSourceImplTest {

  private val database = TestDbProvider().create()

  private val currentTime = LocalDateTime.now()
  private val clock = Clock.fixed(currentTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())
  private val partyCacheSource = PartyCacheSourceImpl(database, clock)

  @Test
  fun testGetListWithinDecay() {
    val party = randomParty()
    partyCacheSource.putParty(party)

    val timeWithinDecay = currentTime.plusMinutes(10)
    val clockWithinDecay = Clock.fixed(timeWithinDecay.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())
    val partyCacheCopiedWithinDecay = PartyCacheSourceImpl(database, clockWithinDecay)

    with(partyCacheCopiedWithinDecay.getPartyList(1, 10)) {
      Assert.assertTrue(this.contains(party))
    }
  }

  @Test
  fun testGetSingleGetWithinDecay() {
    val party = randomParty()
    partyCacheSource.putParty(party)

    val timeWithinDecay = currentTime.plusMinutes(10)
    val clockWithinDecay = Clock.fixed(timeWithinDecay.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())
    val partyCacheCopiedWithinDecay = PartyCacheSourceImpl(database, clockWithinDecay)

    with(partyCacheCopiedWithinDecay.getParty(party.id)) {
      Assert.assertEquals(party, this)
    }
  }

  @Test
  fun testGetListAfterDecay() {
    val party = randomParty()
    partyCacheSource.putParty(party)

    val timeAfterDecay = currentTime.plusHours(1).plusSeconds(1)
    val clockAfterDecay = Clock.fixed(timeAfterDecay.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())
    val partyCacheAfterDecay = PartyCacheSourceImpl(database, clockAfterDecay)

    with(partyCacheAfterDecay.getPartyList(1, 10)) {
      Assert.assertFalse(this.contains(party))
    }
  }

  @Test
  fun testSingleGetAfterDecay() {
    val party = randomParty()
    partyCacheSource.putParty(party)

    val timeAfterDecay = currentTime.plusHours(1).plusSeconds(1)
    val clockAfterDecay = Clock.fixed(timeAfterDecay.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())
    val partyCacheAfterDecay = PartyCacheSourceImpl(database, clockAfterDecay)

    with(partyCacheAfterDecay.getParty(party.id)) {
      Assert.assertEquals(null, this)
    }
  }

  @Test
  fun testFlushDecay() {
    val party = randomParty()
    partyCacheSource.putParty(party)

    val timeAfterDecay = currentTime.plusHours(1).plusSeconds(1)
    val clockAfterDecay = Clock.fixed(timeAfterDecay.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())
    val partyCacheAfterDecay = PartyCacheSourceImpl(database, clockAfterDecay)

    val partyAfterDecay = randomParty()
    partyCacheAfterDecay.putParty(partyAfterDecay)

    partyCacheSource.flushDecayedData()
    with(partyCacheAfterDecay.getParty(party.id)) {
      Assert.assertEquals(null, this)
    }

    with(partyCacheAfterDecay.getParty(partyAfterDecay.id)) {
      Assert.assertEquals(partyAfterDecay, this)
    }
  }

  private fun randomParty(): Party {
    return Party(
      id = PartyId(UUID.randomUUID().toString()),
      registeredNumber = Random().nextInt(),
      nameBurmese = UUID.randomUUID().toString(),
      abbreviation = UUID.randomUUID().toString(),
      nameEnglish = UUID.randomUUID().toString(),
      flagImage = UUID.randomUUID().toString(),
      sealImage = UUID.randomUUID().toString(),
      region = UUID.randomUUID().toString(),
      leadersAndChairmenList = emptyList(),
      memberCount = UUID.randomUUID().toString(),
      headquarterLocation = UUID.randomUUID().toString(),
      policy = UUID.randomUUID().toString(),
      contacts = emptyList(),
      isEstablishedDueToArticle25 = Random().nextBoolean(),
      establishmentApplicationDate = currentTime.toLocalDate(),
      establishmentApprovalDate = currentTime.toLocalDate(),
      registrationApprovalDate = currentTime.toLocalDate(),
      registrationApplicationDate = currentTime.toLocalDate()
    )
  }
}