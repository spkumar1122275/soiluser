# API Integration Guide for POS Terminal App

## Overview
This guide explains the REST API integration implemented in the POS Terminal Application and provides recommendations for additional endpoints based on the local database architecture.

---

## ✅ Currently Implemented

### 1. Login/Authentication API
**Status**: ✅ **FULLY IMPLEMENTED**

**Endpoint**: `GET /login`

**Implementation Files**:
- `AuthApiService.kt` - Retrofit interface
- `LoginResponse.kt` - Response DTO with nested UserData
- `LoginWithApiUseCase.kt` - Business logic
- `LoginViewModel.kt` - Updated to use API
- `LoginTwoViewModel.kt` - Updated to use API
- `NetworkModule.kt` - Retrofit/OkHttp DI setup

**Request Example**:
```
GET https://gac9cb789ceb78c-adba4iv.adb.ap-hyderabad-1.oraclecloudapps.com/ords/hr/pos/login?terminalid=TN5690034001&taxid=CHRPP1539H&memberid=5690004&password=yourPasswordHere
```

**Response Example**:
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

**Flow**:
1. User enters credentials in LoginFragment/LoginTwoFragment
2. ViewModel calls `loginWithApiUseCase.executeLoginWithApi()`
3. Use case calls `AuthApiService.login()`
4. On success, user data saved to local Room database (cache)
5. Access token stored for subsequent API calls
6. User navigates to MainActivity

---

## 📋 Suggested Additional Endpoints

### Files Created for Future Implementation:

#### DTO Models (Data Transfer Objects):
- ✅ `CategoriesResponse.kt` - Categories list & single category
- ✅ `ProductsResponse.kt` - Products list & single product
- ✅ `CustomersResponse.kt` - Customers list & single customer
- ✅ `TerminalUsersResponse.kt` - Terminal users with permissions
- ✅ `OrdersResponse.kt` - Orders with line items

#### API Service Interface:
- ✅ `PosApiService.kt` - Extended API interface with all suggested endpoints

#### Documentation:
- ✅ `API_ENDPOINTS_SUGGESTION.md` - Detailed endpoint specifications

---

## 🔧 How to Integrate Additional Endpoints

When backend implements new endpoints, follow these steps:

### Step 1: Verify Endpoint is Available
Test the endpoint using curl or Postman:
```bash
curl "https://your-api-base-url/categories?memberid=5690004&access_token=YOUR_TOKEN"
```

### Step 2: Update Repository Interface
Add method to `LoginRepository.kt` (or create dedicated repositories):
```kotlin
suspend fun fetchCategories(
    memberId: String, 
    accessToken: String
): Response<CategoriesResponse>
```

### Step 3: Implement in Repository
Update `LoginRepositoryImpl.kt`:
```kotlin
override suspend fun fetchCategories(
    memberId: String,
    accessToken: String
): Response<CategoriesResponse> {
    return posApiService.getCategories(memberId, accessToken)
}
```

### Step 4: Create Use Case
Create `FetchCategoriesUseCase.kt`:
```kotlin
class FetchCategoriesUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend fun execute(memberId: String, accessToken: String): Resource<CategoriesResponse> {
        return try {
            val response = repository.fetchCategories(memberId, accessToken)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(null, "Failed to fetch categories")
            }
        } catch (e: Exception) {
            Resource.Error(null, e.localizedMessage ?: "Network error")
        }
    }
}
```

### Step 5: Update ViewModel
Inject use case and add method:
```kotlin
@HiltViewModel
class EditViewModel @Inject constructor(
    private val fetchCategoriesUseCase: FetchCategoriesUseCase
) : ViewModel() {
    
    private val _categoriesState = MutableLiveData<Resource<CategoriesResponse>>()
    val categoriesState: LiveData<Resource<CategoriesResponse>> = _categoriesState
    
    fun loadCategories(memberId: String, accessToken: String) {
        viewModelScope.launch {
            _categoriesState.value = Resource.Loading(null)
            val result = fetchCategoriesUseCase.execute(memberId, accessToken)
            _categoriesState.value = result
        }
    }
}
```

### Step 6: Update Fragment/Activity
Observe LiveData and update UI:
```kotlin
viewModel.loadCategories(memberId, accessToken)
viewModel.categoriesState.observe(viewLifecycleOwner) { resource ->
    when (resource) {
        is Resource.Success -> {
            // Update UI with categories
            val categories = resource.data?.categories
            updateCategoriesList(categories)
        }
        is Resource.Error -> {
            // Show error message
            showError(resource.message)
        }
        is Resource.Loading -> {
            // Show loading indicator
            showLoading()
        }
    }
}
```

---

## 🔐 Authentication & Token Management

### Token Storage
After successful login, store the access token:
```kotlin
// In LoginViewModel after successful API response
val accessToken = userData.accessToken
CustomSharedPreferences(context).saveAccessToken(accessToken)
```

### Using Token in Subsequent Requests
Retrieve token and pass to API calls:
```kotlin
val accessToken = CustomSharedPreferences(context).getAccessToken()
viewModel.loadProducts(memberId, accessToken)
```

### Token Expiration Handling
Implement token refresh or re-login:
```kotlin
if (response.code() == 401) {
    // Token expired, redirect to login
    navigateToLogin()
}
```

---

## 📊 Priority Implementation Roadmap

### Phase 1: Critical for Basic Operations (Week 1)
1. ✅ **Login** - DONE
2. **Categories** - GET all categories
3. **Products** - GET all products & by barcode
4. **Terminal Users** - GET cashier list

**Impact**: Enables basic POS functionality

### Phase 2: Enhanced Features (Week 2)
5. **Customers** - GET & search customers
6. **Orders** - GET order history
7. **Products by Category** - Filter products

