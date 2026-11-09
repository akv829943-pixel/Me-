package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleData
import com.example.data.local.InstaBoostDatabase
import com.example.data.local.OrderEntity
import com.example.model.MarketingService
import com.example.model.OrderStatus
import com.example.model.ServiceCategory
import com.example.model.ServiceTier
import com.example.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

enum class AppScreen {
  HOME,
  SERVICES,
  SERVICE_DETAIL,
  ORDER_FORM,
  PAYMENT,
  ORDER_SUCCESS,
  TRACK_ORDER,
  ACCOUNT,
  ADMIN
}

class InstaBoostViewModel(application: Application) : AndroidViewModel(application) {

  private val database = InstaBoostDatabase.getDatabase(application)
  private val orderDao = database.orderDao()

  // Navigation state
  private val _currentScreen = MutableStateFlow(AppScreen.HOME)
  val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

  // Back stack for custom navigation
  private val screenStack = mutableListOf(AppScreen.HOME)

  // Current selected service & tier
  private val _selectedService = MutableStateFlow<MarketingService?>(SampleData.services.first())
  val selectedService: StateFlow<MarketingService?> = _selectedService.asStateFlow()

  private val _selectedTier = MutableStateFlow<ServiceTier?>(SampleData.services.first().proTier)
  val selectedTier: StateFlow<ServiceTier?> = _selectedTier.asStateFlow()

  // Service filter
  private val _selectedCategory = MutableStateFlow(ServiceCategory.ALL)
  val selectedCategory: StateFlow<ServiceCategory> = _selectedCategory.asStateFlow()

  // User state
  private val _currentUser = MutableStateFlow(
    UserProfile(
      id = "usr_101",
      name = "Avanish Verma",
      email = "avanishverma858@gmail.com",
      phone = "+91 98765 12345",
      instagramHandle = "@avanish_creator",
      isAdmin = false
    )
  )
  val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

  // Orders flow from Room
  val allOrders: StateFlow<List<OrderEntity>> = orderDao.getAllOrders()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Order submission form states
  val orderInstagramUrl = MutableStateFlow("https://instagram.com/avanish_creator")
  val orderCustomerName = MutableStateFlow("Avanish Verma")
  val orderCustomerEmail = MutableStateFlow("avanishverma858@gmail.com")
  val orderCustomerPhone = MutableStateFlow("+91 98765 12345")
  val orderTargetNiche = MutableStateFlow("Creator & Tech Growth")
  val orderNotes = MutableStateFlow("")
  val orderFormError = MutableStateFlow<String?>(null)

  // Active order for payment & confirmation
  private val _activePendingOrder = MutableStateFlow<OrderEntity?>(null)
  val activePendingOrder: StateFlow<OrderEntity?> = _activePendingOrder.asStateFlow()

  // Payment states
  private val _selectedPaymentMethod = MutableStateFlow("UPI_GPAY")
  val selectedPaymentMethod: StateFlow<String> = _selectedPaymentMethod.asStateFlow()

  private val _isPaymentProcessing = MutableStateFlow(false)
  val isPaymentProcessing: StateFlow<Boolean> = _isPaymentProcessing.asStateFlow()

  // Track order state
  val trackSearchQuery = MutableStateFlow("")
  private val _trackedOrder = MutableStateFlow<OrderEntity?>(null)
  val trackedOrder: StateFlow<OrderEntity?> = _trackedOrder.asStateFlow()

  // Admin filter & search
  val adminSearchQuery = MutableStateFlow("")
  val adminStatusFilter = MutableStateFlow<OrderStatus?>(null)

  // Growth calculator
  val targetFollowersGoal = MutableStateFlow(10000)
  val calculatorNiche = MutableStateFlow("Fashion & Lifestyle")

  init {
    seedInitialDataIfNeeded()
  }

  private fun seedInitialDataIfNeeded() {
    viewModelScope.launch {
      val count = orderDao.getOrderCount()
      if (count == 0) {
        orderDao.insertAll(SampleData.sampleOrders)
      }
    }
  }

  // Navigation helpers
  fun navigateTo(screen: AppScreen) {
    if (_currentScreen.value != screen) {
      screenStack.add(screen)
      _currentScreen.value = screen
    }
  }

  fun navigateBack(): Boolean {
    if (screenStack.size > 1) {
      screenStack.removeAt(screenStack.size - 1)
      val prev = screenStack.last()
      _currentScreen.value = prev
      return true
    } else if (_currentScreen.value != AppScreen.HOME) {
      _currentScreen.value = AppScreen.HOME
      screenStack.clear()
      screenStack.add(AppScreen.HOME)
      return true
    }
    return false
  }

  fun openServiceDetail(service: MarketingService) {
    _selectedService.value = service
    _selectedTier.value = service.proTier
    navigateTo(AppScreen.SERVICE_DETAIL)
  }

  fun openOrderForm(service: MarketingService, tier: ServiceTier? = null) {
    _selectedService.value = service
    _selectedTier.value = tier ?: service.proTier
    orderFormError.value = null
    // Sync contact fields with current user
    orderCustomerName.value = _currentUser.value.name
    orderCustomerEmail.value = _currentUser.value.email
    orderCustomerPhone.value = _currentUser.value.phone
    orderInstagramUrl.value = "https://instagram.com/" + _currentUser.value.instagramHandle.removePrefix("@")
    navigateTo(AppScreen.ORDER_FORM)
  }

