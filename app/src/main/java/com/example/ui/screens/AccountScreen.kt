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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
fun AccountScreen(
  viewModel: InstaBoostViewModel,
  modifier: Modifier = Modifier
) {
  val user by viewModel.currentUser.collectAsState()
  val allOrders by viewModel.allOrders.collectAsState()

  // Filter orders related to current user
  val myOrders = allOrders.filter {
    it.customerEmail.equals(user.email, ignoreCase = true) ||
    it.instagramUrl.contains(user.instagramHandle.removePrefix("@"), ignoreCase = true)
  }.ifEmpty { allOrders.take(3) } // Fallback to showcase if user just signed up

  var isEditingProfile by remember { mutableStateOf(false) }
  var editName by remember(user) { mutableStateOf(user.name) }
  var editEmail by remember(user) { mutableStateOf(user.email) }
  var editPhone by remember(user) { mutableStateOf(user.phone) }
  var editHandle by remember(user) { mutableStateOf(user.instagramHandle) }

  var selectedTab by remember { mutableStateOf(0) } // 0: Dashboard/Profile, 1: Sign in / Register

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(16.dp)
  ) {
    // Header & Tabs
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Creator Account",
            style = MaterialTheme.typography.displayMedium.copy(
              fontWeight = FontWeight.Black,
              color = BrandTextPrimaryLight,
              fontSize = 24.sp
            )
          )
          Text(
            text = "Manage your profile, active campaigns, and login credentials",
            style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.White,
        contentColor = InstaPink,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = InstaPink
          )
        }
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = { Text("My Dashboard & Orders", fontWeight = FontWeight.Bold) },
          modifier = Modifier.testTag("tab_account_dashboard")
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = { Text("Auth & Sign Up", fontWeight = FontWeight.Bold) },
          modifier = Modifier.testTag("tab_account_auth")
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    if (selectedTab == 0) {
      // 1. Profile Card
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.5.dp)
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(InstaGradients.Primary),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = user.name.take(1).uppercase(),
                    style = MaterialTheme.typography.titleLarge.copy(
                      color = Color.White,
                      fontWeight = FontWeight.Black
                    )
                  )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = BrandTextPrimaryLight
                    )
                  )
                  Text(
                    text = user.instagramHandle,
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = InstaPink,
                      fontWeight = FontWeight.Bold
                    )
                  )
                }
              }

              IconButton(onClick = { isEditingProfile = !isEditingProfile }) {
                Icon(
                  imageVector = if (isEditingProfile) Icons.Default.Save else Icons.Default.Edit,
                  contentDescription = "Edit Profile",
                  tint = BrandPurplePrimary
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (isEditingProfile) {
              Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                  value = editName,
                  onValueChange = { editName = it },
                  label = { Text("Name") },
                  modifier = Modifier.fillMaxWidth(),
                  shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                  value = editEmail,
                  onValueChange = { editEmail = it },
                  label = { Text("Email") },
                  modifier = Modifier.fillMaxWidth(),
                  shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                  value = editPhone,
                  onValueChange = { editPhone = it },
                  label = { Text("Phone") },
                  modifier = Modifier.fillMaxWidth(),
                  shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                  value = editHandle,
                  onValueChange = { editHandle = it },
                  label = { Text("Default Instagram Handle") },
                  modifier = Modifier.fillMaxWidth(),
                  shape = RoundedCornerShape(10.dp)
                )

                Button(
                  onClick = {
                    viewModel.updateUserProfile(editName, editEmail, editPhone, editHandle)
                    isEditingProfile = false
                  },
                  modifier = Modifier.fillMaxWidth(),
                  colors = ButtonDefaults.buttonColors(containerColor = BrandPurplePrimary),
                  shape = RoundedCornerShape(10.dp)
                ) {
                  Text("Save Profile Changes")
                }
              }
            } else {
              Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(Icons.Default.AlternateEmail, contentDescription = null, tint = BrandTextSecondaryLight, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(user.email, style = MaterialTheme.typography.bodySmall.copy(color = BrandTextPrimaryLight))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(Icons.Default.Phone, contentDescription = null, tint = BrandTextSecondaryLight, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(user.phone, style = MaterialTheme.typography.bodySmall.copy(color = BrandTextPrimaryLight))
                }
              }
            }
          }
        }
        Spacer(modifier = Modifier.height(16.dp))
      }

      // 2. Admin Switcher Banner
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.toggleAdminMode() }
            .testTag("admin_switch_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F3FF)),
          border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFDDD6FE))
          )
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BrandPurplePrimary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = null,
                tint = BrandPurplePrimary,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Open Admin Dashboard Console",
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = BrandPurplePrimary
                )
              )
              Text(
                text = "Manage incoming enquiries, update order statuses, and view revenue analytics.",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = BrandTextSecondaryLight,
                  fontSize = 11.sp
                )
              )
            }
          }
        }
        Spacer(modifier = Modifier.height(18.dp))
      }

      // 3. Order History Section
      item {
        Text(
          text = "My Campaign Order History",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = BrandTextPrimaryLight
          )
        )
        Text(
          text = "Track live campaign progress and view receipts",
          style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight)
        )
        Spacer(modifier = Modifier.height(10.dp))
      }

      items(myOrders) { ord ->
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { viewModel.selectOrderToTrack(ord) }
            .testTag("my_order_item_${ord.referenceNo}"),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(1.5.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = ord.referenceNo,
                  style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Black,
                    color = BrandPurplePrimary
                  )
                )
                Text(
                  text = ord.serviceName,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = BrandTextPrimaryLight
                  )
                )
              }
              OrderStatusBadge(status = ord.orderStatus)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "${ord.tierName} • ₹${ord.amountInr}",
                style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight)
              )
              Text(
                text = "View Status →",
                style = MaterialTheme.typography.labelSmall.copy(color = InstaPink, fontWeight = FontWeight.Bold)
              )
            }
          }
        }
      }
    } else {
      // Sign Up and Login Tab
      item {
        AuthFormSection(
          onLoginSuccess = { selectedTab = 0 }
        )
      }
    }
  }
}

