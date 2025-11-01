# Suggested API Endpoints Based on Local Database Architecture

## Current Implementation
✅ **Login/Authentication**
- **Endpoint**: `GET /login`
- **Query Params**: `terminalid`, `taxid`, `memberid`, `password`
- **Response**:
```json
{
  "user": {
    "terminal_id": "TN5690034001",
    "tax_id": "CHRPP1539H",
    "member_store": "5690004",
    "role": "main_user",
    "first_name": "PRAVEEN",
    "last_name": "KUMAR",
    "cellphone_number": "9952609929",
    "access_token": "50A7A8F00EC3191133CBFFFC8011E2C8A09FF981BDBEE6B089CB7CAC32260637"
  }
}
```

---

## Recommended Additional GET Endpoints

### 1. **Terminal Users Management**
#### Get All Terminal Users (Cashiers)
- **Endpoint**: `GET /terminal-users`
- **Query Params**: `memberid`, `access_token`
- **Purpose**: Fetch all cashiers/terminal users for a merchant store
- **Response Example**:
```json
{
  "terminal_users": [
    {
      "terminal_user_id": "TU001",
      "terminal_id": "TN5690034001",
      "tax_id": "CHRPP1539H",
      "member_store": "5690004",
      "full_name": "John Doe",
      "password": "hashed_password",
      "created_date": "2024-01-15",
      "created_time": "10:30:00",
      "permissions": {
        "iptal_iade": true,
        "tahsilat": true,
        "kasiyer_goruntuleme": true,
        "kasiyer_ekleme_duzenleme": false,
        "kasiyer_silme": false,
        "urun_goruntuleme": true,
        "urun_ekleme_duzenleme": false,
        "urun_silme": false,
        "tum_raporlari_goruntuleme": false,
        "rapor_kaydet_gonder": false,
        "pos_yonetimi": false,
        "admin": false
      }
    }
  ]
}
```

#### Get Single Terminal User
- **Endpoint**: `GET /terminal-users/{terminal_id}`
- **Query Params**: `access_token`

---

### 2. **Categories Management**
#### Get All Categories
- **Endpoint**: `GET /categories`
- **Query Params**: `memberid`, `access_token`
- **Purpose**: Fetch all product categories for the store
- **Response Example**:
```json
{
  "categories": [
    {
      "category_id": 1,
      "category_code": "CAT001",
      "category_name": "Electronics",
      "category_description": "Electronic items and gadgets",
      "category_image": "https://example.com/images/electronics.jpg"
    },
    {
      "category_id": 2,
      "category_code": "CAT002",
      "category_name": "Clothing",
      "category_description": "Apparel and accessories",
      "category_image": "https://example.com/images/clothing.jpg"
    }
  ]
}
```

#### Get Category by ID
- **Endpoint**: `GET /categories/{category_id}`
- **Query Params**: `access_token`

---

### 3. **Products Management**
#### Get All Products
- **Endpoint**: `GET /products`
- **Query Params**: `memberid`, `access_token`, `category_id` (optional)
- **Purpose**: Fetch all products, optionally filtered by category
- **Response Example**:
```json
{
  "products": [
    {
      "product_id": 1,
      "product_category_id": "1",
      "product_name": "Wireless Mouse",
      "product_code": "PROD001",
      "product_description": "Ergonomic wireless mouse",
      "product_image": "https://example.com/images/mouse.jpg",
      "product_barcode": "1234567890123",
      "product_price": "25.00",
      "product_price_cents": "00",
      "product_kdv": "18",
      "product_stappage": "0",
      "product_quantity": "100",
      "product_discount": "0",
      "product_exception_code": "",
      "product_exception_description": ""
    }
  ]
}
```

#### Get Product by Barcode
- **Endpoint**: `GET /products/barcode/{barcode}`
- **Query Params**: `access_token`
- **Purpose**: Quick product lookup during scanning

#### Get Product by ID
- **Endpoint**: `GET /products/{product_id}`
- **Query Params**: `access_token`

---

### 4. **Customers Management**
#### Get All Customers
- **Endpoint**: `GET /customers`
- **Query Params**: `memberid`, `access_token`
- **Purpose**: Fetch customer database for the store
- **Response Example**:
```json
{
  "customers": [
    {
      "customer_id": 1,
      "customer_vkn_tckn": "12345678901",
      "customer_company_name": "ABC Corp",
      "customer_first_name": "Ali",
      "customer_last_name": "Yılmaz",
      "customer_phone_number": "5551234567",
      "customer_email": "ali@example.com",
      "customer_province": "Istanbul",
      "customer_district": "Kadıköy",
      "customer_tax_office": "Kadıköy Vergi Dairesi",
      "customer_address": "Sample Street, No: 123"
    }
  ]
}
```

#### Search Customer
- **Endpoint**: `GET /customers/search`
- **Query Params**: `query`, `access_token`
- **Purpose**: Search by name, phone, or tax ID