  fun setSelectedCategory(category: ServiceCategory) {
    _selectedCategory.value = category
  }

  fun setSelectedTier(tier: ServiceTier) {
    _selectedTier.value = tier
  }

  fun setSelectedPaymentMethod(method: String) {
    _selectedPaymentMethod.value = method
  }

  fun toggleAdminMode() {
    val current = _currentUser.value
    _currentUser.value = current.copy(isAdmin = !current.isAdmin)
    if (_currentUser.value.isAdmin) {
      navigateTo(AppScreen.ADMIN)
    } else {
      navigateTo(AppScreen.ACCOUNT)
    }
  }

  fun updateUserProfile(name: String, email: String, phone: String, handle: String) {
    _currentUser.value = _currentUser.value.copy(
      name = name.trim(),
      email = email.trim(),
      phone = phone.trim(),
      instagramHandle = if (handle.startsWith("@")) handle.trim() else "@${handle.trim()}"
    )
  }

  fun submitOrderEnquiry(): Boolean {
    val service = _selectedService.value ?: return false
    val tier = _selectedTier.value ?: return false

    val url = orderInstagramUrl.value.trim()
    val name = orderCustomerName.value.trim()
    val email = orderCustomerEmail.value.trim()
    val phone = orderCustomerPhone.value.trim()

    if (url.isEmpty() || (!url.contains("instagram.com") && !url.startsWith("@"))) {
      orderFormError.value = "Please enter a valid Instagram profile URL (e.g., https://instagram.com/yourhandle) or @handle"
      return false
    }

    if (name.isEmpty()) {
      orderFormError.value = "Please enter your full name"
      return false
    }

    if (email.isEmpty() || !email.contains("@")) {
      orderFormError.value = "Please enter a valid email address"
      return false
    }

    if (phone.isEmpty()) {
      orderFormError.value = "Please enter your WhatsApp/phone number"
      return false
    }

    orderFormError.value = null

    // Generate unique reference number: IB-2026-XXXX
    val randomSuffix = (1000..9999).random()
    val referenceNo = "IB-2026-$randomSuffix"

    val newOrder = OrderEntity(
      referenceNo = referenceNo,
      serviceId = service.id,
      serviceName = service.title,
      tierName = tier.name,
      amountInr = tier.priceInr,
      instagramUrl = url,
      customerName = name,
      customerEmail = email,
      customerPhone = phone,
      targetNiche = orderTargetNiche.value.ifBlank { "General Growth" },
      status = OrderStatus.SUBMITTED.name,
      paymentMethod = "PENDING_TEST_SELECTION",
      paymentRef = "PENDING",
      createdAt = System.currentTimeMillis(),
      notes = orderNotes.value.trim()
    )

    _activePendingOrder.value = newOrder
    navigateTo(AppScreen.PAYMENT)
    return true
  }

  fun processTestPayment() {
    val pending = _activePendingOrder.value ?: return
    viewModelScope.launch {
      _isPaymentProcessing.value = true
      kotlinx.coroutines.delay(1200) // realistic smooth animation

      val txnRef = "TEST-TXN-${(100000..999999).random()}"
      val verifiedOrder = pending.copy(
        status = OrderStatus.PAYMENT_VERIFIED.name,
        paymentMethod = _selectedPaymentMethod.value,
        paymentRef = txnRef
      )

      val insertedId = orderDao.insertOrder(verifiedOrder)
      val savedOrder = verifiedOrder.copy(id = insertedId)

      _activePendingOrder.value = savedOrder
      _trackedOrder.value = savedOrder
      _isPaymentProcessing.value = false
      navigateTo(AppScreen.ORDER_SUCCESS)
    }
  }

  fun trackOrder(query: String) {
    trackSearchQuery.value = query
    val clean = query.trim()
    if (clean.isEmpty()) return

    viewModelScope.launch {
      // Check by reference
      val foundByRef = orderDao.getOrderByReference(clean)
      if (foundByRef != null) {
        _trackedOrder.value = foundByRef
        return@launch
      }

      // Check in current loaded orders by handle or id
      val currentList = allOrders.value
      val match = currentList.firstOrNull {
        it.referenceNo.equals(clean, ignoreCase = true) ||
        it.instagramUrl.contains(clean, ignoreCase = true) ||
        it.customerEmail.equals(clean, ignoreCase = true) ||
        it.id.toString() == clean
      }
      _trackedOrder.value = match
    }
  }

  fun selectOrderToTrack(order: OrderEntity) {
    _trackedOrder.value = order
    trackSearchQuery.value = order.referenceNo
    navigateTo(AppScreen.TRACK_ORDER)
  }

  fun updateOrderStatusByAdmin(orderId: Long, newStatus: OrderStatus) {
    viewModelScope.launch {
      orderDao.updateOrderStatus(orderId, newStatus.name)
      // Update tracked order if matches
      if (_trackedOrder.value?.id == orderId) {
        _trackedOrder.value = _trackedOrder.value?.copy(status = newStatus.name)
      }
    }
  }

  fun deleteOrderByAdmin(orderId: Long) {
    viewModelScope.launch {
      orderDao.deleteOrder(orderId)
    }
  }
}