**Impact**: Full CRUD operations for sales

### Phase 3: Reporting & Analytics (Week 3)
8. **Daily Reports** - Sales summaries
9. **Order Details** - Complete order info with line items
10. **Sales by Cashier** - Performance tracking

**Impact**: Business intelligence and reporting

### Phase 4: Optimization (Week 4)
11. **Sync Endpoints** - Incremental data sync
12. **Batch Operations** - Multiple items in one request
13. **Offline Support** - Queue and sync later

**Impact**: Performance and offline capability

---

## 🚨 Error Handling Best Practices

### Network Errors
```kotlin
try {
    val response = apiService.getProducts(memberId, token)
    if (response.isSuccessful) {
        // Success
    } else {
        when (response.code()) {
            401 -> "Unauthorized - Token expired"
            403 -> "Forbidden - Insufficient permissions"
            404 -> "Not found"
            500 -> "Server error"
            else -> "Unknown error: ${response.code()}"
        }
    }
} catch (e: IOException) {
    "Network error - Check internet connection"
} catch (e: Exception) {
    "Unexpected error: ${e.localizedMessage}"
}
```

### Fallback to Local Database
```kotlin
try {
    // Try API first
    val apiResponse = apiService.getProducts(memberId, token)
    if (apiResponse.isSuccessful) {
        return apiResponse.body()
    }
} catch (e: Exception) {
    // Fallback to local database
    Log.e("ProductRepo", "API failed, using local DB", e)
    return localDatabase.productsDao().getAllProducts()
}
```

---

## 🧪 Testing API Integration

### Manual Testing with cURL
```bash
# Test login
curl -X GET "https://your-base-url/ords/hr/pos/login?terminalid=TN5690034001&taxid=CHRPP1539H&memberid=5690004&password=test123"

# Test with token
curl -X GET "https://your-base-url/ords/hr/pos/categories?memberid=5690004&access_token=YOUR_TOKEN"
```

### Unit Tests
Create test cases in `test/` directory:
```kotlin
@Test
fun `test login API returns valid response`() = runBlocking {
    val response = authApiService.login("TN5690034001", "CHRPP1539H", "5690004", "test123")
    assertTrue(response.isSuccessful)
    assertNotNull(response.body()?.user?.accessToken)
}
```

---

## 📝 Configuration

### Base URL Configuration
Current: Hardcoded in `NetworkModule.kt`

**Recommendation**: Make it configurable

**Option 1 - BuildConfig**:
```kotlin
// build.gradle.kts
android {
    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://dev-api.example.com/\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.example.com/\"")
        }
    }
}

// NetworkModule.kt
private const val BASE_URL = BuildConfig.BASE_URL
```

**Option 2 - Environment Configuration**:
```kotlin
// Create Config.kt
object ApiConfig {
    const val PROD_BASE_URL = "https://prod-api.example.com/"
    const val DEV_BASE_URL = "https://dev-api.example.com/"
    
    val BASE_URL = if (BuildConfig.DEBUG) DEV_BASE_URL else PROD_BASE_URL
}
```

---

## 🔍 Debugging Tips

### Enable HTTP Logging
Already configured in `NetworkModule.kt`:
```kotlin
val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
```

View logs in Logcat:
```
Filter: OkHttp
```

### Network Inspector (Android Studio)
1. Run app in debug mode
2. Open "App Inspection" tab
3. Select "Network Inspector"
4. View all HTTP requests/responses

### Common Issues
- **401 Unauthorized**: Token expired or invalid
- **No internet**: Check connectivity
- **SSL errors**: Certificate issues (use HTTPS)
- **Timeout**: Increase timeout in NetworkModule

---

## 📞 Backend Team Coordination

### What Backend Needs to Provide:
1. ✅ Base URL for each environment (dev/staging/prod)
2. ✅ API endpoint documentation (Swagger/Postman collection)
3. ⏳ Sample request/response JSON for each endpoint
4. ⏳ Error codes and error response format
5. ⏳ Authentication mechanism (token format, expiration)
6. ⏳ Rate limiting information
7. ⏳ Pagination parameters (if applicable)

### Communication Checklist:
- [ ] Confirm API response format matches DTOs
- [ ] Agree on date/time format (ISO 8601 recommended)
- [ ] Define error response structure
- [ ] Establish token expiration policy
- [ ] Agree on null handling (null vs empty string)
- [ ] Define pagination approach (page/limit)
- [ ] Clarify currency format (decimal places)

---

## 📚 Additional Resources

### Key Files to Review:
- `NetworkModule.kt` - Retrofit configuration
- `AuthApiService.kt` - Current API endpoints
- `PosApiService.kt` - Suggested additional endpoints
- `LoginResponse.kt` - Response structure
- `API_ENDPOINTS_SUGGESTION.md` - Detailed endpoint specs

### Useful Libraries:
- **Retrofit**: HTTP client
- **OkHttp**: Network layer
- **Gson**: JSON parsing
- **Coroutines**: Async operations

### Next Steps:
1. Share `API_ENDPOINTS_SUGGESTION.md` with backend team
2. Prioritize endpoints based on business needs
3. Backend implements endpoints
4. Mobile team integrates following this guide
5. Test thoroughly with real data
6. Deploy to production

---

## 🆘 Support

For questions or issues:
1. Review error logs in Logcat
2. Check network inspector
3. Verify API endpoint is working (curl/Postman)
4. Consult `API_ENDPOINTS_SUGGESTION.md`
5. Contact backend team for server-side issues

---

**Last Updated**: January 2025
**Version**: 1.0
**Maintainer**: Mobile Development Team
