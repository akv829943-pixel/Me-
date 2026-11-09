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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Smartphone
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.InstagramGradientButton
import com.example.ui.theme.BrandDeepMidnight
import com.example.ui.theme.BrandPinkAccent
import com.example.ui.theme.BrandPurplePrimary
import com.example.ui.theme.BrandTextPrimaryLight
import com.example.ui.theme.BrandTextSecondaryLight
import com.example.ui.theme.InstaGradients
import com.example.ui.theme.InstaPink
import com.example.ui.viewmodel.InstaBoostViewModel

@Composable
fun PaymentScreen(
  viewModel: InstaBoostViewModel,
  modifier: Modifier = Modifier
) {
  val pendingOrder by viewModel.activePendingOrder.collectAsState()
  val selectedMethod by viewModel.selectedPaymentMethod.collectAsState()
  val isProcessing by viewModel.isPaymentProcessing.collectAsState()

  if (pendingOrder == null) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      Text("No pending order found")
    }
    return
  }

  val order = pendingOrder!!

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(16.dp)
  ) {
    item {
      Text(
        text = "Secure Payment Gateway",
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          color = BrandTextPrimaryLight,
          fontSize = 24.sp
        )
      )
      Text(
        text = "Order Reference: ${order.referenceNo}",
        style = MaterialTheme.typography.titleSmall.copy(
          color = BrandPurplePrimary,
          fontWeight = FontWeight.Bold
        )
      )
      Spacer(modifier = Modifier.height(14.dp))
    }

    // MANDATORY Rule Compliance Banner: Clear Test Mode Notice
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF93C5FD))
        )
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(Color(0xFF3B82F6).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = "Test Mode Notice",
              tint = Color(0xFF1D4ED8),
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Payment Gateway: TEST MODE ACTIVE",
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E40AF)
              )
            )
            Text(
              text = "This is a demonstration sandbox interface for UPI and Razorpay. No real funds are deducted.",
              style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF1E3A8A)
              )
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // Amount to Pay Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
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
              Text("Campaign Package", style = MaterialTheme.typography.labelSmall.copy(color = BrandTextSecondaryLight))
              Text(
                order.serviceName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = BrandTextPrimaryLight)
              )
              Text(
                "${order.tierName} • ${order.targetNiche}",
                style = MaterialTheme.typography.bodySmall.copy(color = InstaPink)
              )
            }
            Column(horizontalAlignment = Alignment.End) {
              Text("Total Payable", style = MaterialTheme.typography.labelSmall.copy(color = BrandTextSecondaryLight))
              Text(
                "₹${order.amountInr}",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Black,
                  color = BrandPurplePrimary
                )
              )
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(18.dp))
    }

    // 1. UPI Payment Section
    item {
      Text(
        text = "UPI Options (Zero Fee)",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = BrandTextPrimaryLight
        )
      )
      Spacer(modifier = Modifier.height(8.dp))
    }

    item {
      PaymentMethodOptionCard(
        id = "UPI_GPAY",
        title = "Google Pay (UPI)",
        subtitle = "Fast 1-tap checkout via Google Pay VPA",
        icon = Icons.Default.Smartphone,
        isSelected = selectedMethod == "UPI_GPAY",
        onSelect = { viewModel.setSelectedPaymentMethod("UPI_GPAY") },
        testTag = "pay_method_gpay"
      )
      Spacer(modifier = Modifier.height(8.dp))
    }

    item {
      PaymentMethodOptionCard(
        id = "UPI_PHONEPE",
        title = "PhonePe (UPI)",
        subtitle = "Instant approval via PhonePe handle",
        icon = Icons.Default.Smartphone,
        isSelected = selectedMethod == "UPI_PHONEPE",
        onSelect = { viewModel.setSelectedPaymentMethod("UPI_PHONEPE") },
        testTag = "pay_method_phonepe"
      )
      Spacer(modifier = Modifier.height(8.dp))
    }

    item {
      PaymentMethodOptionCard(
        id = "UPI_QR",
        title = "Scan UPI QR Code",
        subtitle = "Scan with any UPI app (Paytm, BHIM, Cred)",
        icon = Icons.Default.QrCode2,
        isSelected = selectedMethod == "UPI_QR",
        onSelect = { viewModel.setSelectedPaymentMethod("UPI_QR") },
        testTag = "pay_method_qr"
      )
      Spacer(modifier = Modifier.height(16.dp))
    }

    // 2. Razorpay Section
    item {
      Text(
        text = "Razorpay Integration",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = BrandTextPrimaryLight
        )
      )
      Spacer(modifier = Modifier.height(8.dp))
    }

    item {
      PaymentMethodOptionCard(
        id = "RAZORPAY_CARD",
        title = "Cards (Razorpay)",
        subtitle = "Credit / Debit Cards (Visa, MasterCard, RuPay)",
        icon = Icons.Default.CreditCard,
        isSelected = selectedMethod == "RAZORPAY_CARD",
        onSelect = { viewModel.setSelectedPaymentMethod("RAZORPAY_CARD") },
        testTag = "pay_method_card"
      )
      Spacer(modifier = Modifier.height(8.dp))
    }

    item {
      PaymentMethodOptionCard(
        id = "RAZORPAY_NETBANKING",
        title = "NetBanking (Razorpay)",
        subtitle = "HDFC, ICICI, SBI, Axis, Kotak and 50+ banks",
        icon = Icons.Default.AccountBalance,
        isSelected = selectedMethod == "RAZORPAY_NETBANKING",
        onSelect = { viewModel.setSelectedPaymentMethod("RAZORPAY_NETBANKING") },
        testTag = "pay_method_netbanking"
      )
      Spacer(modifier = Modifier.height(20.dp))
    }

    // Security badge
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "256-Bit SSL Encrypted Sandbox • No sensitive card data stored",
          style = MaterialTheme.typography.labelSmall.copy(
            color = BrandTextSecondaryLight,
            fontSize = 11.sp
          )
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // Action button
    item {
      InstagramGradientButton(
        text = if (isProcessing) "Simulating Gateway Approval..." else "Pay ₹${order.amountInr} (Test Mode)",
        onClick = { viewModel.processTestPayment() },
        isLoading = isProcessing,
        testTag = "confirm_pay_btn"
      )
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun PaymentMethodOptionCard(
  id: String,
  title: String,
  subtitle: String,
  icon: ImageVector,
  isSelected: Boolean,
  onSelect: () -> Unit,
  testTag: String
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onSelect)
      .testTag(testTag),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) Color(0xFFFAF5FF) else Color.White
    ),
    border = if (isSelected) {
      CardDefaults.outlinedCardBorder().copy(brush = InstaGradients.Primary, width = 1.8.dp)
    } else {
      CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE5E7EB)))
    },
    elevation = CardDefaults.cardElevation(if (isSelected) 3.dp else 1.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      RadioButton(
        selected = isSelected,
        onClick = onSelect,
        colors = RadioButtonDefaults.colors(selectedColor = InstaPink)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(if (isSelected) InstaPink.copy(alpha = 0.15f) else Color(0xFFF3F4F6)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = if (isSelected) InstaPink else BrandPurplePrimary,
          modifier = Modifier.size(20.dp)
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = BrandTextPrimaryLight
          )
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall.copy(
            color = BrandTextSecondaryLight,
            fontSize = 11.sp
          )
        )
      }
    }
  }
}
