package com.popstack.mvoter2015.data.cache.map

import com.popstack.mvoter2015.data.cache.entity.FaqTable
import com.popstack.mvoter2015.data.cache.entity.NewsTable
import com.popstack.mvoter2015.data.cache.entity.PartyTable
import com.popstack.mvoter2015.domain.faq.model.Faq
import com.popstack.mvoter2015.domain.news.model.News
import com.popstack.mvoter2015.domain.party.model.Party

internal fun FaqTable.mapToFaq(): Faq {
  return Faq(
    id = id,
    question = question,
    answer = answer,
    lawSource = lawSource,
    articleSource = articleSource,
    category = category
  )
}

internal fun NewsTable.mapToNews(): News {
  return News(
    id = id,
    title = title,
    summary = summary,
    body = body,
    imageUrl = imageUrl,
    publishedDate = publishedDate,
    url = url
  )
}

internal fun PartyTable.mapToParty(): Party {
  return Party(
    id = id,
    registeredNumber = number,
    nameBurmese = burmeseName,
    nameEnglish = englishName,
    abbreviation = abbreviation,
    flagImage = flagImage,
    sealImage = sealImage,
    region = region,
    leadersAndChairmenList = leadersAndChairmen,
    contacts = contacts,
    memberCount = memberCount,
    headquarterLocation = headquarterLocation,
    policy = policy,
    establishmentApplicationDate = establishmentApplicationDate,
    establishmentApprovalDate = establishmentApprovalDate,
    registrationApplicationDate = registrationApplicationDate,
    registrationApprovalDate = registrationApprovalDate
  )
}