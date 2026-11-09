package com.example

import com.example.data.SampleData
import com.example.model.ServiceCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InstaBoostDataTest {

  @Test
  fun testRequiredMarketingServicesPresent() {
    val services = SampleData.services
    assertEquals(6, services.size)

    val serviceIds = services.map { it.id }
    assertTrue(serviceIds.contains("promo_campaigns"))
    assertTrue(serviceIds.contains("reel_promotion"))
    assertTrue(serviceIds.contains("creator_collabs"))
    assertTrue(serviceIds.contains("audience_reach"))
    assertTrue(serviceIds.contains("growth_packages"))
    assertTrue(serviceIds.contains("profile_audit"))
  }

  @Test
  fun testServiceTiersAndPricing() {
    SampleData.services.forEach { service ->
      assertNotNull(service.starterTier)
      assertNotNull(service.proTier)
      assertNotNull(service.viralTier)

      assertTrue(service.starterTier.priceInr > 0)
      assertTrue(service.proTier.priceInr >= service.starterTier.priceInr)
      assertTrue(service.viralTier.priceInr >= service.proTier.priceInr)
    }
  }

  @Test
  fun testSampleOrdersAndTestimonials() {
    assertTrue(SampleData.sampleOrders.isNotEmpty())
    assertTrue(SampleData.testimonials.isNotEmpty())
    assertTrue(SampleData.faqs.isNotEmpty())

    SampleData.sampleOrders.forEach { order ->
      assertTrue(order.referenceNo.startsWith("IB-2026-"))
    }
  }
}
