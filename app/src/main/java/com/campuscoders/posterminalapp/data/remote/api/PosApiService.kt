package com.campuscoders.posterminalapp.data.remote.api

import com.campuscoders.posterminalapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Extended API Service for POS Terminal Application
 * 
 * Note: These endpoints are suggested based on local database architecture.
 * Backend team needs to implement these endpoints on the server.
 * 
 * All endpoints (except login) require access_token for authentication.
 */
interface PosApiService {
    
    // ==================== Authentication ====================
    
    @GET("login")
    suspend fun login(
        @Query("terminalid") terminalId: String,
        @Query("taxid") taxId: String,
        @Query("memberid") memberId: String,
        @Query("password") password: String
    ): Response<LoginResponse>
    
    // ==================== Terminal Users ====================
    
    /**
     * Get all terminal users (cashiers) for a merchant store
     * Backend endpoint: GET /terminal-users?memberid={memberid}&access_token={token}
     */
    @GET("terminal-users")
    suspend fun getTerminalUsers(
        @Query("memberid") memberId: String,
        @Query("access_token") accessToken: String
    ): Response<TerminalUsersResponse>
    
    /**
     * Get single terminal user by terminal ID
     * Backend endpoint: GET /terminal-users/{terminal_id}?access_token={token}
     */
    @GET("terminal-users/{terminal_id}")
    suspend fun getTerminalUserById(
        @Path("terminal_id") terminalId: String,
        @Query("access_token") accessToken: String
    ): Response<TerminalUsersResponse>
    
    // ==================== Categories ====================
    
    /**
     * Get all product categories
     * Backend endpoint: GET /categories?memberid={memberid}&access_token={token}
     */
    @GET("categories")
    suspend fun getCategories(
        @Query("memberid") memberId: String,
        @Query("access_token") accessToken: String
    ): Response<CategoriesResponse>
    
    /**
     * Get category by ID
     * Backend endpoint: GET /categories/{category_id}?access_token={token}
     */
    @GET("categories/{category_id}")
    suspend fun getCategoryById(
        @Path("category_id") categoryId: Int,
        @Query("access_token") accessToken: String
    ): Response<CategoryData>
    
    // ==================== Products ====================
    
    /**
     * Get all products, optionally filtered by category
     * Backend endpoint: GET /products?memberid={memberid}&access_token={token}&category_id={category_id}
     */
    @GET("products")
    suspend fun getProducts(
        @Query("memberid") memberId: String,
        @Query("access_token") accessToken: String,
        @Query("category_id") categoryId: Int? = null
    ): Response<ProductsResponse>
    
    /**
     * Get product by barcode (for scanning)
     * Backend endpoint: GET /products/barcode/{barcode}?access_token={token}
     */
    @GET("products/barcode/{barcode}")
    suspend fun getProductByBarcode(
        @Path("barcode") barcode: String,
        @Query("access_token") accessToken: String
    ): Response<ProductData>
    
    /**
     * Get product by ID
     * Backend endpoint: GET /products/{product_id}?access_token={token}
     */
    @GET("products/{product_id}")
    suspend fun getProductById(
        @Path("product_id") productId: Int,
        @Query("access_token") accessToken: String
    ): Response<ProductData>
    
    // ==================== Customers ====================
    
    /**
     * Get all customers
     * Backend endpoint: GET /customers?memberid={memberid}&access_token={token}
     */
    @GET("customers")
    suspend fun getCustomers(
        @Query("memberid") memberId: String,
        @Query("access_token") accessToken: String
    ): Response<CustomersResponse>
    
    /**
     * Search customers by query
     * Backend endpoint: GET /customers/search?query={query}&access_token={token}
     */
    @GET("customers/search")
    suspend fun searchCustomers(
        @Query("query") searchQuery: String,
        @Query("access_token") accessToken: String
    ): Response<CustomersResponse>
    
    /**
     * Get customer by ID
     * Backend endpoint: GET /customers/{customer_id}?access_token={token}
     */
    @GET("customers/{customer_id}")
    suspend fun getCustomerById(
        @Path("customer_id") customerId: Int,
        @Query("access_token") accessToken: String
    ): Response<CustomerData>
    
    // ==================== Orders ====================
    
    /**
     * Get all orders with optional filters
     * Backend endpoint: GET /orders?memberid={memberid}&access_token={token}&start_date={date}&end_date={date}&status={status}
     */
    @GET("orders")
    suspend fun getOrders(
        @Query("memberid") memberId: String,
        @Query("access_token") accessToken: String,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("status") status: String? = null
    ): Response<OrdersResponse>
    
    /**
     * Get order by ID with line items
     * Backend endpoint: GET /orders/{order_id}?access_token={token}
     */
    @GET("orders/{order_id}")
    suspend fun getOrderById(
        @Path("order_id") orderId: Int,
        @Query("access_token") accessToken: String
    ): Response<OrderData>
    
    /**
     * Get orders by date range
     * Backend endpoint: GET /orders/date-range?start_date={date}&end_date={date}&access_token={token}
     */
    @GET("orders/date-range")
    suspend fun getOrdersByDateRange(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("access_token") accessToken: String
    ): Response<OrdersResponse>
}
