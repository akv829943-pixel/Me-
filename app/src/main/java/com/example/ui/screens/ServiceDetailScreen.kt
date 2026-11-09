package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MarketingService
import com.example.model.ServiceTier
import com.example.ui.components.InstagramGradientButton
import com.example.ui.components.PolicySafeGuaranteeCard
import com.example.ui.theme.BrandPinkAccent
import com.example.ui.theme.BrandPurplePrimary
import com.example.ui.theme.BrandTextPrimaryLight
import com.example.ui.theme.BrandTextSecondaryLight
import com.example.ui.theme.InstaGradients
import com.example.ui.theme.InstaPink
import com.example.ui.viewmodel.InstaBoostViewModel

@Composable
fun ServiceDetailScreen(
  viewModel: InstaBoostViewModel,
  modifier: Modifier = Modifier
) {
  val service by viewModel.selectedService.collectAsState()
  val selectedTier by viewModel.selectedTier.collectAsState()

  if (service == null) {
    Box(
      modifier = modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text("No service selected")
    }
    return
  }

  val curService = service!!
  val tiers = listOf(curService.starterTier, curService.proTier, curService.viralTier)

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(16.dp)
  ) {
    // Header
    item {
      Column {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(InstaPink.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = curService.category.displayName,
            style = MaterialTheme.typography.labelSmall.copy(
              color = InstaPink,
              fontWeight = FontWeight.Bold
            )
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = curService.title,
          style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.Black,
            color = BrandTextPrimaryLight,
            fontSize = 24.sp
          )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = curService.subtitle,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = BrandTextSecondaryLight,
            lineHeight = 22.sp
          )
        )

        Spacer(modifier = Modifier.height(14.dp))
      }
    }

    // Deliverables breakdown
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "What's Included",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BrandTextPrimaryLight
            )
          )
          Spacer(modifier = Modifier.height(10.dp))
          curService.highlights.forEach { item ->
            Row(
              modifier = Modifier.padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF10B981),
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = item,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = BrandTextPrimaryLight
                )
              )
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // Policy Card
    item {
      PolicySafeGuaranteeCard()
      Spacer(modifier = Modifier.height(18.dp))
    }

    // Select Package Tier
    item {
      Text(
        text = "Select Campaign Package",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = BrandTextPrimaryLight
        )
      )
      Text(
        text = "Choose the growth intensity matching your creator goals",
        style = MaterialTheme.typography.bodySmall.copy(
          color = BrandTextSecondaryLight
        )
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    items(tiers.size) { index ->
      val tier = tiers[index]
      val isSelected = selectedTier?.id == tier.id

      TierSelectionCard(
        tier = tier,
        isSelected = isSelected,
        onSelect = { viewModel.setSelectedTier(tier) },
        modifier = Modifier.padding(bottom = 12.dp)
      )
    }

    // Bottom Action
    item {
      Spacer(modifier = Modifier.height(10.dp))
      val activeTier = selectedTier ?: curService.proTier
      InstagramGradientButton(
        text = "Proceed with ${activeTier.name} (₹${activeTier.priceInr})",
        onClick = { viewModel.openOrderForm(curService, activeTier) },
        testTag = "proceed_with_tier_btn"
      )
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@Composable
fun TierSelectionCard(
  tier: ServiceTier,
  isSelected: Boolean,
  onSelect: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onSelect)
      .testTag("tier_card_${tier.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) Color(0xFFFDF4FF) else Color.White
    ),
    border = if (isSelected) {
      CardDefaults.outlinedCardBorder().copy(brush = InstaGradients.Primary, width = 2.dp)
    } else {
      CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE5E7EB)))
    },
    elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.5.dp)
  ) {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
              selectedColor = InstaPink
            )
          )
          Spacer(modifier = Modifier.width(6.dp))
          Column {
            Text(
              text = tier.name,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BrandTextPrimaryLight
              )
            )
            if (tier.tag != null) {
              Text(
                text = tier.tag,
                style = MaterialTheme.typography.labelSmall.copy(
                  color = InstaPink,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp
                )
              )
            }
          }
        }

        Column(horizontalAlignment = Alignment.End) {
          Text(
            text = "₹${tier.priceInr}",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Black,
              color = BrandPurplePrimary
            )
          )
          Text(
            text = "₹${tier.originalPriceInr}",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BrandTextSecondaryLight,
              textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Schedule,
          contentDescription = null,
          tint = BrandTextSecondaryLight,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "Duration: ${tier.durationDays} days",
          style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
          imageVector = Icons.Default.RocketLaunch,
          contentDescription = null,
          tint = InstaPink,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = tier.estimatedReach,
          style = MaterialTheme.typography.bodySmall.copy(
            color = InstaPink,
            fontWeight = FontWeight.SemiBold
          )
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        tier.deliverables.forEach { item ->
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              tint = Color(0xFF10B981),
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = item,
              style = MaterialTheme.typography.bodySmall.copy(
                color = BrandTextPrimaryLight,
                fontSize = 12.sp
              )
            )
          }
        }
      }
    }
  }
}
