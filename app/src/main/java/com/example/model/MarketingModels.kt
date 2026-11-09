package com.example.model

enum class ServiceCategory(val displayName: String) {
  ALL("All Services"),
  PROMOTION("Promotions"),
  REELS("Reels Viral"),
  COLLAB("Collabs"),
  REACH("Audience Reach"),
  PACKAGES("Growth Packages"),
  AUDIT("Profile Audit")
}

data class ServiceTier(
  val id: String,
  val name: String,
  val priceInr: Int,
  val originalPriceInr: Int,
  val durationDays: Int,
  val estimatedReach: String,
  val deliverables: List<String>,
  val isPopular: Boolean = false,
  val tag: String? = null
)

data class MarketingService(
  val id: String,
  val title: String,
  val subtitle: String,
  val category: ServiceCategory,
  val description: String,
  val highlights: List<String>,
  val iconType: String,
  val starterTier: ServiceTier,
  val proTier: ServiceTier,
  val viralTier: ServiceTier,
  val badge: String? = null
)

enum class OrderStatus(val label: String, val stepIndex: Int, val description: String) {
  SUBMITTED("Enquiry Submitted", 1, "Your campaign enquiry has been received and logged in queue."),
  PAYMENT_VERIFIED("Payment Verified", 2, "Test mode / payment confirmed. Assigned to growth strategist."),
  STRATEGY_AUDIT("Strategy Review", 3, "Account & reel audit underway to identify top-performing hooks."),
  CAMPAIGN_ACTIVE("Campaign Active", 4, "Real targeted audience reach and promotions actively running."),
  COMPLETED("Completed", 5, "Campaign targets reached. Full performance analytics report ready."),
  CANCELLED("Cancelled", 0, "Campaign cancelled or refunded.")
}

data class Testimonial(
  val id: String,
  val creatorName: String,
  val instagramHandle: String,
  val niche: String,
  val growthMetric: String,
  val quote: String,
  val rating: Int = 5,
  val isVerified: Boolean = true
)

data class FaqItem(
  val question: String,
  val answer: String
)

data class UserProfile(
  val id: String,
  val name: String,
  val email: String,
  val phone: String,
  val instagramHandle: String,
  val isAdmin: Boolean = false
)
