package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.data.local.OrderEntity
import com.example.model.OrderStatus
import com.example.ui.components.OrderStatusBadge
import com.example.ui.theme.BrandPinkAccent
import com.example.ui.theme.BrandPurplePrimary
import com.example.ui.theme.BrandTextPrimaryLight
import com.example.ui.theme.BrandTextSecondaryLight
import com.example.ui.theme.InstaGradients
import com.example.ui.theme.InstaPink
import com.example.ui.viewmodel.InstaBoostViewModel

@Composable
fun TrackOrderScreen(
  viewModel: InstaBoostViewModel,
  modifier: Modifier = Modifier
) {
  val searchQuery by viewModel.trackSearchQuery.collectAsState()
  val trackedOrder by viewModel.trackedOrder.collectAsState()
  val allOrders by viewModel.allOrders.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(16.dp)
  ) {
    item {
      Text(
        text = "Track Campaign Order",
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          color = BrandTextPrimaryLight,
          fontSize = 24.sp
        )
      )
      Text(
        text = "Enter your reference number or select an order below to view live campaign status.",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = BrandTextSecondaryLight
        )
      )
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Search bar
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = {
          viewModel.trackSearchQuery.value = it
          viewModel.trackOrder(it)
        },
        label = { Text("Search by Order Reference (e.g. IB-2026-9042)") },
        placeholder = { Text("IB-2026-XXXX or Instagram handle") },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = null, tint = InstaPink)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { viewModel.trackOrder(searchQuery) }) {
              Icon(Icons.Default.Search, contentDescription = "Search", tint = BrandPurplePrimary)
            }
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("track_search_input"),
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = InstaPink,
          unfocusedBorderColor = Color(0xFFD1D5DB)
        ),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(16.dp))
    }

    // If an order is tracked, show full timeline and details
    if (trackedOrder != null) {
      val order = trackedOrder!!
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(3.dp)
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = order.referenceNo,
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = BrandPurplePrimary
                  )
                )
                Text(
                  text = order.serviceName,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BrandTextPrimaryLight
                  )
                )
              }
              OrderStatusBadge(status = order.orderStatus)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Target: ${order.instagramUrl}",
              style = MaterialTheme.typography.bodySmall.copy(color = InstaPink)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Step-by-step progress timeline
            CampaignTimeline(currentStatus = order.orderStatus)

            Spacer(modifier = Modifier.height(16.dp))

            // Order summary box
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF9FAFB))
                .padding(12.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ReceiptRow("Package Tier", order.tierName)
                ReceiptRow("Amount", "₹${order.amountInr}")
                ReceiptRow("Customer", order.customerName)
                ReceiptRow("Target Niche", order.targetNiche)
                ReceiptRow("Payment Status", if (order.paymentRef.startsWith("PENDING")) "Pending Selection" else "Verified (${order.paymentRef})")
              }
            }
          }
        }
        Spacer(modifier = Modifier.height(20.dp))
      }
    }

    // List of all orders for quick selection
    item {
      Text(
        text = "Recent Orders & Enquiries",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = BrandTextPrimaryLight
        )
      )
      Spacer(modifier = Modifier.height(8.dp))
    }

    items(allOrders) { ord ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
          .clickable { viewModel.selectOrderToTrack(ord) }
          .testTag("track_list_item_${ord.referenceNo}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = ord.referenceNo,
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = BrandPurplePrimary
              )
            )
            Text(
              text = "${ord.serviceName} • ${ord.customerName}",
              style = MaterialTheme.typography.bodySmall.copy(
                color = BrandTextSecondaryLight
              )
            )
          }
          OrderStatusBadge(status = ord.orderStatus)
        }
      }
    }
  }
}

@Composable
fun CampaignTimeline(currentStatus: OrderStatus) {
  val steps = listOf(
    OrderStatus.SUBMITTED,
    OrderStatus.PAYMENT_VERIFIED,
    OrderStatus.STRATEGY_AUDIT,
    OrderStatus.CAMPAIGN_ACTIVE,
    OrderStatus.COMPLETED
  )

  val currentStepIndex = currentStatus.stepIndex

  Column(modifier = Modifier.fillMaxWidth()) {
    steps.forEachIndexed { index, step ->
      val isDone = step.stepIndex <= currentStepIndex
      val isCurrent = step == currentStatus

      Row(modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Box(
            modifier = Modifier
              .size(24.dp)
              .clip(CircleShape)
              .background(
                if (isDone) Color(0xFF10B981)
                else Color(0xFFE5E7EB)
              ),
            contentAlignment = Alignment.Center
          ) {
            if (isDone) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
              )
            } else {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(Color(0xFF9CA3AF))
              )
            }
          }

          if (index < steps.size - 1) {
            Box(
              modifier = Modifier
                .width(2.dp)
                .height(30.dp)
                .background(
                  if (step.stepIndex < currentStepIndex) Color(0xFF10B981)
                  else Color(0xFFE5E7EB)
                )
            )
          }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = step.label,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.SemiBold,
              color = if (isCurrent) InstaPink else if (isDone) BrandTextPrimaryLight else BrandTextSecondaryLight
            )
          )
          Text(
            text = step.description,
            style = MaterialTheme.typography.bodySmall.copy(
              color = BrandTextSecondaryLight,
              fontSize = 11.sp
            )
          )
          Spacer(modifier = Modifier.height(14.dp))
        }
      }
    }
  }
}
