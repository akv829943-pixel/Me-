package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
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
fun AdminDashboardScreen(
  viewModel: InstaBoostViewModel,
  modifier: Modifier = Modifier
) {
  val allOrders by viewModel.allOrders.collectAsState()
  var searchQuery by remember { mutableStateOf("") }
  var filterStatus by remember { mutableStateOf<OrderStatus?>(null) }

  // Status update modal state
  var orderToUpdate by remember { mutableStateOf<OrderEntity?>(null) }

  // Filter orders
  val filteredOrders = allOrders.filter { ord ->
    val matchesSearch = searchQuery.isBlank() ||
      ord.referenceNo.contains(searchQuery, ignoreCase = true) ||
      ord.customerName.contains(searchQuery, ignoreCase = true) ||
      ord.instagramUrl.contains(searchQuery, ignoreCase = true) ||
      ord.serviceName.contains(searchQuery, ignoreCase = true)

    val matchesStatus = filterStatus == null || ord.orderStatus == filterStatus

    matchesSearch && matchesStatus
  }

  // Statistics calculation
  val totalOrders = allOrders.size
  val totalRevenue = allOrders.sumOf { it.amountInr }
  val activeCampaigns = allOrders.count { it.orderStatus == OrderStatus.CAMPAIGN_ACTIVE }
  val pendingEnquiries = allOrders.count { it.orderStatus == OrderStatus.SUBMITTED || it.orderStatus == OrderStatus.PAYMENT_VERIFIED }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(16.dp)
  ) {
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Admin Dashboard",
            style = MaterialTheme.typography.displayMedium.copy(
              fontWeight = FontWeight.Black,
              color = BrandTextPrimaryLight,
              fontSize = 24.sp
            )
          )
          Text(
            text = "Platform performance, incoming enquiries & order management",
            style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight)
          )
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // 1. Analytics Cards Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        AdminStatCard(
          title = "Total Revenue",
          value = "₹${String.format("%,d", totalRevenue)}",
          icon = Icons.Default.AttachMoney,
          color = Color(0xFF10B981),
          modifier = Modifier.weight(1f)
        )
        AdminStatCard(
          title = "Total Orders",
          value = "$totalOrders",
          icon = Icons.Default.TrendingUp,
          color = BrandPurplePrimary,
          modifier = Modifier.weight(1f)
        )
      }
      Spacer(modifier = Modifier.height(10.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        AdminStatCard(
          title = "Active Campaigns",
          value = "$activeCampaigns",
          icon = Icons.Default.Campaign,
          color = InstaPink,
          modifier = Modifier.weight(1f)
        )
        AdminStatCard(
          title = "Pending Review",
          value = "$pendingEnquiries",
          icon = Icons.Default.HourglassEmpty,
          color = Color(0xFFF59E0B),
          modifier = Modifier.weight(1f)
        )
      }
      Spacer(modifier = Modifier.height(20.dp))
    }

    // 2. Search & Status Filter
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        label = { Text("Search by reference, name, handle, or service") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = InstaPink) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("admin_search_input"),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = InstaPink,
          unfocusedBorderColor = Color(0xFFD1D5DB)
        ),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(10.dp))
    }

    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        item {
          AdminFilterPill(
            label = "All Statuses (${allOrders.size})",
            isSelected = filterStatus == null,
            onClick = { filterStatus = null }
          )
        }
        items(OrderStatus.values()) { status ->
          val count = allOrders.count { it.orderStatus == status }
          AdminFilterPill(
            label = "${status.label} ($count)",
            isSelected = filterStatus == status,
            onClick = { filterStatus = status }
          )
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // 3. Customer Enquiries & Orders List
    item {
      Text(
        text = "Customer Enquiries (${filteredOrders.size})",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = BrandTextPrimaryLight
        )
      )
      Spacer(modifier = Modifier.height(8.dp))
    }

    if (filteredOrders.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "No enquiries found matching filters",
              style = MaterialTheme.typography.bodyMedium.copy(color = BrandTextSecondaryLight)
            )
          }
        }
      }
    } else {
      items(filteredOrders) { order ->
        AdminOrderCard(
          order = order,
          onEditStatus = { orderToUpdate = order },
          onDelete = { viewModel.deleteOrderByAdmin(order.id) },
          modifier = Modifier.padding(bottom = 10.dp)
        )
      }
    }
  }

  // Status Update Dialog
  if (orderToUpdate != null) {
    val cur = orderToUpdate!!
    AlertDialog(
      onDismissRequest = { orderToUpdate = null },
      title = {
        Text("Update Order Status: ${cur.referenceNo}", fontWeight = FontWeight.Bold)
      },
      text = {
        Column {
          Text(
            text = "Select the new lifecycle stage for this customer enquiry:",
            style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight)
          )
          Spacer(modifier = Modifier.height(12.dp))
          OrderStatus.values().forEach { st ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                  viewModel.updateOrderStatusByAdmin(cur.id, st)
                  orderToUpdate = null
                }
                .padding(vertical = 8.dp, horizontal = 6.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = st.label,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = if (cur.orderStatus == st) FontWeight.Bold else FontWeight.Normal,
                  color = if (cur.orderStatus == st) InstaPink else BrandTextPrimaryLight
                )
              )
              OrderStatusBadge(status = st)
            }
          }
        }
      },
      confirmButton = {
        TextButton(onClick = { orderToUpdate = null }) {
          Text("Close")
        }
      }
    )
  }
}

