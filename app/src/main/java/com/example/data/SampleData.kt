package com.example.data

import com.example.data.local.OrderEntity
import com.example.model.FaqItem
import com.example.model.MarketingService
import com.example.model.OrderStatus
import com.example.model.ServiceCategory
import com.example.model.ServiceTier
import com.example.model.Testimonial

object SampleData {

  val services: List<MarketingService> = listOf(
    MarketingService(
      id = "promo_campaigns",
      title = "Instagram Promotion Campaigns",
      subtitle = "Targeted sponsored promotions & audience discovery to skyrocket genuine profile visits.",
      category = ServiceCategory.PROMOTION,
      description = "Reach hyper-targeted users interested in your exact niche through verified social ad channels, curated publisher pages, and demographic discovery. 100% compliant with Meta terms.",
      highlights = listOf(
        "Zero bots or fake accounts — 100% real human reach",
        "Targeted by geography, language, and interests",
        "Detailed profile visit & click-through analytics",
        "Safe & non-intrusive promotion methods"
      ),
      iconType = "campaign",
      badge = "Bestseller",
      starterTier = ServiceTier(
        id = "promo_starter",
        name = "Starter Discovery",
        priceInr = 999,
        originalPriceInr = 1499,
        durationDays = 3,
        estimatedReach = "5,000 – 10,000 Impressions",
        deliverables = listOf(
          "Sponsored feed feature on partner pages",
          "Niche audience interest targeting",
          "Basic delivery report with reach breakdown"
        )
      ),
      proTier = ServiceTier(
        id = "promo_pro",
        name = "Pro Momentum",
        priceInr = 2499,
        originalPriceInr = 3499,
        durationDays = 7,
        estimatedReach = "25,000 – 40,000 Impressions",
        deliverables = listOf(
          "Multi-channel sponsor placements",
          "Advanced age, gender & geo-targeting",
          "Dedicated campaign supervisor",
          "Detailed conversion & profile visit metrics"
        ),
        isPopular = true,
        tag = "Most Popular"
      ),
      viralTier = ServiceTier(
        id = "promo_viral",
        name = "Viral Surge",
        priceInr = 5499,
        originalPriceInr = 7999,
        durationDays = 14,
        estimatedReach = "80,000 – 120,000 Impressions",
        deliverables = listOf(
          "Premium network syndication across top communities",
          "High-intent creator audience routing",
          "Live campaign optimization adjustments",
          "Comprehensive ROI & retention analytics PDF"
        ),
        tag = "Max Impact"
      )
    ),

    MarketingService(
      id = "reel_promotion",
      title = "Reel Promotion",
      subtitle = "Boost high-retention 9:16 videos onto Explore and audio trending feeds.",
      category = ServiceCategory.REELS,
      description = "Maximize your Reel's initial momentum with organic engagement seeds from active creators, boosting completion rates and signaling Instagram's algorithm to recommend your content to millions.",
      highlights = listOf(
        "Optimized for 3-second hook & retention metrics",
        "Trending audio alignment recommendations",
        "Boosts reel to Explore page candidate pools",
        "Ideal for creators, musicians, and D2C brands"
      ),
      iconType = "video",
      badge = "Trending",
      starterTier = ServiceTier(
        id = "reel_starter",
        name = "Reel Spark",
        priceInr = 799,
        originalPriceInr = 1199,
        durationDays = 2,
        estimatedReach = "10,000 – 20,000 Reel Plays",
        deliverables = listOf(
          "Promoted to 1 chosen reel",
          "Organic niche viewer discovery",
          "Sound retention boost"
        )
      ),
      proTier = ServiceTier(
        id = "reel_pro",
        name = "Reel Velocity",
        priceInr = 1899,
        originalPriceInr = 2799,
        durationDays = 5,
        estimatedReach = "40,000 – 75,000 Reel Plays",
        deliverables = listOf(
          "Promoted across 2 chosen reels",
          "Explore tab recommendation priming",
          "High-completion rate viewer engagement",
          "Save & share amplification"
        ),
        isPopular = true,
        tag = "Creator Favorite"
      ),
      viralTier = ServiceTier(
        id = "reel_viral",
        name = "Reel Takeover",
        priceInr = 4199,
        originalPriceInr = 5999,
        durationDays = 10,
        estimatedReach = "150,000+ Potential Views",
        deliverables = listOf(
          "Promoted across 3 core reels",
          "Aggregated influencer story reposts",
          "Viral audio cluster push",
          "Deep-dive retention & watch-time report"
        ),
        tag = "Mega Reach"
      )
    ),

    MarketingService(
      id = "creator_collabs",
      title = "Creator Collaborations",
      subtitle = "Partner with verified creators in your domain for authentic shoutouts & co-reels.",
      category = ServiceCategory.COLLAB,
      description = "Tap into existing trust by partnering with pre-vetted niche influencers (10k-250k followers) who mention your page, co-author reels, or feature your brand in their stories.",
      highlights = listOf(
        "Vetted genuine creators with 4%+ engagement rates",
        "Hands-on collaboration brief & content review",
        "Direct audience trust transfer",
        "Contractual delivery guarantee"
      ),
      iconType = "handshake",
      badge = "High Trust",
      starterTier = ServiceTier(
        id = "collab_starter",
        name = "Micro Collab (1 Creator)",
        priceInr = 2999,
        originalPriceInr = 4499,
        durationDays = 5,
        estimatedReach = "15,000 – 25,000 Audience",
        deliverables = listOf(
          "1 Verified niche creator (10k-25k followers)",
          "1 Story mention with interactive sticker",
          "Account tag in bio highlight for 48h"
        )
      ),
      proTier = ServiceTier(
        id = "collab_pro",
        name = "Duo Collab (2 Creators)",
        priceInr = 5999,
        originalPriceInr = 8999,
        durationDays = 7,
        estimatedReach = "50,000 – 90,000 Audience",
        deliverables = listOf(
          "2 Verified niche creators (25k-60k followers)",
          "Co-authored Reel collaboration or Feed post",
          "Swipe-up/link sticker promotion in stories",
          "Audience cross-pollination report"
        ),
        isPopular = true,
        tag = "Best Value"
      ),
      viralTier = ServiceTier(
        id = "collab_viral",
        name = "Influencer Squad (4 Creators)",
        priceInr = 11999,
        originalPriceInr = 16999,
        durationDays = 14,
        estimatedReach = "150,000 – 300,000 Audience",
        deliverables = listOf(
          "4 Top-tier creators in your domain",
          "Synchronized launch day promo blitz",
          "Co-authored Reels + Dedicated story takeovers",
          "Full usage rights for campaign collateral"
        ),
        tag = "Elite Growth"
      )
    ),

    MarketingService(
      id = "audience_reach",
      title = "Audience Reach Campaigns",
      subtitle = "Broad demographic visibility through content seeding & explore page placement.",
      category = ServiceCategory.REACH,
      description = "Expand beyond your immediate follower circle. We seed your best content into niche thematic community hubs and explore page candidate pools.",
      highlights = listOf(
        "Multi-hub community seeding",
        "Niche hashtag cluster research included",
        "Continuous impression pacing over days",
        "Safe organic discovery methods"
      ),
      iconType = "trending",
      badge = null,
      starterTier = ServiceTier(
        id = "reach_starter",
        name = "Local Niche",
        priceInr = 1199,
        originalPriceInr = 1799,
        durationDays = 4,
        estimatedReach = "15,000+ Unique Accounts",
        deliverables = listOf(
          "Niche community syndication",
          "Custom researched hashtag set (30 tags)",
          "Daily impression tracking"
        )
      ),
      proTier = ServiceTier(
        id = "reach_pro",
        name = "National Expansion",
        priceInr = 2799,
        originalPriceInr = 3999,
        durationDays = 8,
        estimatedReach = "50,000+ Unique Accounts",
        deliverables = listOf(
          "Multi-state/National interest curation",
          "3 Curated hashtag clusters for future posts",
          "Story feature in community bulletin",
          "Discovery breakdown by age & city"
        ),
        isPopular = true,
        tag = "High Growth"
      ),
      viralTier = ServiceTier(
        id = "reach_viral",
        name = "Global Scale",
        priceInr = 6499,
        originalPriceInr = 9499,
        durationDays = 14,
        estimatedReach = "150,000+ Unique Accounts",
        deliverables = listOf(
          "Worldwide or target continent distribution",
          "High-authority page pin for 72 hours",
          "Explore tab presence accelerator",
          "Complete demographic audience export"
        ),
        tag = "Scale"
      )
    ),

    MarketingService(
      id = "growth_packages",
      title = "Content Growth Packages",
      subtitle = "All-inclusive monthly marketing service: Reels, stories, feed and profile optimization.",
      category = ServiceCategory.PACKAGES,
      description = "The ultimate hands-off growth engine for ambitious creators and businesses. Our team manages your promotion calendar, runs weekly sponsored pushes, and audits every post for maximum algorithmic lift.",
      highlights = listOf(
        "Comprehensive 30-day dedicated growth strategy",
        "Weekly performance review calls or WhatsApp updates",
        "Includes promotions, collab credits, and reels push",
        "Guaranteed authentic reach benchmarks"
      ),
      iconType = "package",
      badge = "Full Service",
      starterTier = ServiceTier(
        id = "pkg_starter",
        name = "Creator Kickstarter (15 Days)",
        priceInr = 4999,
        originalPriceInr = 6999,
        durationDays = 15,
        estimatedReach = "45,000+ Impressions & Growth",
        deliverables = listOf(
          "Promotion on 4 selected reels",
          "1 Micro creator collaboration",
          "Custom Bio & Highlight redesign guidelines",
          "Bi-weekly performance audit"
        )
      ),
      proTier = ServiceTier(
        id = "pkg_pro",
        name = "Growth Accelerator (30 Days)",
        priceInr = 8999,
        originalPriceInr = 12999,
        durationDays = 30,
        estimatedReach = "120,000+ Impressions & High Engagement",
        deliverables = listOf(
          "Continuous sponsored reach on 8 reels",
          "2 Creator shoutouts / collabs included",
          "Weekly trending audio & script prompts",
          "Dedicated growth account manager",
          "Monthly ROI executive briefing"
        ),
        isPopular = true,
        tag = "Most Comprehensive"
      ),
      viralTier = ServiceTier(
        id = "pkg_viral",
        name = "Brand Domination (30 Days)",
        priceInr = 16999,
        originalPriceInr = 24999,
        durationDays = 30,
        estimatedReach = "300,000+ Multi-format Impressions",
        deliverables = listOf(
          "Full suite: 15 Reels promoted + 4 Influencer collabs",
          "Weekly viral hook & caption copywriting",
          "Priority 24/7 VIP strategist direct line",
          "Comprehensive funnel & conversion tracking",
          "Competitor audience capture campaign"
        ),
        tag = "Enterprise"
      )
    ),

    MarketingService(
      id = "profile_audit",
      title = "Profile Audit & Content Strategy",
      subtitle = "Actionable report by seasoned Instagram strategists to fix hooks, bio, and grid aesthetic.",
      category = ServiceCategory.AUDIT,
      description = "Why spend on promotions if your profile doesn't convert visitors into followers? Get an exhaustive, 12-page diagnostic report pinpointing exactly what to change in your bio, hooks, captions, and posting frequency.",
      highlights = listOf(
        "12-page bespoke PDF audit delivered in 48 hours",
        "Bio, grid aesthetics & highlight revamps",
        "Hook analysis for your past 10 reels",
        "Custom content calendar with 20 high-viral post ideas"
      ),
      iconType = "analytics",
      badge = "Fast Delivery",
      starterTier = ServiceTier(
        id = "audit_starter",
        name = "Quick Diagnostics",
        priceInr = 499,
        originalPriceInr = 999,
        durationDays = 2,
        estimatedReach = "N/A (Strategic Report)",
        deliverables = listOf(
          "Profile bio & link optimization",
          "Top 5 reels hook & retention critique",
          "3 actionable fixes for higher conversion"
        )
      ),
      proTier = ServiceTier(
        id = "audit_pro",
        name = "Complete Strategy Blueprint",
        priceInr = 1299,
        originalPriceInr = 1999,
        durationDays = 3,
        estimatedReach = "Strategic Playbook + 20 Ideas",
        deliverables = listOf(
          "Exhaustive 12-page personalized PDF audit",
          "Grid color & visual aesthetic overhaul plan",
          "20 viral reel script concepts for your niche",
          "Custom hashtag & SEO keyword database",
          "30-day recommended publishing schedule"
        ),
        isPopular = true,
        tag = "Top Rated"
      ),
      viralTier = ServiceTier(
        id = "audit_viral",
        name = "Audit + 1-on-1 Strategy Call",
        priceInr = 2999,
        originalPriceInr = 4499,
        durationDays = 4,
        estimatedReach = "Personalized Coaching & Blueprint",
        deliverables = listOf(
          "Everything in Complete Strategy Blueprint",
          "45-minute 1-on-1 Google Meet coaching session",
          "Live Q&A on monetization, brand deals & pitches",
          "Direct WhatsApp access to strategist for 14 days"
        ),
        tag = "VIP Coaching"
      )
    )
  )

