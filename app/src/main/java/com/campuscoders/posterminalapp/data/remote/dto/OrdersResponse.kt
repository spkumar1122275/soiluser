package com.campuscoders.posterminalapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OrdersResponse(
    @SerializedName("orders")
    val orders: List<OrderData>? = null
)

data class OrderData(
    @SerializedName("order_id")
    val orderId: Int? = null,
    
    @SerializedName("order_customer_id")
    val orderCustomerId: String? = null,
    
    @SerializedName("order_receipt_type")
    val orderReceiptType: String? = null,
    
    @SerializedName("order_payment_type")
    val orderPaymentType: String? = null,
    
    @SerializedName("order_date")
    val orderDate: String? = null,
    
    @SerializedName("order_time")
    val orderTime: String? = null,
    
    @SerializedName("order_status")
    val orderStatus: String? = null,
    
    @SerializedName("order_receipt_no")
    val orderReceiptNo: String? = null,
    
    @SerializedName("order_mali_id")
    val orderMaliId: String? = null,
    
    @SerializedName("order_terminal_id")
    val orderTerminalId: String? = null,
    
    @SerializedName("order_uye_isyeri_no")
    val orderUyeIsyeriNo: String? = null,
    
    @SerializedName("order_ettn")
    val orderETTN: String? = null,
    
    @SerializedName("order_order_no_backend")
    val orderOrderNoBackend: String? = null,
    
    @SerializedName("order_total")
    val orderTotal: String? = null,
    
    @SerializedName("order_total_tax")
    val orderTotalTax: String? = null,
    
    @SerializedName("order_timestamp")
    val orderTimestamp: Long? = null,
    
    @SerializedName("order_items")
    val orderItems: List<OrderItemData>? = null
)

data class OrderItemData(
    @SerializedName("product_id")
    val productId: Int? = null,
    
    @SerializedName("product_name")
    val productName: String? = null,
    
    @SerializedName("quantity")
    val quantity: String? = null,
    
    @SerializedName("unit_price")
    val unitPrice: String? = null,
    
    @SerializedName("total_price")
    val totalPrice: String? = null,
    
    @SerializedName("tax_amount")
    val taxAmount: String? = null
)
