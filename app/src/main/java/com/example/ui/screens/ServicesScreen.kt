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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.MarketingService
import com.example.model.ServiceCategory
import com.example.ui.components.PolicySafeGuaranteeCard
import com.example.ui.theme.BrandPinkAccent
import com.example.ui.theme.BrandPurplePrimary
import com.example.ui.theme.BrandTextPrimaryLight
import com.example.ui.theme.BrandTextSecondaryLight
import com.example.ui.theme.InstaGradients
import com.example.ui.theme.InstaPink
import com.example.ui.viewmodel.InstaBoostViewModel

@Composable
fun ServicesScreen(
  viewModel: InstaBoostViewModel,
  modifier: Modifier = Modifier
) {
  val selectedCategory by viewModel.selectedCategory.collectAsState()

  val filteredServices = if (selectedCategory == ServiceCategory.ALL) {
    SampleData.services
  } else {
    SampleData.services.filter { it.category == selectedCategory }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(bottom = 24.dp)
  ) {
    item {
      Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
      ) {
        Text(
          text = "Marketing & Growth Services",
          style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.Black,
            color = BrandTextPrimaryLight,
            fontSize = 24.sp
          )
        )
        Text(
          text = "100% Policy-Safe organic campaigns tailored for Instagram creators and brands.",
          style = MaterialTheme.typography.bodyMedium.copy(
            color = BrandTextSecondaryLight
          )
        )
      }
    }

    // Category Filter Chips
    item {
      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(ServiceCategory.values()) { category ->
          val isSelected = category == selectedCategory
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(
                if (isSelected) InstaPink else Color.White
              )
              .border(
                1.dp,
                if (isSelected) InstaPink else Color(0xFFE5E7EB),
                RoundedCornerShape(20.dp)
              )
              .clickable { viewModel.setSelectedCategory(category) }
              .padding(horizontal = 14.dp, vertical = 8.dp)
              .testTag("cat_filter_${category.name}"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = category.displayName,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else BrandTextPrimaryLight
              )
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Policy Guarantee reminder
    item {
      PolicySafeGuaranteeCard(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
      )
      Spacer(modifier = Modifier.height(10.dp))
    }

    // Services list
    items(filteredServices) { service ->
      FullServiceCard(
        service = service,
        onViewDetails = { viewModel.openServiceDetail(service) },
        onSelectOrder = { viewModel.openOrderForm(service, service.proTier) },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
      )
    }
  }
}

@Composable
fun FullServiceCard(
  service: MarketingService,
  onViewDetails: () -> Unit,
  onSelectOrder: () -> Unit,
  modifier: Modifier = Modifier
) {
  val serviceIcon: ImageVector = when (service.iconType) {
    "campaign" -> Icons.Default.Campaign
    "video" -> Icons.Default.Videocam
    "handshake" -> Icons.Default.Handshake
    "trending" -> Icons.Default.TrendingUp
    "package" -> Icons.Default.Inventory2
    else -> Icons.Default.Insights
  }

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(2.5.dp)
  ) {
    Column(
      modifier = Modifier.padding(18.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(InstaGradients.Primary),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = serviceIcon,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(24.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = service.title,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BrandTextPrimaryLight
              )
            )
            Text(
              text = service.category.displayName,
              style = MaterialTheme.typography.labelSmall.copy(
                color = BrandPurplePrimary,
                fontWeight = FontWeight.SemiBold
              )
            )
          }
        }

        if (service.badge != null) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(InstaPink.copy(alpha = 0.12f))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = service.badge,
              style = MaterialTheme.typography.labelSmall.copy(
                color = InstaPink,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = service.description,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = BrandTextSecondaryLight,
          lineHeight = 20.sp
        )
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Highlights
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        service.highlights.take(3).forEach { highlight ->
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = Color(0xFF10B981),
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = highlight,
              style = MaterialTheme.typography.bodySmall.copy(
                color = BrandTextPrimaryLight,
                fontSize = 12.sp
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Tier pricing preview bar
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFFFAF5FF))
          .padding(horizontal = 14.dp, vertical = 10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Packages from:",
              style = MaterialTheme.typography.labelSmall.copy(color = BrandTextSecondaryLight)
            )
            Row(verticalAlignment = Alignment.Bottom) {
              Text(
                text = "₹${service.starterTier.priceInr}",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Black,
                  color = BrandPurplePrimary
                )
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "₹${service.starterTier.originalPriceInr}",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = BrandTextSecondaryLight,
                  textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                )
              )
            }
          }

          Text(
            text = "Estimated: ${service.proTier.estimatedReach}",
            style = MaterialTheme.typography.labelSmall.copy(
              color = BrandPinkAccent,
              fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.widthIn(max = 140.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = onViewDetails,
          modifier = Modifier
            .weight(1f)
            .height(44.dp)
            .testTag("view_details_${service.id}"),
          shape = RoundedCornerShape(22.dp)
        ) {
          Text("Compare Tiers", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
        }

        Box(
          modifier = Modifier
            .weight(1.2f)
            .height(44.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(InstaGradients.Primary)
            .clickable(onClick = onSelectOrder)
            .testTag("select_package_${service.id}"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Select & Order",
            style = MaterialTheme.typography.labelMedium.copy(
              color = Color.White,
              fontWeight = FontWeight.Bold
            )
          )
        }
      }
    }
  }
}
