package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.OrderStatus
import com.example.ui.components.InstagramGradientButton
import com.example.ui.components.OrderStatusBadge
import com.example.ui.theme.BrandPinkAccent
import com.example.ui.theme.BrandPurplePrimary
import com.example.ui.theme.BrandTextPrimaryLight
import com.example.ui.theme.BrandTextSecondaryLight
import com.example.ui.theme.InstaGradients
import com.example.ui.theme.InstaPink
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.InstaBoostViewModel

@Composable
fun OrderSuccessScreen(
  viewModel: InstaBoostViewModel,
  modifier: Modifier = Modifier
) {
  val order by viewModel.activePendingOrder.collectAsState()

  if (order == null) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      Text("Order details not found")
    }
    return
  }

  val activeOrder = order!!

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    item {
      Spacer(modifier = Modifier.height(10.dp))
      Box(
        modifier = Modifier
          .size(80.dp)
          .clip(CircleShape)
          .background(Color(0xFF10B981).copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color(0xFF10B981)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "Campaign Order Confirmed!",
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          color = BrandTextPrimaryLight,
          fontSize = 24.sp
        )
      )

      Text(
        text = "Your promotion enquiry has been verified and queued for launch.",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = BrandTextSecondaryLight,
          fontSize = 14.sp
        )
      )

      Spacer(modifier = Modifier.height(20.dp))
    }

    // Order Reference Box
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.5.dp)
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "ORDER REFERENCE NUMBER",
            style = MaterialTheme.typography.labelSmall.copy(
              color = BrandTextSecondaryLight,
              letterSpacing = 1.sp
            )
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = activeOrder.referenceNo,
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Black,
              color = BrandPurplePrimary
            )
          )

          Spacer(modifier = Modifier.height(14.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFFF9FAFB))
              .padding(14.dp)
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              ReceiptRow("Service", activeOrder.serviceName)
              ReceiptRow("Package Tier", activeOrder.tierName)
              ReceiptRow("Target Instagram", activeOrder.instagramUrl)
              ReceiptRow("Amount Paid (Test)", "₹${activeOrder.amountInr}")
              ReceiptRow("Payment Method", activeOrder.paymentMethod)
              ReceiptRow("Transaction ID", activeOrder.paymentRef)
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(18.dp))
    }

    // Next steps
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5FF))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "What happens next?",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = BrandTextPrimaryLight
            )
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "1. Strategy team audits your Instagram profile & content hooks.\n" +
                   "2. Promotion campaign goes live within 12–24 hours.\n" +
                   "3. Real-time status updates are tracked under the 'Track' tab anytime.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BrandTextSecondaryLight,
              lineHeight = 20.sp
            )
          )
        }
      }
      Spacer(modifier = Modifier.height(20.dp))
    }

    // Navigation buttons
    item {
      InstagramGradientButton(
        text = "Track Campaign Live Status",
        onClick = { viewModel.selectOrderToTrack(activeOrder) },
        icon = Icons.Default.TrackChanges,
        testTag = "track_success_order_btn"
      )

      Spacer(modifier = Modifier.height(10.dp))

      OutlinedButton(
        onClick = { viewModel.navigateTo(AppScreen.HOME) },
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("return_home_btn"),
        shape = RoundedCornerShape(25.dp)
      ) {
        Text("Back to Home", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
      }
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun ReceiptRow(label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(label, style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight))
    Text(
      value,
      style = MaterialTheme.typography.bodySmall.copy(
        fontWeight = FontWeight.Bold,
        color = BrandTextPrimaryLight
      ),
      modifier = Modifier.widthIn(max = 200.dp)
    )
  }
}