@Composable
fun AuthFormSection(
  onLoginSuccess: () -> Unit
) {
  var isSignUp by remember { mutableStateOf(false) }
  var name by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var instagramHandle by remember { mutableStateOf("") }
  var message by remember { mutableStateOf<String?>(null) }

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(2.5.dp)
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Text(
        text = if (isSignUp) "Create Creator Account" else "Sign In to InstaBoost",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = BrandTextPrimaryLight
        )
      )
      Text(
        text = if (isSignUp) "Sign up to track and manage all your Instagram promotions in one place." else "Welcome back! Enter your login credentials.",
        style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight)
      )

      Spacer(modifier = Modifier.height(14.dp))

      if (isSignUp) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Full Name") },
          leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = instagramHandle,
          onValueChange = { instagramHandle = it },
          label = { Text("Instagram Handle (e.g. @yourpage)") },
          leadingIcon = { Icon(Icons.Default.AlternateEmail, contentDescription = null) },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = phone,
          onValueChange = { phone = it },
          label = { Text("Phone Number") },
          leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
      }

      OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email Address") },
        leadingIcon = { Icon(Icons.Default.AlternateEmail, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
      )

      Spacer(modifier = Modifier.height(10.dp))

      OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password (InstaBoost App Account)") },
        visualTransformation = PasswordVisualTransformation(),
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
      )

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Note: This password is for your InstaBoost account only. We NEVER ask for your Instagram password.",
        style = MaterialTheme.typography.labelSmall.copy(color = BrandTextSecondaryLight, fontSize = 10.sp)
      )

      Spacer(modifier = Modifier.height(16.dp))

      if (message != null) {
        Text(
          text = message!!,
          style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))
      }

      InstagramGradientButton(
        text = if (isSignUp) "Complete Sign Up" else "Log In",
        onClick = {
          message = if (isSignUp) "Account created successfully! Redirecting..." else "Logged in successfully!"
          onLoginSuccess()
        },
        testTag = "auth_submit_btn"
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          text = if (isSignUp) "Already have an account? " else "Don't have an account yet? ",
          style = MaterialTheme.typography.bodySmall.copy(color = BrandTextSecondaryLight)
        )
        Text(
          text = if (isSignUp) "Log In" else "Sign Up",
          style = MaterialTheme.typography.bodySmall.copy(
            color = InstaPink,
            fontWeight = FontWeight.Bold
          ),
          modifier = Modifier.clickable {
            isSignUp = !isSignUp
            message = null
          }
        )
      }
    }
  }
}