#### Get Customer by ID
- **Endpoint**: `GET /customers/{customer_id}`
- **Query Params**: `access_token`

---

### 5. **Orders Management**
#### Get All Orders
- **Endpoint**: `GET /orders`
- **Query Params**: `memberid`, `access_token`, `start_date`, `end_date`, `status`
- **Purpose**: Fetch order history with filters
- **Response Example**:
```json
{
  "orders": [
    {
      "order_id": 1001,
      "order_customer_id": "1",
      "order_receipt_type": "E-Fatura",
      "order_payment_type": "Credit Card",
      "order_date": "2024-01-20",
      "order_time": "14:30:00",
      "order_status": "completed",
      "order_receipt_no": "RCP001",
      "order_mali_id": "MALI001",
      "order_terminal_id": "TN5690034001",
      "order_uye_isyeri_no": "5690004",
      "order_ettn": "ETTN123456",
      "order_order_no_backend": "ORD001",
      "order_total": "125.50",
      "order_total_tax": "22.59",
      "order_timestamp": 1705756200000,
      "order_items": [
        {
          "product_id": 1,
          "product_name": "Wireless Mouse",
          "quantity": 2,
          "unit_price": "25.00",
          "total_price": "50.00",
          "tax_amount": "9.00"
        }
      ]
    }
  ]
}
```

#### Get Order by ID
- **Endpoint**: `GET /orders/{order_id}`
- **Query Params**: `access_token`
- **Purpose**: Get detailed order with line items

#### Get Orders by Date Range
- **Endpoint**: `GET /orders/date-range`
- **Query Params**: `start_date`, `end_date`, `access_token`

---

### 6. **Reports & Analytics**
#### Daily Report
- **Endpoint**: `GET /reports/daily`
- **Query Params**: `date`, `memberid`, `access_token`
- **Purpose**: Daily sales summary
- **Response Example**:
```json
{
  "report_date": "2024-01-20",
  "total_sales": "5234.50",
  "total_tax": "942.21",
  "total_orders": 47,
  "payment_methods": {
    "cash": "1234.00",
    "credit_card": "3500.50",
    "debit_card": "500.00"
  },
  "top_products": [
    {
      "product_name": "Wireless Mouse",
      "quantity_sold": 25,
      "total_revenue": "625.00"
    }
  ]
}
```

#### Sales by Cashier
- **Endpoint**: `GET /reports/cashier-sales`
- **Query Params**: `start_date`, `end_date`, `terminal_id`, `access_token`

#### Monthly Report
- **Endpoint**: `GET /reports/monthly`
- **Query Params**: `year`, `month`, `memberid`, `access_token`

---

### 7. **Sync/Initialization Endpoints**
#### Initial Data Sync
- **Endpoint**: `GET /sync/initial`
- **Query Params**: `memberid`, `access_token`
- **Purpose**: One-time sync of all master data after login
- **Response Example**:
```json
{
  "categories": [...],
  "products": [...],
  "customers": [...],
  "terminal_users": [...],
  "last_sync_timestamp": 1705756200000
}
```

#### Incremental Sync
- **Endpoint**: `GET /sync/incremental`
- **Query Params**: `memberid`, `last_sync_timestamp`, `access_token`
- **Purpose**: Get only data changed since last sync

---

## Authentication & Authorization
All endpoints (except `/login`) should require:
- **access_token**: Returned from login endpoint
- Pass as query parameter or Authorization header: `Bearer {access_token}`

## Error Responses
Standard error format:
```json
{
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Invalid or expired access token",
    "details": null
  }
}
```

Common error codes:
- `UNAUTHORIZED` (401): Invalid/expired token
- `FORBIDDEN` (403): Insufficient permissions
- `NOT_FOUND` (404): Resource not found
- `VALIDATION_ERROR` (400): Invalid request parameters
- `INTERNAL_ERROR` (500): Server error

---

## Implementation Priority

### Phase 1 (Critical):
1. ✅ Login/Authentication
2. Terminal Users GET
3. Products GET (all & by barcode)
4. Categories GET

### Phase 2 (High Priority):
5. Customers GET & Search
6. Orders GET (history)
7. Daily Report

### Phase 3 (Medium Priority):
8. Sync endpoints
9. Detailed reports
10. Order details with line items

---

## Notes for Backend Team

1. **Pagination**: Consider adding pagination for large datasets
   - Query params: `page`, `limit`
   - Response: Include `total_count`, `page`, `has_more`

2. **Caching**: Products and categories can be cached locally
   - Use `last_modified` timestamp for cache invalidation

3. **Offline Support**: Orders created offline should sync later
   - Consider POST endpoints for order creation
   - Batch sync endpoint for multiple orders

4. **Security**: 
   - Token expiration (recommended: 24 hours)
   - Rate limiting on API endpoints
   - HTTPS only

5. **Data Consistency**:
   - Version control for master data
   - Conflict resolution strategy for offline changes
