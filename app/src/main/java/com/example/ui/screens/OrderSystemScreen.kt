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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MarketingService
import com.example.model.ServiceTier
import com.example.ui.components.InstagramGradientButton
import com.example.ui.theme.BrandPinkAccent
import com.example.ui.theme.BrandPurplePrimary
import com.example.ui.theme.BrandTextPrimaryLight
import com.example.ui.theme.BrandTextSecondaryLight
import com.example.ui.theme.InstaGradients
import com.example.ui.theme.InstaPink
import com.example.ui.viewmodel.InstaBoostViewModel

@Composable
fun OrderSystemScreen(
  viewModel: InstaBoostViewModel,
  modifier: Modifier = Modifier
) {
  val service by viewModel.selectedService.collectAsState()
  val tier by viewModel.selectedTier.collectAsState()

  val instagramUrl by viewModel.orderInstagramUrl.collectAsState()
  val customerName by viewModel.orderCustomerName.collectAsState()
  val customerEmail by viewModel.orderCustomerEmail.collectAsState()
  val customerPhone by viewModel.orderCustomerPhone.collectAsState()
  val targetNiche by viewModel.orderTargetNiche.collectAsState()
  val notes by viewModel.orderNotes.collectAsState()
  val formError by viewModel.orderFormError.collectAsState()

  if (service == null || tier == null) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      Text("Please select a service first")
    }
    return
  }

  val activeService = service!!
  val activeTier = tier!!

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(16.dp)
  ) {
    item {
      Text(
        text = "Campaign Order Enquiry",
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          color = BrandTextPrimaryLight,
          fontSize = 24.sp
        )
      )
      Text(
        text = "Submit your profile and target goals to launch your promotion.",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = BrandTextSecondaryLight
        )
      )
      Spacer(modifier = Modifier.height(14.dp))
    }

    // 1. Strict Security & Zero Password Guarantee Alert
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFCA5A5))
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
              .background(Color(0xFFEF4444).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "Password Security",
              tint = Color(0xFFDC2626),
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Account Security Guarantee",
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF991B1B)
              )
            )
            Text(
              text = "We will NEVER ask for your Instagram password. Only provide your public profile or reel link.",
              style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF7F1D1D)
              )
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // 2. Selected Service & Package Summary
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
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
              Text(
                text = "Selected Package",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = BrandTextSecondaryLight
                )
              )
              Text(
                text = activeService.title,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = BrandTextPrimaryLight
                )
              )
              Text(
                text = "${activeTier.name} • ${activeTier.estimatedReach}",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = InstaPink,
                  fontWeight = FontWeight.SemiBold
                )
              )
            }
            Text(
              text = "₹${activeTier.priceInr}",
              style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Black,
                color = BrandPurplePrimary
              )
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // 3. Instagram Details Form
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "1. Instagram Profile / Reel Details",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BrandTextPrimaryLight
            )
          )
          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = instagramUrl,
            onValueChange = { viewModel.orderInstagramUrl.value = it },
            label = { Text("Instagram Profile URL or @handle") },
            placeholder = { Text("https://instagram.com/yourhandle or @yourhandle") },
            leadingIcon = {
              Icon(Icons.Default.Link, contentDescription = null, tint = InstaPink)
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_instagram_url"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = InstaPink,
              unfocusedBorderColor = Color(0xFFD1D5DB)
            ),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Make sure your account is set to PUBLIC so reach is measurable.",
            style = MaterialTheme.typography.labelSmall.copy(
              color = BrandTextSecondaryLight,
              fontSize = 11.sp
            )
          )

          Spacer(modifier = Modifier.height(14.dp))

          OutlinedTextField(
            value = targetNiche,
            onValueChange = { viewModel.orderTargetNiche.value = it },
            label = { Text("Content Niche / Target Audience") },
            placeholder = { Text("e.g. Fitness, Fashion, Tech, Small Business") },
            leadingIcon = {
              Icon(Icons.Default.Tag, contentDescription = null, tint = BrandPurplePrimary)
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_target_niche"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = InstaPink,
              unfocusedBorderColor = Color(0xFFD1D5DB)
            ),
            singleLine = true
          )
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // 4. Contact Details
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "2. Contact & Campaign Updates",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BrandTextPrimaryLight
            )
          )
          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = customerName,
            onValueChange = { viewModel.orderCustomerName.value = it },
            label = { Text("Full Name") },
            leadingIcon = {
              Icon(Icons.Default.Person, contentDescription = null, tint = BrandPurplePrimary)
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_customer_name"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = InstaPink,
              unfocusedBorderColor = Color(0xFFD1D5DB)
            ),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = customerEmail,
            onValueChange = { viewModel.orderCustomerEmail.value = it },
            label = { Text("Email Address (for campaign reports)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {
              Icon(Icons.Default.AlternateEmail, contentDescription = null, tint = BrandPurplePrimary)
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_customer_email"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = InstaPink,
              unfocusedBorderColor = Color(0xFFD1D5DB)
            ),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = customerPhone,
            onValueChange = { viewModel.orderCustomerPhone.value = it },
            label = { Text("WhatsApp / Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            leadingIcon = {
              Icon(Icons.Default.Phone, contentDescription = null, tint = BrandPurplePrimary)
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_customer_phone"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = InstaPink,
              unfocusedBorderColor = Color(0xFFD1D5DB)
            ),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = notes,
            onValueChange = { viewModel.orderNotes.value = it },
            label = { Text("Specific Instructions (Optional)") },
            placeholder = { Text("Any specific reel URL, target cities or age groups...") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_order_notes"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = InstaPink,
              unfocusedBorderColor = Color(0xFFD1D5DB)
            ),
            minLines = 2
          )
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // 5. Price Breakdown & Order Summary
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE5E7EB))
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Package Price", style = MaterialTheme.typography.bodyMedium.copy(color = BrandTextSecondaryLight))
            Text("₹${activeTier.originalPriceInr}", style = MaterialTheme.typography.bodyMedium.copy(
              color = BrandTextSecondaryLight,
              textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
            ))
          }
          Spacer(modifier = Modifier.height(4.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Launch Promotion Discount", style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF10B981)))
            Text("-₹${activeTier.originalPriceInr - activeTier.priceInr}", style = MaterialTheme.typography.bodyMedium.copy(
              color = Color(0xFF10B981),
              fontWeight = FontWeight.Bold
            ))
          }
          Spacer(modifier = Modifier.height(6.dp))
          Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFE5E7EB)))
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("Total Amount", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = BrandTextPrimaryLight))
            Text(
              "₹${activeTier.priceInr}",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Black,
                color = BrandPurplePrimary
              )
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Error message display
    item {
      AnimatedVisibility(visible = formError != null) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFEE2E2))
            .padding(12.dp)
        ) {
          Text(
            text = formError ?: "",
            style = MaterialTheme.typography.bodySmall.copy(
              color = Color(0xFFDC2626),
              fontWeight = FontWeight.SemiBold
            )
          )
        }
      }
      if (formError != null) Spacer(modifier = Modifier.height(12.dp))
    }

    // Submit button
    item {
      InstagramGradientButton(
        text = "Proceed to Secure Payment (Test Mode)",
        onClick = { viewModel.submitOrderEnquiry() },
        testTag = "submit_enquiry_btn"
      )
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
