package com.campuscoders.posterminalapp.di

import com.campuscoders.posterminalapp.data.locale.CategoriesDao
import com.campuscoders.posterminalapp.data.locale.CustomersDao
import com.campuscoders.posterminalapp.data.locale.OrdersDao
import com.campuscoders.posterminalapp.data.locale.OrdersProductsDao
import com.campuscoders.posterminalapp.data.locale.ProductsDao
import com.campuscoders.posterminalapp.data.locale.TerminalUsersDao
import com.campuscoders.posterminalapp.data.repository.locale.*
import com.campuscoders.posterminalapp.domain.repository.locale.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSaleRepository(
        categoriesDao: CategoriesDao,
        productsDao: ProductsDao,
        customersDao: CustomersDao,
        ordersDao: OrdersDao,
        ordersProductsDao: OrdersProductsDao
    ): SaleRepository {
        return SaleRepositoryImpl(
            categoriesDao,
            productsDao,
            ordersDao,
            customersDao,
            ordersProductsDao
        )
    }

    @Provides
    @Singleton
    fun provideCashierAndReportRepository(
        terminalUsersDao: TerminalUsersDao,
        ordersDao: OrdersDao
    ): CashierAndReportRepository {
        return CashierAndReportRepositoryImpl(terminalUsersDao, ordersDao)
    }

    @Provides
    @Singleton
    fun provideDocumentRepository(
        ordersDao: OrdersDao,
        ordersProductsDao: OrdersProductsDao
    ): DocumentRepository {
        return DocumentRepositoryImpl(ordersDao, ordersProductsDao)
    }

    @Provides
    @Singleton
    fun provideEditRepository(
        categoriesDao: CategoriesDao,
        productsDao: ProductsDao
    ): EditRepository {
        return EditRepositoryImpl(categoriesDao, productsDao)
    }
}
