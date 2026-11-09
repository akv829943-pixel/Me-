package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
  @Query("SELECT * FROM orders ORDER BY createdAt DESC")
  fun getAllOrders(): Flow<List<OrderEntity>>

  @Query("SELECT * FROM orders WHERE referenceNo = :ref LIMIT 1")
  suspend fun getOrderByReference(ref: String): OrderEntity?

  @Query("SELECT * FROM orders WHERE customerEmail = :email ORDER BY createdAt DESC")
  fun getOrdersByEmail(email: String): Flow<List<OrderEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrder(order: OrderEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(orders: List<OrderEntity>)

  @Update
  suspend fun updateOrder(order: OrderEntity)

  @Query("UPDATE orders SET status = :newStatus WHERE id = :orderId")
  suspend fun updateOrderStatus(orderId: Long, newStatus: String)

  @Query("DELETE FROM orders WHERE id = :orderId")
  suspend fun deleteOrder(orderId: Long)

  @Query("SELECT COUNT(*) FROM orders")
  suspend fun getOrderCount(): Int
}
