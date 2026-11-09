package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.OrderStatus
import com.example.ui.theme.BrandDeepMidnight
import com.example.ui.theme.BrandPinkAccent
import com.example.ui.theme.BrandPurplePrimary
import com.example.ui.theme.BrandSurfaceDark
import com.example.ui.theme.BrandTextPrimaryLight
import com.example.ui.theme.BrandTextSecondaryLight
import com.example.ui.theme.InstaGradients
import com.example.ui.theme.InstaMagenta
import com.example.ui.theme.InstaOrange
import com.example.ui.theme.InstaPink
import com.example.ui.theme.InstaPurple
import com.example.ui.theme.StatusCancelled
import com.example.ui.theme.StatusInfo
import com.example.ui.theme.StatusPurple
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.StatusWarning
import com.example.ui.viewmodel.AppScreen

@Composable
fun InstaBoostTopBar(
  currentScreen: AppScreen,
  isAdmin: Boolean,
  onBackClick: () -> Unit,
  onAdminToggle: () -> Unit,
  onAccountClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val showBack = currentScreen != AppScreen.HOME

  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(MaterialTheme.colorScheme.surface)
      .border(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      .padding(horizontal = 16.dp, vertical = 10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertornCenter,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (showBack) {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier
              .size(40.dp)
              .testTag("back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = MaterialTheme.colorScheme.primary
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
        }

        // Brand logo icon pill
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(InstaGradients.Primary),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.RocketLaunch,
            contentDescription = "InstaBoost Logo",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Insta",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Black,
                color = BrandTextPrimaryLight
              )
            )
            Text(
              text = "Boost",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Black,
                color = InstaPink
              )
            )
          }
          Text(
            text = "Organic Social Marketing",
            style = MaterialTheme.typography.labelSmall.copy(
              color = BrandTextSecondaryLight,
              fontSize = 10.sp
            )
          )
        }
      }

      // Right actions
      Row(verticalAlignment = Alignment.CenterVertically) {
        // Mode badge toggle
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
              if (isAdmin) BrandPurplePrimary.copy(alpha = 0.15f)
              else Color(0xFF10B981).copy(alpha = 0.12f)
            )
            .clickable(onClick = onAdminToggle)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("admin_mode_toggle"),
          contentAlignment = Alignment.Center
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = if (isAdmin) Icons.Default.AdminPanelSettings else Icons.Default.Person,
              contentDescription = "User Mode",
              modifier = Modifier.size(14.dp),
              tint = if (isAdmin) BrandPurplePrimary else Color(0xFF10B981)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (isAdmin) "Admin" else "Creator",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (isAdmin) BrandPurplePrimary else Color(0xFF047857)
              )
            )
          }
        }
      }
    }
  }
}

private val Alignment.Companion.CenterVertornCenter: Alignment.Vertical
  get() = Alignment.CenterVertically

@Composable
fun InstaBoostBottomBar(
  currentScreen: AppScreen,
  isAdmin: Boolean,
  onNavigate: (AppScreen) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    modifier = modifier
      .fillMaxWidth()
      .navigationBarsPadding(),
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 6.dp
  ) {
    NavigationBarItem(
      selected = currentScreen == AppScreen.HOME,
      onClick = { onNavigate(AppScreen.HOME) },
      icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
      label = { Text("Home") },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = InstaPink,
        selectedTextColor = InstaPink,
        indicatorColor = InstaPink.copy(alpha = 0.12f)
      ),
      modifier = Modifier.testTag("nav_home")
    )

    NavigationBarItem(
      selected = currentScreen == AppScreen.SERVICES || currentScreen == AppScreen.SERVICE_DETAIL,
      onClick = { onNavigate(AppScreen.SERVICES) },
      icon = { Icon(Icons.Default.ViewCarousel, contentDescription = "Services") },
      label = { Text("Services") },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = InstaPink,
        selectedTextColor = InstaPink,
        indicatorColor = InstaPink.copy(alpha = 0.12f)
      ),
      modifier = Modifier.testTag("nav_services")
    )

    NavigationBarItem(
      selected = currentScreen == AppScreen.TRACK_ORDER || currentScreen == AppScreen.ORDER_SUCCESS,
      onClick = { onNavigate(AppScreen.TRACK_ORDER) },
      icon = { Icon(Icons.Default.TrackChanges, contentDescription = "Track") },
      label = { Text("Track") },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = InstaPink,
        selectedTextColor = InstaPink,
        indicatorColor = InstaPink.copy(alpha = 0.12f)
      ),
      modifier = Modifier.testTag("nav_track")
    )

    NavigationBarItem(
      selected = currentScreen == AppScreen.ACCOUNT,
      onClick = { onNavigate(AppScreen.ACCOUNT) },
      icon = { Icon(Icons.Default.Person, contentDescription = "Account") },
      label = { Text("Account") },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = InstaPink,
        selectedTextColor = InstaPink,
        indicatorColor = InstaPink.copy(alpha = 0.12f)
      ),
      modifier = Modifier.testTag("nav_account")
    )

    if (isAdmin) {
      NavigationBarItem(
        selected = currentScreen == AppScreen.ADMIN,
        onClick = { onNavigate(AppScreen.ADMIN) },
        icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin") },
        label = { Text("Admin") },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = BrandPurplePrimary,
          selectedTextColor = BrandPurplePrimary,
          indicatorColor = BrandPurplePrimary.copy(alpha = 0.15f)
        ),
        modifier = Modifier.testTag("nav_admin")
      )
    }
  }
}

