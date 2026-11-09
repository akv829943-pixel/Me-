package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.OrderStatus

@Entity(tableName = "orders")
data class OrderEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val referenceNo: String,
  val serviceId: String,
  val serviceName: String,
  val tierName: String,
  val amountInr: Int,
  val instagramUrl: String,
  val customerName: String,
  val customerEmail: String,
  val customerPhone: String,
  val targetNiche: String,
  val status: String, // from OrderStatus.name
  val paymentMethod: String,
  val paymentRef: String,
  val createdAt: Long = System.currentTimeMillis(),
  val notes: String = ""
) {
  val orderStatus: OrderStatus
    get() = try {
      OrderStatus.valueOf(status)
    } catch (e: Exception) {
      OrderStatus.SUBMITTED
    }
}
