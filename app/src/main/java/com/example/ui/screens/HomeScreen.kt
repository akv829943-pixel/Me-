package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SampleData
import com.example.model.FaqItem
import com.example.model.MarketingService
import com.example.model.Testimonial
import com.example.ui.components.InstagramGradientButton
import com.example.ui.components.PolicySafeGuaranteeCard
import com.example.ui.theme.BrandDeepMidnight
import com.example.ui.theme.BrandPinkAccent
import com.example.ui.theme.BrandPurplePrimary
import com.example.ui.theme.BrandTextPrimaryLight
import com.example.ui.theme.BrandTextSecondaryLight
import com.example.ui.theme.InstaGold
import com.example.ui.theme.InstaGradients
import com.example.ui.theme.InstaMagenta
import com.example.ui.theme.InstaOrange
import com.example.ui.theme.InstaPink
import com.example.ui.theme.InstaPurple
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.InstaBoostViewModel

@Composable
fun HomeScreen(
  viewModel: InstaBoostViewModel,
  modifier: Modifier = Modifier
) {
  val targetGoal by viewModel.targetFollowersGoal.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(bottom = 24.dp)
  ) {
    // 1. Hero Section
    item {
      HeroHeaderSection(
        onExploreClick = { viewModel.navigateTo(AppScreen.SERVICES) },
        onTrackClick = { viewModel.navigateTo(AppScreen.TRACK_ORDER) }
      )
    }

    // 2. Stats bar
    item {
      StatsHighlightBar(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
    }

    // 3. Policy & Trust guarantee
    item {
      PolicySafeGuaranteeCard(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
      )
    }

    // 4. Interactive Growth & Reach Calculator
    item {
      GrowthCalculatorSection(
        targetGoal = targetGoal,
        onGoalChange = { viewModel.targetFollowersGoal.value = it },
        onOrderRecommended = {
          val recommended = SampleData.services.find { it.id == "reel_promotion" } ?: SampleData.services.first()
          viewModel.openOrderForm(recommended, recommended.proTier)
        },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
      )
    }

    // 5. Popular Services Carousel
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Popular Services",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = BrandTextPrimaryLight
            )
          )
          Text(
            text = "Hand-picked for rapid organic reach",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BrandTextSecondaryLight
            )
          )
        }
        Text(
          text = "View All →",
          style = MaterialTheme.typography.labelMedium.copy(
            color = InstaPink,
            fontWeight = FontWeight.Bold
          ),
          modifier = Modifier
            .clickable { viewModel.navigateTo(AppScreen.SERVICES) }
            .padding(4.dp)
            .testTag("view_all_services_btn")
        )
      }
      Spacer(modifier = Modifier.height(10.dp))
    }

    item {
      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        items(SampleData.services.take(4)) { service ->
          ServiceMiniCard(
            service = service,
            onClick = { viewModel.openServiceDetail(service) },
            onQuickOrder = { viewModel.openOrderForm(service, service.proTier) }
          )
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // 6. Testimonials Section
    item {
      Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Text(
          text = "Creator Success Stories",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = BrandTextPrimaryLight
          )
        )
        Text(
          text = "Real results from verified Instagram creators",
          style = MaterialTheme.typography.bodySmall.copy(
            color = BrandTextSecondaryLight
          )
        )
        Spacer(modifier = Modifier.height(12.dp))

        SampleData.testimonials.forEach { testimonial ->
          TestimonialCard(testimonial = testimonial)
          Spacer(modifier = Modifier.height(10.dp))
        }
      }
    }

    // 7. Frequently Asked Questions
    item {
      Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Text(
          text = "Frequently Asked Questions",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = BrandTextPrimaryLight
          )
        )
        Text(
          text = "Everything you need to know about InstaBoost",
          style = MaterialTheme.typography.bodySmall.copy(
            color = BrandTextSecondaryLight
          )
        )
        Spacer(modifier = Modifier.height(12.dp))

        SampleData.faqs.forEach { faq ->
          FaqCard(faq = faq)
          Spacer(modifier = Modifier.height(8.dp))
        }
      }
    }
  }
}