@Composable
fun AdminStatCard(
  title: String,
  value: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(2.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelSmall.copy(color = BrandTextSecondaryLight)
        )
        Box(
          modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        }
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Black,
          color = BrandTextPrimaryLight
        )
      )
    }
  }
}

@Composable
fun AdminFilterPill(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .background(if (isSelected) BrandPurplePrimary else Color.White)
      .border(1.dp, if (isSelected) BrandPurplePrimary else Color(0xFFE5E7EB), RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 12.dp, vertical = 6.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        color = if (isSelected) Color.White else BrandTextPrimaryLight
      )
    )
  }
}

@Composable
fun AdminOrderCard(
  order: OrderEntity,
  onEditStatus: () -> Unit,
  onDelete: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(2.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = order.referenceNo,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                color = BrandPurplePrimary
              )
            )
            Spacer(modifier = Modifier.width(8.dp))
            OrderStatusBadge(status = order.orderStatus)
          }
          Text(
            text = order.serviceName,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BrandTextPrimaryLight
            )
          )
        }

        Row {
          IconButton(
            onClick = onEditStatus,
            modifier = Modifier.size(36.dp).testTag("admin_edit_order_${order.id}")
          ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Status", tint = InstaPink, modifier = Modifier.size(18.dp))
          }
          IconButton(
            onClick = onDelete,
            modifier = Modifier.size(36.dp).testTag("admin_delete_order_${order.id}")
          ) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Order", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(Color(0xFFF9FAFB))
          .padding(10.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text("Instagram: ${order.instagramUrl}", style = MaterialTheme.typography.bodySmall.copy(color = InstaPink, fontWeight = FontWeight.SemiBold))
          Text("Customer: ${order.customerName} • ${order.customerPhone} (${order.customerEmail})", style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight))
          Text("Tier & Amount: ${order.tierName} • ₹${order.amountInr}", style = MaterialTheme.typography.bodySmall.copy(color = BrandTextPrimaryLight, fontWeight = FontWeight.SemiBold))
          Text("Payment Ref: ${order.paymentMethod} / ${order.paymentRef}", style = MaterialTheme.typography.labelSmall.copy(color = BrandTextSecondaryLight))
          if (order.notes.isNotBlank()) {
            Text("Notes: ${order.notes}", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF047857)))
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      OutlinedButton(
        onClick = onEditStatus,
        modifier = Modifier.fillMaxWidth().height(38.dp),
        shape = RoundedCornerShape(19.dp)
      ) {
        Text("Change Status (Current: ${order.orderStatus.label})", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
      }
    }
  }
}