@Composable
fun InstagramGradientButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  isLoading: Boolean = false,
  icon: ImageVector? = null,
  enabled: Boolean = true,
  testTag: String = "gradient_button"
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(52.dp)
      .clip(RoundedCornerShape(26.dp))
      .background(
        if (enabled) InstaGradients.Primary else Brush.linearGradient(listOf(Color.Gray, Color.LightGray))
      )
      .clickable(enabled = enabled && !isLoading, onClick = onClick)
      .testTag(testTag),
    contentAlignment = Alignment.Center
  ) {
    if (isLoading) {
      CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = Color.White,
        strokeWidth = 2.5.dp
      )
    } else {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        if (icon != null) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
          text = text,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        )
      }
    }
  }
}

@Composable
fun PolicySafeGuaranteeCard(
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = Color(0xFFF9F5FF)
    ),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = Brush.horizontalGradient(
        listOf(InstaPurple.copy(alpha = 0.3f), InstaPink.copy(alpha = 0.3f))
      )
    )
  ) {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color(0xFF10B981).copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Security,
            contentDescription = "Security Guarantee",
            tint = Color(0xFF047857),
            modifier = Modifier.size(20.dp)
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "100% Policy-Safe & Organic Guarantee",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = BrandTextPrimaryLight
            )
          )
          Text(
            text = "Meta TOS Compliant • No Bots • No Passwords",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BrandTextSecondaryLight
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        GuaranteePill(
          icon = Icons.Default.Lock,
          text = "Zero Password Request",
          modifier = Modifier.weight(1f)
        )
        GuaranteePill(
          icon = Icons.Default.CheckCircle,
          text = "Real Human Reach",
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

@Composable
fun GuaranteePill(
  icon: ImageVector,
  text: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(Color.White)
      .padding(horizontal = 8.dp, vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = Color(0xFF047857),
      modifier = Modifier.size(14.dp)
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = text,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.SemiBold,
        color = BrandTextPrimaryLight,
        fontSize = 11.sp
      ),
      maxLines = 1
    )
  }
}

@Composable
fun OrderStatusBadge(
  status: OrderStatus,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor) = when (status) {
    OrderStatus.SUBMITTED -> StatusWarning.copy(alpha = 0.15f) to Color(0xFFB45309)
    OrderStatus.PAYMENT_VERIFIED -> StatusInfo.copy(alpha = 0.15f) to Color(0xFF0E7490)
    OrderStatus.STRATEGY_AUDIT -> StatusPurple.copy(alpha = 0.15f) to Color(0xFF7E22CE)
    OrderStatus.CAMPAIGN_ACTIVE -> InstaPink.copy(alpha = 0.15f) to Color(0xFFBE185D)
    OrderStatus.COMPLETED -> StatusSuccess.copy(alpha = 0.15f) to Color(0xFF047857)
    OrderStatus.CANCELLED -> StatusCancelled.copy(alpha = 0.15f) to Color(0xFFB91C1C)
  }

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .background(bgColor)
      .padding(horizontal = 10.dp, vertical = 4.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = status.label,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        color = textColor
      )
    )
  }
}