@Composable
fun HeroHeaderSection(
  onExploreClick: () -> Unit,
  onTrackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(BrandDeepMidnight)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // Badge
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(Color.White.copy(alpha = 0.12f))
          .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
          .padding(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.RocketLaunch,
            contentDescription = null,
            tint = InstaGold,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "#1 Organic Social Marketing Platform",
            style = MaterialTheme.typography.labelSmall.copy(
              color = Color.White,
              fontWeight = FontWeight.SemiBold
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Grow Your Social Presence",
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          color = Color.White,
          lineHeight = 34.sp
        )
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Scale your reach, Reels, and engagement with 100% policy-compliant promotional strategies. No passwords needed, ever.",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = Color.White.copy(alpha = 0.8f),
          lineHeight = 21.sp
        )
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Hero banner image
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(16f / 9f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
      ) {
        Image(
          painter = painterResource(id = R.drawable.hero_growth_banner),
          contentDescription = "InstaBoost Growth Analytics",
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        InstagramGradientButton(
          text = "Explore Services",
          onClick = onExploreClick,
          icon = Icons.AutoMirrored.Filled.ArrowForward,
          modifier = Modifier.weight(1.3f),
          testTag = "explore_services_hero_btn"
        )

        OutlinedButton(
          onClick = onTrackClick,
          modifier = Modifier
            .weight(0.9f)
            .height(52.dp)
            .testTag("track_order_hero_btn"),
          shape = RoundedCornerShape(26.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.White
          ),
          border = ButtonDefaults.outlinedButtonBorder().copy(
            brush = Brush.horizontalGradient(listOf(Color.White.copy(alpha = 0.6f), Color.White.copy(alpha = 0.2f)))
          )
        ) {
          Text(
            text = "Track Order",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
          )
        }
      }
    }
  }
}

@Composable
fun StatsHighlightBar(
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = Color.White
    ),
    elevation = CardDefaults.cardElevation(2.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 14.dp, horizontal = 12.dp),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      StatItem(number = "14.2K+", label = "Campaigns")
      Box(modifier = Modifier.width(1.dp).height(30.dp).background(Color(0xFFE5E7EB)))
      StatItem(number = "45M+", label = "Real Reach")
      Box(modifier = Modifier.width(1.dp).height(30.dp).background(Color(0xFFE5E7EB)))
      StatItem(number = "98.6%", label = "Satisfaction")
    }
  }
}

@Composable
fun StatItem(
  number: String,
  label: String
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = number,
      style = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Black,
        color = BrandPurplePrimary
      )
    )
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(
        color = BrandTextSecondaryLight,
        fontSize = 11.sp
      )
    )
  }
}

@Composable
fun GrowthCalculatorSection(
  targetGoal: Int,
  onGoalChange: (Int) -> Unit,
  onOrderRecommended: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = Color.White
    ),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = Brush.horizontalGradient(listOf(InstaPurple.copy(alpha = 0.2f), InstaPink.copy(alpha = 0.2f)))
    ),
    elevation = CardDefaults.cardElevation(3.dp)
  ) {
    Column(
      modifier = Modifier.padding(18.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(InstaPink.copy(alpha = 0.12f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Calculate,
            contentDescription = null,
            tint = InstaPink,
            modifier = Modifier.size(18.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "Growth & Reach Calculator",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BrandTextPrimaryLight
            )
          )
          Text(
            text = "Slide to estimate campaign impressions & target reach",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BrandTextSecondaryLight
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
      ) {
        Text(
          text = "Target Growth Scale:",
          style = MaterialTheme.typography.bodyMedium.copy(
            color = BrandTextSecondaryLight,
            fontWeight = FontWeight.Medium
          )
        )
        Text(
          text = "+${String.format("%,d", targetGoal)} Reach",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Black,
            color = InstaPink
          )
        )
      }

      Slider(
        value = targetGoal.toFloat(),
        onValueChange = { onGoalChange(it.toInt()) },
        valueRange = 5000f..150000f,
        steps = 14,
        colors = SliderDefaults.colors(
          thumbColor = InstaPink,
          activeTrackColor = InstaMagenta,
          inactiveTrackColor = Color(0xFFF3E8FF)
        ),
        modifier = Modifier.testTag("calculator_slider")
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text("5,000", style = MaterialTheme.typography.labelSmall.copy(color = BrandTextSecondaryLight))
        Text("75,000", style = MaterialTheme.typography.labelSmall.copy(color = BrandTextSecondaryLight))
        Text("150,000+", style = MaterialTheme.typography.labelSmall.copy(color = BrandTextSecondaryLight))
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Calculated stats summary box
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFFFAF5FF))
          .padding(12.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Est. Profile Impressions:", style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight))
            Text(
              "${String.format("%,d", (targetGoal * 2.8).toInt())} views",
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = BrandPurplePrimary)
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Recommended Strategy:", style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight))
            Text(
              if (targetGoal < 25000) "Reel Spark / Starter" else if (targetGoal < 75000) "Reel Velocity (Pro)" else "Viral Takeover",
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = InstaPink)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      InstagramGradientButton(
        text = "Select Recommended Package",
        onClick = onOrderRecommended,
        testTag = "calc_order_recommended_btn"
      )
    }
  }
}