  val testimonials: List<Testimonial> = listOf(
    Testimonial(
      id = "t1",
      creatorName = "Priya Sharma",
      instagramHandle = "@priyastylefile",
      niche = "Fashion & Lifestyle",
      growthMetric = "+18.4K genuine followers",
      quote = "InstaBoost completely transformed my fashion account. I was stuck at 8k for a year. Their Reel Promotion and Profile Audit gave me the exact hooks I needed!",
      rating = 5,
      isVerified = true
    ),
    Testimonial(
      id = "t2",
      creatorName = "Vikram Aditya",
      instagramHandle = "@vikram_fitnessfit",
      niche = "Fitness & Nutrition",
      growthMetric = "3.2M Reel Impressions",
      quote = "I love their policy transparency. Zero fake bots, zero shady logins. Just real people watching my workouts and asking for coaching clients. Best ROI ever.",
      rating = 5,
      isVerified = true
    ),
    Testimonial(
      id = "t3",
      creatorName = "Aanya & Rohan",
      instagramHandle = "@nomadcurators",
      niche = "Travel & Filmmaking",
      growthMetric = "4 Brand Sponsorships signed",
      quote = "The Creator Collaboration package introduced our reels to travel communities we could never reach organically. The order tracking kept us informed every day.",
      rating = 5,
      isVerified = true
    ),
    Testimonial(
      id = "t4",
      creatorName = "Kabir Tech",
      instagramHandle = "@kabirgadgets",
      niche = "Consumer Tech",
      growthMetric = "+42K profile visits",
      quote = "Legitimate marketing is rare in this space. InstaBoost delivers real campaigns with genuine engagement. Their admin and customer support is top tier.",
      rating = 5,
      isVerified = true
    )
  )

