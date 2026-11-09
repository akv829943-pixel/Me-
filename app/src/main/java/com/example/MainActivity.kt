package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.components.InstaBoostBottomBar
import com.example.ui.components.InstaBoostTopBar
import com.example.ui.screens.AccountScreen
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderSuccessScreen
import com.example.ui.screens.OrderSystemScreen
import com.example.ui.screens.PaymentScreen
import com.example.ui.screens.ServiceDetailScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.screens.TrackOrderScreen
import com.example.ui.theme.InstaBoostTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.InstaBoostViewModel

class MainActivity : ComponentActivity() {

  private val viewModel: InstaBoostViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      InstaBoostTheme {
        InstaBoostApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun InstaBoostApp(viewModel: InstaBoostViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val currentUser by viewModel.currentUser.collectAsState()

  // Handle Android hardware/gesture back navigation
  BackHandler(enabled = currentScreen != AppScreen.HOME) {
    viewModel.navigateBack()
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      InstaBoostTopBar(
        currentScreen = currentScreen,
        isAdmin = currentUser.isAdmin,
        onBackClick = { viewModel.navigateBack() },
        onAdminToggle = { viewModel.toggleAdminMode() },
        onAccountClick = { viewModel.navigateTo(AppScreen.ACCOUNT) }
      )
    },
    bottomBar = {
      InstaBoostBottomBar(
        currentScreen = currentScreen,
        isAdmin = currentUser.isAdmin,
        onNavigate = { screen -> viewModel.navigateTo(screen) }
      )
    }
  ) { innerPadding ->
    Crossfade(
      targetState = currentScreen,
      animationSpec = tween(durationMillis = 250),
      label = "screen_transition",
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) { screen ->
      when (screen) {
        AppScreen.HOME -> HomeScreen(viewModel = viewModel)
        AppScreen.SERVICES -> ServicesScreen(viewModel = viewModel)
        AppScreen.SERVICE_DETAIL -> ServiceDetailScreen(viewModel = viewModel)
        AppScreen.ORDER_FORM -> OrderSystemScreen(viewModel = viewModel)
        AppScreen.PAYMENT -> PaymentScreen(viewModel = viewModel)
        AppScreen.ORDER_SUCCESS -> OrderSuccessScreen(viewModel = viewModel)
        AppScreen.TRACK_ORDER -> TrackOrderScreen(viewModel = viewModel)
        AppScreen.ACCOUNT -> AccountScreen(viewModel = viewModel)
        AppScreen.ADMIN -> AdminDashboardScreen(viewModel = viewModel)
      }
    }
  }
}
