package com.campuscoders.posterminalapp.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.campuscoders.posterminalapp.domain.model.Categories
import com.campuscoders.posterminalapp.domain.model.Customers
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.Orders
import com.campuscoders.posterminalapp.domain.model.OrdersProducts
import com.campuscoders.posterminalapp.domain.model.Products
import com.campuscoders.posterminalapp.domain.model.TerminalUsers
import com.campuscoders.posterminalapp.domain.model.Company
import com.campuscoders.posterminalapp.domain.model.License
import com.campuscoders.posterminalapp.domain.model.Department
import com.campuscoders.posterminalapp.utils.Converters


@Database(
    entities = [ Company::class, License::class, Department::class, MainUser::class, TerminalUsers::class, Products::class, Orders::class, OrdersProducts::class, Customers::class, Categories::class],
    version = 1)
@TypeConverters(Converters::class)
abstract class PosDatabase: RoomDatabase() {

    abstract fun companyDao(): CompanyDao

    abstract fun licenseDao(): LicenseDao

    abstract fun departmentDao(): DepartmentDao

    abstract fun mainUserDao(): MainUserDao
    abstract fun terminalUsersDao(): TerminalUsersDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun productsDao(): ProductsDao
    abstract fun ordersDao(): OrdersDao
    abstract fun customersDao(): CustomersDao
    abstract fun ordersProductsDao(): OrdersProductsDao
}