  val faqs: List<FaqItem> = listOf(
    FaqItem(
      question = "Do you ever ask for my Instagram password?",
      answer = "Never! We will NEVER ask for your Instagram password or login credentials. All our promotional campaigns are executed externally through partner networks, sponsored placements, and community hubs using only your public profile URL or Reel link."
    ),
    FaqItem(
      question = "Are these services compliant with Instagram policies?",
      answer = "Yes, 100%. We strictly prohibit bots, click farms, automated likes, fake views, or scraping. Every view and profile visit comes from legitimate, interested human users through targeted social media marketing."
    ),
    FaqItem(
      question = "How quickly does my campaign begin?",
      answer = "Once your order is submitted and payment is verified, our strategists review your profile within 4–12 hours. Campaigns typically go live within 24 hours and you can track live status in the Track Order screen."
    ),
    FaqItem(
      question = "How do payments work in this app?",
      answer = "Currently, the app operates in a secure Sandbox / Test Mode for UPI and Razorpay. You can test complete ordering and payment flows without entering real banking credentials or spending real money."
    ),
    FaqItem(
      question = "What if my account is private?",
      answer = "Your Instagram profile must be set to PUBLIC for promotional campaigns, so new audiences can view your content and choose to follow your journey."
    )
  )

  val sampleOrders: List<OrderEntity> = listOf(
    OrderEntity(
      id = 1,
      referenceNo = "IB-2026-9042",
      serviceId = "reel_promotion",
      serviceName = "Reel Promotion",
      tierName = "Reel Velocity (Pro)",
      amountInr = 1899,
      instagramUrl = "https://instagram.com/priyastylefile",
      customerName = "Priya Sharma",
      customerEmail = "priya@gmail.com",
      customerPhone = "+91 98765 43210",
      targetNiche = "Fashion & OOTD",
      status = OrderStatus.CAMPAIGN_ACTIVE.name,
      paymentMethod = "UPI_GPAY",
      paymentRef = "UPI/TEST/892138",
      createdAt = System.currentTimeMillis() - 86400000L * 2,
      notes = "Focus on wedding season reel with trending audio."
    ),
    OrderEntity(
      id = 2,
      referenceNo = "IB-2026-8815",
      serviceId = "profile_audit",
      serviceName = "Profile Audit & Strategy",
      tierName = "Complete Strategy Blueprint",
      amountInr = 1299,
      instagramUrl = "https://instagram.com/vikram_fitnessfit",
      customerName = "Vikram Aditya",
      customerEmail = "vikram@gmail.com",
      customerPhone = "+91 98111 22334",
      targetNiche = "Fitness & Nutrition",
      status = OrderStatus.COMPLETED.name,
      paymentMethod = "RAZORPAY_CARD",
      paymentRef = "PAY_TEST_771892",
      createdAt = System.currentTimeMillis() - 86400000L * 5,
      notes = "Audit report delivered. High satisfaction."
    ),
    OrderEntity(
      id = 3,
      referenceNo = "IB-2026-7640",
      serviceId = "promo_campaigns",
      serviceName = "Instagram Promotion Campaigns",
      tierName = "Pro Momentum",
      amountInr = 2499,
      instagramUrl = "https://instagram.com/kabirgadgets",
      customerName = "Kabir Tech",
      customerEmail = "kabir@techmail.com",
      customerPhone = "+91 99887 66554",
      targetNiche = "Tech Reviews",
      status = OrderStatus.STRATEGY_AUDIT.name,
      paymentMethod = "UPI_PHONEPE",
      paymentRef = "UPI/TEST/992014",
      createdAt = System.currentTimeMillis() - 3600000L * 8,
      notes = "Target audience: 18-35 gadget enthusiasts in metros."
    )
  )
}