@Composable
fun ServiceMiniCard(
  service: MarketingService,
  onClick: () -> Unit,
  onQuickOrder: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .width(260.dp)
      .clickable(onClick = onClick)
      .testTag("service_card_${service.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(3.dp)
  ) {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (service.badge != null) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(InstaPink)
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = service.badge,
              style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              )
            )
          }
        } else {
          Spacer(modifier = Modifier.width(1.dp))
        }

        Text(
          text = "From ₹${service.starterTier.priceInr}",
          style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.Black,
            color = BrandPurplePrimary
          )
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = service.title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = BrandTextPrimaryLight
        ),
        maxLines = 2
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = service.subtitle,
        style = MaterialTheme.typography.bodySmall.copy(
          color = BrandTextSecondaryLight,
          lineHeight = 16.sp
        ),
        maxLines = 3
      )

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedButton(
          onClick = onClick,
          modifier = Modifier.weight(1f).height(38.dp),
          shape = RoundedCornerShape(19.dp)
        ) {
          Text("Details", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .height(38.dp)
            .clip(RoundedCornerShape(19.dp))
            .background(InstaGradients.Primary)
            .clickable(onClick = onQuickOrder),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Order",
            style = MaterialTheme.typography.labelSmall.copy(
              color = Color.White,
              fontWeight = FontWeight.Bold
            )
          )
        }
      }
    }
  }
}

@Composable
fun TestimonialCard(
  testimonial: Testimonial,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(1.5.dp)
  ) {
    Column(
      modifier = Modifier.padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(InstaGradients.Primary),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = testimonial.creatorName.take(1),
              style = MaterialTheme.typography.titleSmall.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
              )
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = testimonial.creatorName,
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = BrandTextPrimaryLight
                )
              )
              if (testimonial.isVerified) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  imageVector = Icons.Default.Verified,
                  contentDescription = "Verified Creator",
                  tint = Color(0xFF0284C7),
                  modifier = Modifier.size(14.dp)
                )
              }
            }
            Text(
              text = "${testimonial.instagramHandle} • ${testimonial.niche}",
              style = MaterialTheme.typography.bodySmall.copy(
                color = BrandTextSecondaryLight,
                fontSize = 11.sp
              )
            )
          }
        }

        // Growth pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFECFDF5))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = testimonial.growthMetric,
            style = MaterialTheme.typography.labelSmall.copy(
              color = Color(0xFF047857),
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "\"${testimonial.quote}\"",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = BrandTextPrimaryLight,
          lineHeight = 20.sp
        )
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row {
        repeat(testimonial.rating) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = InstaGold,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}

@Composable
fun FaqCard(
  faq: FaqItem,
  modifier: Modifier = Modifier
) {
  var expanded by remember { mutableStateOf(false) }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .clickable { expanded = !expanded },
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(1.dp)
  ) {
    Column(
      modifier = Modifier.padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = faq.question,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.SemiBold,
            color = BrandTextPrimaryLight
          ),
          modifier = Modifier.weight(1f)
        )
        Icon(
          imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
          contentDescription = null,
          tint = BrandTextSecondaryLight
        )
      }

      AnimatedVisibility(visible = expanded) {
        Column {
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = faq.answer,
            style = MaterialTheme.typography.bodyMedium.copy(
              color = BrandTextSecondaryLight,
              lineHeight = 20.sp
            )
          )
        }
      }
    }
  }
}
